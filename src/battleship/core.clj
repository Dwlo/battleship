(ns battleship.core
  (:require [clojure.string :as str]))


(def matrix-row 5)


(defn compute-index "Computes a cell index based on a row and a column. "
  [row col]
  (+ col (* matrix-row row)))

(defn get-cell
  "Retrieves a cell from the battlefield: [:a :b :c]"
  [row col battlefield]
  (battlefield (compute-index row col)))

(defn random-bool "Generate a boolean based on the following frequency: 1/5 for a true and 4/5 for a false."
  []
  (= (rand-int (+ matrix-row 1)) matrix-row))

(defn init-cell "Initialize a cell"
  []
  {:has-enemy? (random-bool) :shot-by :none})

(defn generate-battlefield "Generates a 5x5 matrix. Each cell of this matrix looks like this: {:has-enemy? true/false :shot-by playerX}"
  []
  (into [] (repeatedly (* matrix-row matrix-row) init-cell)))

(defn found-active-enemy? "Predicate that tells if there is an active enemy at a given location."
  [row col battlefield]
  (let [cell (get-cell row col battlefield)]
    (and (:has-enemy? cell) (= :none (:shot-by cell)))))

(defn score "Computes the score of all players. ex: {'player1' 5 'player2' 1}"
  [battlefield]
  (frequencies (for [cell battlefield :when (not= (:shot-by cell) :none)] (:shot-by cell))))

(defn is-game-over? "Checks whether of not not there is an enemy left."
  [battlefield]
  (not (true? (some #(= {:has-enemy? true :shot-by :none} %)  battlefield))))

(defn shoot-enemy "Marks a cell as shot with the name of the player."
  [row col player battlefield]
  (swap! battlefield update-in [(compute-index row col) :shot-by] (fn [x] player )))

(defn battlefield-string "Draws the battlefield with shot enemies."
  [battlefield]
  (str/join "\n"(map (fn [names] (str/join "|" names)) (partition 5 (map (fn [shot-by] (if (= shot-by :none) "-" shot-by))
                                                           (map (fn [cell] (cell :shot-by)) battlefield)))))
)
