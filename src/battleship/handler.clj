(ns battleship.handler
  (:use compojure.core ring.middleware.json ring.util.response)
  (:require [compojure.handler :as handler]
            [battleship.core   :as core ]
            [battleship.logic  :as logic]
            [compojure.route   :as route]))


(def battlefield (atom (core/generate-battlefield)))

(defroutes app-routes
  (GET "/"  [] "<br/><h1>Battleship ... </h1> boom ! fire !")

  (PUT "/battleship/game/:game/player/:player/attack"
       {{row :row col :col player :player } :params}
       (response (logic/launch-attack
         (read-string row)
         (read-string col)
         player
         battlefield)))

  (route/resources "/")

  (route/not-found "Not Found"))

(def app
  (wrap-json-response (handler/site app-routes)))
