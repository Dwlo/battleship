(ns battleship.logic
  "This package contains the battleship's game logic."
  (:require [battleship.core :refer :all]))

(def default-size 5)

(defn generate-game-id
  "Generates an random game identifier."
  []
  (str (java.util.UUID/randomUUID)))

(defn register-new-game
  "Registers a new game into the global context."
  [games]
  (let [game-id (generate-game-id)]
    (swap! games assoc game-id (atom (initialize-battlefield default-size)))
    game-id))

(defn shoot-enemy
  "Marks a cell as shot with the name of the player."
  [row col player game]
  (swap! game update-in [(locate-cell row col (battlefield-length @game)) :shot-by] (fn [x] player )))

(defn attempt-attack
  "A player attempt to shoot an enemy.
   If an enemy is shot the fun returns :success otherwise :failure"
  [row col player game]
  (if (found-enemy? row col @game)
    (do (shoot-enemy row col player game) :success)
    :failure))

(defn fire
  "Fires against an enemy supposed to be at a given location.
   Returns a result as following:
   {:fire-status :success|:failure :game-status :running|:over :score {...}}"
  [row col player game]
  {:fire-status (attempt-attack row col player game)
   :game-status ({true :over false :running} (is-game-over? @game))
   :score       (score @game)})

(defn get-games-info
  "Retrieves the informations about all games.
   Ex: {game-id {player1 score1 player2 score2}}"
  [games]
  (for [ctx @games
        :let [game-id (key ctx) bf @(val ctx) status {false :running  true :over}]]
    {game-id (assoc (score bf) :status (status (is-game-over? bf)))}))

(defn get-game-stats
  "Retrieves a game context."
  [game]
  {:status ({true :over false :running} (is-game-over? game))
   :score  (score game)})

(defn terminated-games
  "Returns all the games in a over state"
  [games]
  (for [ctx @games
        :let  [game-id (key ctx) bf @(val ctx)]
        :when (is-game-over? bf)]
    game-id))

(defn clean-up
  "Releases the terminated games memory."
  [games]
  (swap! games #(apply dissoc % (terminated-games games))))
