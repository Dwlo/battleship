(ns battleship.logic
  "This package contains the battleship's game logic."
  (:use battleship.core))


(defn generate-game-id "Generates an random game identifier."
  []
  (str (java.util.UUID/randomUUID)))

(defn register-new-game "Registers a new game into the global context."
  [battlefields]
  (let [game-id (generate-game-id)]
    (swap! battlefields assoc game-id (atom (generate-battlefield)))
    game-id))

(defn shoot-enemy "Marks a cell as shot with the name of the player."
  [row col player battlefield]
  (swap! battlefield update-in [(compute-index row col) :shot-by] (fn [x] player )))

(defn attempt-attack "A player attempt to shoot an enemy. If an enemy is shot the fun returns :success otherwise :failure"
  [row col player battlefield]
  (if (found-active-enemy? row col @battlefield)
    (do (shoot-enemy row col player battlefield)
        :success)
    :failure))

(defn launch-attack "Launches an attack against an enemy supposed to be at a given location and returns a result as following: {:attack-status :success|:failure :game-status :running|:over}"
  [row col player battlefield]
  {:attack-status (attempt-attack row col player battlefield)
   :game-status ({true :over false :running} (is-game-over? @battlefield))
   :score (score @battlefield)})

(defn show-global-context "Shows the global context.
Ex: {game-id {player1 score1 player2 score2}}"
  [battlefields]
  (for [ctx @battlefields
        :let [game-id (key ctx) bf @(val ctx) status {false :running  true :over}]]
    {game-id (assoc (score bf) :status (status (is-game-over? bf)))}))

(defn terminated-games "Returns all the games in a over state"
  [battlefields]
  (for [ctx @battlefields
        :let [game-id (key ctx) bf @(val ctx)]
        :when (is-game-over? bf)]
    game-id))

(defn gc "Releases the terminated games."
  [battlefields]
  (swap! battlefields #(apply dissoc % (terminated-games battlefields))))
