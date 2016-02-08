(ns battleship.core
  "This package contains basic and elementary functions of the battleship game."
  ;;
  ;;
  ;;
  ;; NB: this namespace must be renamed battlefield
  ;;
  ;;
  ;;
  (:require [clojure.string :as str])
  (:import  (java.lang Math)))

(defn battlefield-length
  [battlefield]
  (int (Math/sqrt (count battlefield))))

(defn locate-cell
  "Locates a cell index based on a row and a column."
  [row col matrix-length]
  (+ col (* matrix-length row)))

(defn select-cell
  "Selects a given cell from a battlefield."
  [row col battlefield]
  (nth battlefield (locate-cell row col (battlefield-length battlefield))))

(defn initialize-cell
  "Initialize a cell.
   Ex: {:has-enemy? true|false :shot-by :none}"
  []
  {:has-enemy? (= (rand-int 5) 0) :shot-by :none})

(defn initialize-battlefield
  "Generates nxn matrix game.
   Each cell of this matrix looks like this: {:has-enemy? true|false :shot-by :none}"
  [matrix-length]
  (into [] (repeatedly (* matrix-length matrix-length) initialize-cell)))

(defn battlefield-string
  "Draws the battlefield with shot enemies."
  [battlefield]
  (->> battlefield
       (map (comp (fn [shot-by] (if (= shot-by :none) "-" shot-by)) :shot-by))
       (partition 5)
       (map (fn [names] (str/join "|" names)))
       (str/join "\n")))

(defn show-enemies
  "Shows the location of all enemies"
  [battlefield]
  (->> battlefield
       (map (comp (fn [has-enemy] (if has-enemy "E" "-")) :has-enemy?))
       (partition 5)
       (map (fn [status] (str/join "|" status)))
       (str/join "\n")))
