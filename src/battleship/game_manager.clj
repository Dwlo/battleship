(ns battleship.game-manager
  "This namespace is dedicated to functions related with game management, not the game logic itself"
  (:require [battleship.core  :as c]
            [battleship.logic :as l]))

(def default-size 5)
(def games (atom {}))

(defn generate-game-id
  "Generates an random game identifier."
  []
  (str (java.util.UUID/randomUUID)))

(defn lookup-game
  "Extracts a game context from the context of games."
  [game-id]
  (@games game-id))

(defn register-new-game
  "Registers a new game into the global context."
  [game]
  (let [game-id (generate-game-id)]
    (swap! games assoc game-id (atom game))
    game-id))

(defn get-games-info
  "Retrieves the informations about all games."
  []
  (count @games))

(defn terminated-games
  "Returns all the games in a over state"
  [games]
  (for [ctx @games
        :let  [game-id (key ctx) bf @(val ctx)]
        :when (l/is-game-over? bf)]
    game-id))

(defn clean-up
  "Releases the terminated games memory."
  []
  (swap! games #(apply dissoc % (terminated-games games))))
