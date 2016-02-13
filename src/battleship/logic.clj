(ns battleship.logic
  "This package contains the battleship's game logic."
  (:require [battleship.battlefield :refer :all]))

(defn create-game
  "Creates a new Battlefield game"
  [size]
  (initialize-battlefield size))

(defn found-enemy?
  "Predicate that tells if there is an enemy at a given location."
  [row col battlefield]
  (let [cell (select-cell row col battlefield)]
    (and (:has-enemy? cell) (= :none (:shot-by cell)))))

(defn attack
  "A player attempt to shoot an enemy.
   If an enemy is shot the fun returns :success otherwise :failure"
  [row col player game]
  (if (found-enemy? row col game) :success :failure))

(defn get-score
  "Computes the score of all players. ex: {'player1' 5 'player2' 1}"
  [battlefield]
  (frequencies (for [cell battlefield :when (not= (:shot-by cell) :none)] (:shot-by cell))))

(defn is-game-over?
  "Checks whether of not there is an enemy left."
  [battlefield]
  (not (some #(= {:has-enemy? true :shot-by :none} %)  battlefield)))

(defn show-battlefield
  [battlefield]
  (show-battlefield-status battlefield))

(defn show-enemies
  [battlefield]
  (show-enemies-in-battlefield battlefield))

(defn describe-game
  "Gives a description of the current game"
  [battlefield]
  {:size  (battlefield-length battlefield)
   :score (get-score battlefield)
   :live  (not (is-game-over? battlefield))})
