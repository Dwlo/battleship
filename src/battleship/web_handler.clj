(ns battleship.web-handler
  "This package contains the REST API and the server instance of battleship game."
  (:require [battleship
             [game-handler        :as game-handler]
             [view                :as view]]
            [compojure
             [core                :refer :all]
             [handler             :as    handler]
             [route               :as    route]]
            [ring.middleware.json :refer :all]
            [ring.util.response   :refer :all]))

(defroutes app-routes
  ;;;;; Public APIs  ;;;;;
  ;; --- The index page.
  (GET "/" [] (view/index-page))

  ;;;;; Admin APIs  ;;;;;
  ;; --- Getting infos about all games.
  (GET "/game-center/status" [] (response (game-handler/describe-global-status (deref game-handler/games))))


  ;;;;; Players APIs  ;;;;;
  ;; --- Retrieves the battlefield for the given game id.
  (GET "/games/:game-id/battlefield" [game-id] (response (game-handler/display-battlefield game-id)))

  ;; --- Registers a new game to the all games context.
  (POST "/games" request
        (let [size    (get-in request [:body :size])
              players (get-in request [:body :players])]
          (response {:game-id (game-handler/register-game size players)})))

  ;;  --- Attemps an attack by a given player on a given location.
  (PUT "/games/:game-id/players/:player/fire" {{row :row col :col player :player game-id :game-id} :params}
       (let [result (game-handler/play (read-string row) (read-string col) player game-id)
             msg    (:message result)
             err    (:error result)]
         (cond (= err :game-not-found) (not-found msg)
               (not= err nil)          {:status 400
                                        :body   msg}
           :else
           (response result))))

  ;; --- Describes game
  (GET "/games/:game-id/describe" [game-id]
       (if-let [game (game-handler/lookup-game game-id)]
         (response (game-handler/describe-game game))
         (not-found {:error (str "No game found with the given id: " game-id)})))

  ;;;;; Others
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (handler/site app-routes)
      (wrap-json-response)
      (wrap-json-body {:keywords? true})))
