(ns battleship.logic
  (:use battleship.core))


(defn fire "A player attempt to shoot an enemy. If an enemy is shot the fun returns :success otherwise :failure"
  [row col player battlefield]
  (if (found-active-enemy? row col @battlefield)
    (do (shoot-enemy row col player battlefield)
        :success)
    :failure))
