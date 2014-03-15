(ns battleship.handler
  "This package contains the REST API and the server instance of battleship game."
  (:use compojure.core ring.middleware.json ring.util.response)
  (:require [compojure.handler :as handler]
            [battleship.core   :as core ]
            [battleship.logic  :as logic]
            [compojure.route   :as route]
            [battleship.view   :as view]))


(def battlefields (atom {}))


(defroutes app-routes
  ;;;;; Public APIs
  ;; --- The index page.
  (GET "/" [] (view/index-page))


  ;;;;; Admin APIs
  ;; --- Getting infos about the global context.
  (GET "/admin/info" [] (response (logic/show-global-context battlefields)))
  ;; --- Releasing the terminated games from the global context.
  (DELETE "/admin/gc" [] (do (logic/gc battlefields) (response {:clean-up :done})))


  ;;;;; Players APIs
  ;; --- Retrieve the battlefield for the given game id.
  (GET "/games/:game/battlefield" [game] (response (core/battlefield-string @(@battlefields game))))
  ;; --- Generates a new game context.
  (POST "/games" []
        (response {:game-id (logic/register-new-game battlefields)}))
  ;; --- Attemps an attack by a given player on a given location.
  (PUT "/games/:game-id/players/:player/attack"
       {{row :row col :col player :player game-id :game-id} :params}
       (response (logic/launch-attack
                  (read-string row)
                  (read-string col)
                  player
                  (@battlefields game-id))))


  ;;;;; Others
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (wrap-json-response (handler/site app-routes)))
