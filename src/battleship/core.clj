(ns battleship.core
  "This package contains basic and elementary functions of the battleship game."
  (:require [clojure.string :as str]))


(def matrix-row 5)
(def true-period 5)

(defn compute-index
  "Computes a cell index based on a row and a column. "
  [row col]
  (+ col (* matrix-row row)))

(defn get-cell
  "Retrieves a cell from a battlefield."
  [row col battlefield]
  (battlefield (compute-index row col)))

(defn random-bool
  "Generate a boolean based on the following frequency: 1/5 for a true and 4/5 for a false."
  [period]
  (= (rand-int (+ period 1)) 0))

(defn init-cell
  "Initialize a cell.
   Ex: {:has-enemy? true|false :shot-by :none}"
  []
  {:has-enemy? (random-bool true-period) :shot-by :none})

(defn generate-game
  "Generates 5x5 matrix game.
   Each cell of this matrix looks like this: {:has-enemy? true|false :shot-by :none}"
  []
  (into [] (repeatedly (* matrix-row matrix-row) init-cell)))

(defn found-enemy?
  "Predicate that tells if there is an enemy at a given location."
  [row col battlefield]
  (let [cell (get-cell row col battlefield)]
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
