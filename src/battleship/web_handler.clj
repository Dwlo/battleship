(ns battleship.web-handler
  "This package contains the REST API and the server instance of battleship game."
  (:require [battleship
             [game-handler :as game-handler]
             [logic        :as logic]
             [view         :as view]]
            [compojure
             [core    :refer :all]
             [handler :as    handler]
             [route   :as    route]]
            [ring.middleware.json :refer :all]
            [ring.util.response   :refer :all]))

(defroutes app-routes
  ;;;;; Public APIs  ;;;;;
  ;; --- The index page.
  (GET "/" [] (view/index-page))

  ;;;;; Admin APIs  ;;;;;
  ;; --- Getting infos about all games.
  (GET "/game-center/status" [] (response (game-handler/describe (deref game-handler/games))))

  ;; --- Cleans up the finished games from the 'all games context'.
  (DELETE "/admin/gc" [] (do (game-handler/clean-up) (response {:clean-up :done})))


  ;;;;; Players APIs  ;;;;;
  ;; --- Retrieves the battlefield for the given game id.
  (GET "/games/:game-id/battlefield" [game-id] (response (logic/show-battlefield (game-handler/lookup-game game-id))))

  ;; --- Retrieves the full battlefield data for the given game id.
  ;; To be removed: only for debug purpose
  (GET "/games/:game-id/show-enemies" [game-id] (response (logic/show-enemies (game-handler/lookup-game game-id))))

  ;; --- Registers a new game to the all games context.
  (POST "/games" {{size :size} :params}
        (response {:game-id (game-handler/register-new-game (logic/create-game (read-string size)) [])}))

  ;;  --- Attemps an attack by a given player on a given location.
  (PUT "/games/:game-id/players/:player/fire" {{row :row col :col player :player game-id :game-id} :params}
       (if-let [game (game-handler/lookup-game game-id)]
         (let [row'             (read-string row)
               col'             (read-string col)
               attack-result (logic/attack row' col' player game)]
           (when (= attack-result :success)
             (game-handler/update-game row' col' player game-id))
           (response {:shot-result attack-result}))
         (not-found {:error (str "No game found with the given id: " game-id)})))

  ;; --- Descibes game
  (GET "/games/:game-id/describe" [game-id]
       (if-let [game (game-handler/lookup-game game-id)]
         (response (logic/describe-game game))
         (not-found {:error (str "No game found with the given id: " game-id)})))

  ;;;;; Others
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (handler/site app-routes)
      (wrap-json-response)))
