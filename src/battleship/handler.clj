(ns battleship.handler
  "This package contains the REST API and the server instance of battleship game."
  (:require [compojure.handler       :as handler]
            [compojure.core          :refer :all]
            [ring.middleware.json    :refer :all]
            [ring.util.response      :refer :all]
            [battleship.game-manager :as    gm]
            [battleship.logic        :as    log]
            [compojure.route         :as    route]
            [battleship.view         :as    view]))

(defroutes app-routes
  ;;;;; Public APIs  ;;;;;
  ;; --- The index page.
  (GET "/" [] (view/index-page))


  ;;;;; Admin APIs  ;;;;;
  ;; --- Getting infos about all games.
  (GET "/admin/info" [] (response {:total-games (gm/get-games-info)}))

  ;; --- Cleans up the finished games from the 'all games context'.
  (DELETE "/admin/gc" [] (do (gm/clean-up) (response {:clean-up :done})))


  ;;;;; Players APIs  ;;;;;
  ;; --- Retrieves the battlefield for the given game id.
  (GET "/games/:game-id/battlefield" [game-id] (response (log/show-battlefield (gm/lookup-game game-id))))

  ;; --- Retrieves the full battlefield data for the given game id.
  (GET "/games/:game-id/show-enemies" [game-id] (response (log/show-enemiesX (gm/lookup-game game-id))))

  ;; --- Registers a new game to the all games context.
  (POST "/games" [] (response {:game-id (gm/register-new-game (log/create-game 5))}))

  ;;  --- Attemps an attack by a given player on a given location.
  (PUT "/games/:game-id/players/:player/fire" {{row :row col :col player :player game-id :game-id} :params}
       (if-let [game (gm/lookup-game game-id)]
         (let [r             (read-string row)
               c             (read-string col)
               attack-result (log/attack r c player game)]
           (when (= attack-result :success)
             (gm/update-game r c player game-id))
           (response {:shot-result attack-result}))
         (not-found {:error (str "No game found with the given id: " game-id)})))

  ;; --- Retrieves game info about a game
  (GET "/games/:game-id/stats" [game-id]
       (if-let [game (gm/lookup-game game-id)]
         (response {:score  (log/get-game-stats game-id)
                    :status ({true :over false :running} (log/is-game-over? game))})
         (not-found {:error (str "No game found with the given id: " game-id)})))

  ;;;;; Others
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (handler/site app-routes)
      (wrap-json-response)))
