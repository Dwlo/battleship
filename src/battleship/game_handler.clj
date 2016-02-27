(ns battleship.game-handler
  "This namespace is dedicated to functions related with game management, not the game logic itself"
  (:require [battleship
             [battlefield   :as bf]
             [logic         :as log]]
            [clj-time.local :as time]))

(def default-size 5)
(def games (atom {}))

(defn generate-game-id
  "Generates an random game identifier."
  []
  (str (java.util.UUID/randomUUID)))

(defn lookup-game
  "Extracts a game context from the context of games."
  [game-id]
  (deref (:battlefield (@games game-id))))

(defn register-new-game
  "Registers a new game into the global context."
  [game players]
  (let [game-id (generate-game-id)]
    (swap! games assoc game-id {:battlefield       (atom game)
                                :creation-date     (time/local-now)
                                :next-player-index (atom 0)
                                :players           players})
    game-id))

(defn update-game
  "Updates the battlefield after a successful shot"
  [row col player game-id]
  (let [game (:battlefield (@games game-id))]
    (swap! game update-in [(bf/locate-cell row col (bf/length @game)) :shot-by] (fn [x] player ))))

(defn terminated-games
  "Returns all the games in a over state"
  [games]
  (for [ctx @games
        :let  [game-id (key ctx) bf @(:battlefield (val ctx))]
        :when (log/is-game-over? bf)]
    game-id))

(defn clean-up
  "Releases the terminated games memory."
  []
  (swap! games #(apply dissoc % (terminated-games games))))

(defn describe
  "Global status on games"
  [games]
  (let [live-game-ids (keys (remove #(log/is-game-over? @(:battlefield (val %))) games))]
   {:total-games (count games)
    :live-games  {:count (count live-game-ids)
                  :game-ids (or live-game-ids [])}}))
