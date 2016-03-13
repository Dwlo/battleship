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
                                :next-player-index (atom (cycle (range (count players))))
                                :players           players})
    game-id))

(defn update-player-turn
  "Sets the next player for a game"
  [game]
  (let [next-player-gen (:next-player-index game)]
    (swap! next-player-gen #(rest %))))

(defn update-battlefield
  "Updates the battlefield after a successful shot"
  [row col player game-id]
  (let [battlefield (:battlefield (@games game-id))]
    (swap! battlefield update-in [(battlefield/locate-cell row col (battlefield/length @battlefield)) :shot-by] (fn [x] player ))))

(defn is-game-over?
  "Checks whether of not there is an enemy left."
  [game]
  (let [ battlefield (lookup-battlefield game)]
    (not (some #(= {:has-enemy? true :shot-by :none} %)  battlefield))))

(defn get-next-player
  "Returns the next waited player to play"
  [game]
  (let [index (first @(:next-player-index game))]
    (:name (get (:players game) index))))

(defn is-player-in-game?
  "Checks whether or not the player is part of the given game"
  [player game]
  (->> (:players game)
       (some #(= (:name %) player))))

(defn is-player-turn?
  "Checks whether or not it's the given player's turn"
  [player game]
  (->> (get-next-player game)
       (= player)))

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
    :next-player (get-next-player game)
    :battlefield (battlefield/describe-battlefield battlefield)}))

;; Play functions
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

(defn fire
  "A player attempt to shoot an enemy.
   If an enemy is shot the fun returns :success otherwise :failure"
  [row col player game]
  (let [battlefield (lookup-battlefield game)]
    (if (found-enemy? row col battlefield) :success :failure)))

(defn play
  "Runs the fire process involving the game management"
  [row col player game-id]
  (if-let [game (lookup-game game-id)]
    (cond (is-game-over? game)                   {:error :game-over        :message "The game is over"}
          (not (is-player-in-game? player game)) {:error :player-not-found :message (str "Player " player " not found for this game")}
          (not (is-player-turn? player game))    {:error :wait-your-turm   :message (str "Player " player " not yet allowed to attack, other player should play")}
          :else
          (let [attack-result (fire row col player game)]
            (update-player-turn game)
            (if (= attack-result :success)
              (do
                (update-battlefield row col player game-id)
                {:result  :success
                 :message (str "You've sank battleship at row=" row " column=" col)})
              {:result  :failure
               :message (str "Your attack targeting ship at row=" row " column=" col " failed")})))
    {:error :game-not-found :message (str "Game with id=" game-id " not found")}))
