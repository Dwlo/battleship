(ns battleship.core)

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

(defn found-enemy? "Predicate that tells if there is an enemy at a given location."
  [row col battlefield]
  (:has-enemy? (get-cell row col battlefield)))

(defn score "Computes the score of all players. ex: {'player1' 5 'player2' 1}"
  [battlefield]
  (frequencies (for [cell battlefield :when (not= (:shot-by cell) :none)] (:shot-by cell))))
