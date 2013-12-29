(ns battleship.handler
  (:use compojure.core ring.middleware.json ring.util.response)
  (:require [compojure.handler :as handler]
            [battleship.core   :as core ]
            [battleship.logic  :as logic]
            [compojure.route   :as route]))


(def battlefields (atom {}))

(defn generate-game-id "Generates an random game identifier."
  []
  (str (java.util.UUID/randomUUID)))

(defroutes app-routes
  (GET "/" [] "<br/><h1>Battleship ... </h1> boom ! fire !")

  (POST "/battleship/games" []
        (let [game-id (generate-game-id)]
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
