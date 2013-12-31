(ns battleship.handler
  (:use compojure.core ring.middleware.json ring.util.response)
  (:require [compojure.handler :as handler]
            [battleship.core   :as core ]
            [battleship.logic  :as logic]
            [compojure.route   :as route]))


(def battlefields (atom {}))


(defroutes app-routes
  (GET "/" [] "<br/><h1>Battleship ... </h1> boom ! fire !")

  (GET "/battleship/admin/info" [] (response (logic/show-global-context battlefields)))

  (DELETE "/battleship/admin/gc" [] (do (logic/gc battlefields)
                                      (response {:clean-up :done})))

  (POST "/battleship/games" []
        (let [game-id (logic/generate-game-id)]
          (do
            (swap! battlefields assoc game-id (atom (core/generate-battlefield)))
            (response {:game-id game-id}))))

  (PUT "/battleship/games/:game-id/players/:player/attack"
       {{row :row col :col player :player game-id :game-id} :params}
       (response (logic/launch-attack
                  (read-string row)
                  (read-string col)
                  player
                  (@battlefields game-id))))

  (route/resources "/")

  (route/not-found "Not Found"))

(def app
  (wrap-json-response (handler/site app-routes)))
