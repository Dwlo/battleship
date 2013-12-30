(ns battleship.logic
  (:use battleship.core))


(defn attempt-attack "A player attempt to shoot an enemy. If an enemy is shot the fun returns :success otherwise :failure"
  [row col player battlefield]
  (if (found-active-enemy? row col @battlefield)
    (do (shoot-enemy row col player battlefield)
        :success)
    :failure))

(defn launch-attack "Launches an attack against an enemy supposed to be at a given location and returns a result as following: {:attack-status :success|:failure :game-status :running|:over}"
  [row col player battlefield]
  {:attack-status (attempt-attack row col player battlefield)
   :game-status ({true :over false :running} (is-game-over? @battlefield))})

(defn generate-game-id "Generates an random game identifier."
  []
  (str (java.util.UUID/randomUUID)))
