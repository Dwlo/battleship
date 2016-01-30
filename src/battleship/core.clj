(ns battleship.core
  "This package contains basic and elementary functions of the battleship game."
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
  (battlefield (locate-cell row col (battlefield-length battlefield))))

(defn initialize-cell
  "Initialize a cell.
   Ex: {:has-enemy? true|false :shot-by :none}"
  []
  {:has-enemy? #(= (rand-int 5) 0) :shot-by :none})

(defn initialize-battlefield
  "Generates nxn matrix game.
   Each cell of this matrix looks like this: {:has-enemy? true|false :shot-by :none}"
  [matrix-length]
  (into [] (repeatedly (* matrix-length matrix-length) initialize-cell)))

(defn found-enemy?
  "Predicate that tells if there is an enemy at a given location."
  [row col battlefield]
  (let [cell (select-cell row col battlefield)]
    (and (:has-enemy? cell) (= :none (:shot-by cell)))))

(defn score
  "Computes the score of all players. ex: {'player1' 5 'player2' 1}"
  [battlefield]
  (frequencies (for [cell battlefield :when (not= (:shot-by cell) :none)] (:shot-by cell))))

(defn is-game-over?
  "Checks whether of not there is an enemy left."
  [battlefield]
  (not (some #(= {:has-enemy? true :shot-by :none} %)  battlefield)))

(defn battlefield-string
  "Draws the battlefield with shot enemies."
  [battlefield]
  (->> battlefield
       (map (comp (fn [shot-by] (if (= shot-by :none) "-" shot-by)) :shot-by))
       (partition 5)
       (map (fn [names] (str/join "|" names)))
       (str/join "\n")))
