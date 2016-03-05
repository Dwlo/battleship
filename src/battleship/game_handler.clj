(ns battleship.game-handler
  "This namespace is dedicated to functions related with game management, not the game logic itself"
  (:require [battleship.battlefield :as battlefield]
            [clj-time.local         :as time]))

(def games (atom {}))

;; Game management functions
(defn generate-game-id
  "Generates an random game identifier."
  []
  (str (java.util.UUID/randomUUID)))

(defn has-game-enemy?
  "Whether or not a battlefield has enemies in it"
  [battlefield]
  (some #(and (:has-enemy? %) (= :none (:shot-by %))) battlefield))

(defn new-game
  "Creates a new Battlefield game"
  [size]
  (->> (repeatedly #(battlefield/initialize-battlefield size))
       (filter has-game-enemy?)
       (first)))

(defn lookup-game
  "Lookups a game context from the context of games"
  [game-id]
  (@games game-id))

(defn lookup-battlefield
  "Lookups a game's battlefield"
  [game]
  (deref (:battlefield game)))

(defn register-game
  "Registers a new game into the global context"
  [length players]
  (let [game-id     (generate-game-id)
        battlefield (new-game length)]
    (swap! games assoc game-id {:battlefield       (atom battlefield)
                                :creation-date     (time/local-now)
                                :next-player-index (atom 0)
                                :players           players})
    game-id))

(defn update-battlefield
  "Updates the battlefield after a successful shot"
  [row col player game-id]
  (let [game (:battlefield (@games game-id))]
    (swap! game update-in [(battlefield/locate-cell row col (battlefield/length @game)) :shot-by] (fn [x] player ))))

(defn is-game-over?
  "Checks whether of not there is an enemy left."
  [game]
  (let [ battlefield (lookup-battlefield game)]
       (not (some #(= {:has-enemy? true :shot-by :none} %)  battlefield))))

(defn terminated-games
  "Returns all terminated games"
  [games]
  (for [ctx @games
        :let  [game-id (key ctx) bf @(:battlefield (val ctx))]
        :when (is-game-over? (val ctx))]
    game-id))

(defn clean-up
  "Releases the terminated games memory."
  []
  (swap! games #(apply dissoc % (terminated-games games))))

(defn describe-global-status
  "Global status on games"
  [games]
  (let [live-game-ids (keys (remove #(is-game-over? (val %)) games))]
   {:total-games (count games)
    :live-games  {:count (count live-game-ids)
                  :game-ids (or live-game-ids [])}}))

(defn get-players
  "List of players in game"
  [game]
  (:players game))

(defn get-score
  "Computes players score"
  [game]
  (let [battlefield (lookup-battlefield game)]
   (frequencies (for [cell battlefield :when (not= (:shot-by cell) :none)] (:shot-by cell)))))

(defn describe-game
  "Describes a game"
  [game]
  (let [battlefield (lookup-battlefield game)]
   {:score       (get-score game)
    :size        (battlefield/length battlefield)
    :live        (not (is-game-over? game))
    :players     (get-players game)
    :battlefield (battlefield/describe-battlefield battlefield)}))

;; Play function
(defn display-battlefield
  [game-id]
  (->> (lookup-game game-id)
       (lookup-battlefield)
       (battlefield/show-battlefield-status)))

(defn display-enemies
  [game-id]
  (->> (lookup-game game-id)
       (lookup-battlefield)
       (battlefield/show-enemies-in-battlefield)))

(defn found-enemy?
  "Predicate that tells if there is an enemy at a given location."
  [row col battlefield]
  (let [cell (battlefield/select-cell row col battlefield)]
    (and (:has-enemy? cell) (= :none (:shot-by cell)))))

(defn attack
  "A player attempt to shoot an enemy.
   If an enemy is shot the fun returns :success otherwise :failure"
  [row col player game]
  (let [battlefield (lookup-battlefield game)]
    (if (found-enemy? row col battlefield) :success :failure)))
