(ns battleship.handler
  "This package contains the REST API and the server instance of battleship game."
  (:use compojure.core ring.middleware.json ring.util.response)
  (:require [compojure.handler :as handler]
            [battleship.core   :as core ]
            [battleship.logic  :as logic]
            [compojure.route   :as route]
            [battleship.view   :as view]))


(def games (atom {}))

(defn lookup-game "Extracts a game context from the context of games."
  [game-id]
  (@games game-id))


(defroutes app-routes
  ;;;;; Public APIs
  ;; --- The index page.
  (GET "/" [] (view/index-page))


  ;;;;; Admin APIs
  ;; --- Getting infos about the global context.
  (GET "/admin/info" [] (response (logic/get-games-info games)))
  ;; --- Releasing the terminated games from the global context.
  (DELETE "/admin/gc" [] (do (logic/gc games) (response {:clean-up :done})))


  ;;;;; Players APIs
  ;; --- Retrieves the battlefield for the given game id.
  (GET "/games/:game/battlefield" [game] (response (core/battlefield-string @(@games game))))
  ;; --- Generates a new game context.
  (POST "/games" []
        (response {:game-id (logic/register-new-game games)}))
  ;; --- Attemps an attack by a given player on a given location.
  (PUT "/games/:game-id/players/:player/fire"
       {{row :row col :col player :player game-id :game-id} :params}
       (response (logic/launch-attack
                  (read-string row)
                  (read-string col)
                  player
                  (lookup-game game-id))))

  ;; --- Retrieves game info about a game
  (GET "/games/:game-id/stats" [game-id] (let [game (lookup-game game-id)]
                                        (if (nil? game)
                                          (not-found {:error (str "No game found with the given id: " game-id)})
                                          (response (assoc (logic/get-game-stats @game) :game-id game-id)))))

  ;; response logic/get-game-context game

  ;;;;; Others
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (wrap-json-response (handler/site app-routes)))
