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
  ;; --- Getting infos about all games.
  (GET "/admin/info" [] (response (logic/get-games-info games)))

  ;; --- Cleans up the finished games from the 'all games context'.
  (DELETE "/admin/clean-up" [] (do (logic/clean-up games) (response {:clean-up :done})))


  ;;;;; Players APIs
  ;; --- Retrieves the battlefield for the given game id.
  (GET "/games/:game-id/battlefield" [game-id] (response (core/battlefield-string @(@games game-id))))

  ;; --- Registers a new game to the all games context.
  (POST "/games" [] (response {:game-id (logic/register-new-game games)}))

  ;; --- Attemps an attack by a given player on a given location.
  (PUT "/games/:game-id/players/:player/fire" {{row :row col :col player :player game-id :game-id} :params}
       (if-let [game (lookup-game game-id)]
         (response (logic/fire
                    (read-string row)
                    (read-string col)
                    player
                    (lookup-game game-id)))
         (not-found {:error (str "No game found with the given id: " game-id)})))

  ;; --- Retrieves game info about a game
  (GET "/games/:game-id/stats" [game-id]
       (if-let [game (lookup-game game-id)]
         (response (assoc (logic/get-game-stats @game) :game-id game-id))
         (not-found {:error (str "No game found with the given id: " game-id)})))

  ;;;;; Others
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (wrap-json-response (handler/site app-routes)))
