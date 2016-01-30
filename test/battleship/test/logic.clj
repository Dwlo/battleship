(ns battleship.test.logic
  (:require [battleship.core :as core])
  (:use clojure.test
        battleship.logic))


(deftest test-register-new-game
  (let [battlefields (atom {})]
    (testing "Adding new game into empty global game context"
      (let [actual (register-new-game battlefields)]
        (is (not= actual nil))
        (is (= (count @battlefields) 1))))
    (testing "Adding new game into an not empty global game context"
      (let [actual (register-new-game battlefields)]
        (is (not= actual nil))
        (is (= (count @battlefields) 2))))))

(deftest test-shoot-enemy
  (let [battlefield (atom [{:has-enemy? true  :shot-by "player1"}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? true  :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? true  :shot-by "player1"}
                           {:has-enemy? true  :shot-by "player2"}
                           {:has-enemy? true  :shot-by "player2"}
                           {:has-enemy? true  :shot-by "player2"}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? true  :shot-by :none}
                           {:has-enemy? true  :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? true  :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? true  :shot-by "playerX"}
                           {:has-enemy? true  :shot-by "playerX2"}
                           {:has-enemy? true  :shot-by "playerX2"}
                           {:has-enemy? true  :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? true  :shot-by "player1"}])]
    (testing "nxn matrice: Shoot enemy in cell: row=2 col=1"
      (let [actual (shoot-enemy 2 1 "plx" battlefield)]
        (is (= (:shot-by (core/select-cell 2 1 actual)) "plx" ))))))

(deftest test-attempt-attack
  (let [battlefield (atom [{:has-enemy? true  :shot-by "player1"}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? true  :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? true  :shot-by "player1"}
                           {:has-enemy? true  :shot-by "player2"}
                           {:has-enemy? true  :shot-by "player2"}
                           {:has-enemy? true  :shot-by "player2"}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? true  :shot-by :none}
                           {:has-enemy? true  :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? true  :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? true  :shot-by "playerX"}
                           {:has-enemy? true  :shot-by "playerX2"}
                           {:has-enemy? true  :shot-by "playerX2"}
                           {:has-enemy? true  :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? true  :shot-by "player1"}])]
    (testing "nxn matrice: Fire on enemy located at row=0 col=0"
      (let [actual (attempt-attack 0 0 "plx" battlefield)]
        (is (= actual :failure))))

    (testing "nxn matrice: Fire on enemy located at row=0 col=1"
      (let [actual (attempt-attack 0 1 "plx" battlefield)]
        (is (= actual :failure))))

    (testing "nxn matrice: Fire on enemy located at row=0 col=2"
      (let [actual (attempt-attack 0 2 "plx" battlefield)]
        (is (= actual :success))))))

(deftest test-launck-attack
  (let [battlefield (atom [{:has-enemy? true  :shot-by "player1"}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? true  :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? true  :shot-by "player1"}
                           {:has-enemy? true  :shot-by "player2"}
                           {:has-enemy? true  :shot-by "player2"}
                           {:has-enemy? true  :shot-by "player2"}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? true  :shot-by "player1"}
                           {:has-enemy? true  :shot-by "playerX2"}
                           {:has-enemy? true  :shot-by "playerX2"}
                           {:has-enemy? true  :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? true  :shot-by "player1"}])]
    (testing "nxn matrice: Fire on enemy located at row=0 col=0"
      (let [actual (fire 0 0 "plx" battlefield)]
        (is (= actual {:fire-status :failure :game-status :running :score {"player1" 4 "player2" 3 "playerX2" 2}} ))))

    (testing "nxn matrice: Fire on enemy located at row=0 col=1"
      (let [actual (fire 0 1 "plx" battlefield)]
        (is (= actual {:fire-status :failure :game-status :running :score {"player1" 4 "player2" 3 "playerX2" 2}}))))

    (testing "nxn matrice: Fire on enemy located at row=0 col=2"
      (let [actual (fire 0 2 "plx" battlefield)]
        (is (= actual {:fire-status :success :game-status :running :score {"player1" 4 "player2" 3 "playerX2" 2 "plx" 1}}))))

    (testing "nxn matrice: Fire on enemy located at row=4 col=0"
      (let [actual (fire 4 0 "plx" battlefield)]
        (is (= actual {:fire-status :success :game-status :over :score {"player1" 4 "player2" 3 "playerX2" 2 "plx" 2}}))))))

(deftest test-generate-game-id
  (testing "Generates a random identifier."
    (let [actual (generate-game-id)]
      (is (not= actual nil))
      (is (false? (.isEmpty actual))))))

(deftest test-show-context
  (testing "nxn matrices: 2 games context"
    (let [actual (get-games-info (atom {"game-id1" (atom [{:has-enemy? true  :shot-by "player1"}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? true  :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? true  :shot-by "player1"}
                                                               {:has-enemy? true  :shot-by "player2"}
                                                               {:has-enemy? true  :shot-by "player2"}
                                                               {:has-enemy? true  :shot-by "player2"}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? true  :shot-by "playerX"}
                                                               {:has-enemy? true  :shot-by "playerX2"}
                                                               {:has-enemy? true  :shot-by "playerX2"}
                                                               {:has-enemy? true  :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? true  :shot-by "player1"}])

                                             "game-id2" (atom [{:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? true  :shot-by "pl3"}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? true  :shot-by "pl1"}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? true  :shot-by "pl1"}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}])}))]
      (is (= (count actual) 2))
      (is (= (nth actual 0) {"game-id1" {"player1" 3 "player2" 3 "playerX" 1 "playerX2" 2 :status :running}}))
      (is (= (nth actual 1) {"game-id2" {"pl3" 1 "pl1" 2 :status :over}})))))

(deftest test-terminated-gamnes
  (testing "nxn matrices: 3 games context, 1 running and 2 terminated"
    (let [actual (terminated-games (atom {"game-id1" (atom [{:has-enemy? true  :shot-by "player1"}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? true  :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? true  :shot-by "player1"}
                                                            {:has-enemy? true  :shot-by "player2"}
                                                            {:has-enemy? true  :shot-by "player2"}
                                                            {:has-enemy? true  :shot-by "player2"}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? true  :shot-by "playerX"}
                                                            {:has-enemy? true  :shot-by "playerX2"}
                                                            {:has-enemy? true  :shot-by "playerX2"}
                                                            {:has-enemy? true  :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? true  :shot-by "player1"}])

                                          "game-id2" (atom [{:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? true  :shot-by "pl3"}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? true  :shot-by "pl1"}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? true  :shot-by "pl1"}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}])

                                          "game-id3" (atom [{:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? true  :shot-by "pl3"}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? true  :shot-by "pl1"}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? true  :shot-by "pl1"}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}])}))]
      (is (= (count actual) 2))
      (is (= actual  ["game-id2" "game-id3"])))))

(deftest test-clean-up
  (testing "nxn matrices: 3 games context, 1 running and 2 terminated"
    (let [actual (clean-up (atom { "game-id1" (atom [{:has-enemy? true  :shot-by "player1"}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? true  :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? true  :shot-by "player1"}
                                               {:has-enemy? true  :shot-by "player2"}
                                               {:has-enemy? true  :shot-by "player2"}
                                               {:has-enemy? true  :shot-by "player2"}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? true  :shot-by "playerX"}
                                               {:has-enemy? true  :shot-by "playerX2"}
                                               {:has-enemy? true  :shot-by "playerX2"}
                                               {:has-enemy? true  :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? true  :shot-by "player1"}])

                             "game-id2" (atom [{:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? true  :shot-by "pl3"}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? true  :shot-by "pl1"}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? true  :shot-by "pl1"}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}])

                             "game-id3" (atom [{:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? true  :shot-by "pl3"}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? true  :shot-by "pl1"}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? true  :shot-by "pl1"}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}])}))]
      (is (= (count actual) 1))
      (is (= (first (keys actual)) "game-id1"))
      (is (= @(first (vals actual)) [{:has-enemy? true  :shot-by "player1"}
                                     {:has-enemy? false :shot-by :none}
                                     {:has-enemy? true  :shot-by :none}
                                     {:has-enemy? false :shot-by :none}
                                     {:has-enemy? true  :shot-by "player1"}
                                     {:has-enemy? true  :shot-by "player2"}
                                     {:has-enemy? true  :shot-by "player2"}
                                     {:has-enemy? true  :shot-by "player2"}
                                     {:has-enemy? false :shot-by :none}
                                     {:has-enemy? false :shot-by :none}
                                     {:has-enemy? false :shot-by :none}
                                     {:has-enemy? false :shot-by :none}
                                     {:has-enemy? false :shot-by :none}
                                     {:has-enemy? false :shot-by :none}
                                     {:has-enemy? false :shot-by :none}
                                     {:has-enemy? false :shot-by :none}
                                     {:has-enemy? false :shot-by :none}
                                     {:has-enemy? true  :shot-by "playerX"}
                                     {:has-enemy? true  :shot-by "playerX2"}
                                     {:has-enemy? true  :shot-by "playerX2"}
                                     {:has-enemy? true  :shot-by :none}
                                     {:has-enemy? false :shot-by :none}
                                     {:has-enemy? false :shot-by :none}
                                     {:has-enemy? false :shot-by :none}
                                     {:has-enemy? true  :shot-by "player1"}])))))

(deftest test-get-game-stats
  (testing "nxn matrices: battlefield"
    (let [actual (get-game-stats [{:has-enemy? true  :shot-by "player1"}
                                    {:has-enemy? false :shot-by :none}
                                    {:has-enemy? true  :shot-by :none}
                                    {:has-enemy? false :shot-by :none}
                                    {:has-enemy? true  :shot-by "player1"}
                                    {:has-enemy? true  :shot-by "player2"}
                                    {:has-enemy? true  :shot-by "player2"}
                                    {:has-enemy? true  :shot-by "player2"}
                                    {:has-enemy? false :shot-by :none}
                                    {:has-enemy? false :shot-by :none}
                                    {:has-enemy? false :shot-by :none}
                                    {:has-enemy? false :shot-by :none}
                                    {:has-enemy? false :shot-by :none}
                                    {:has-enemy? false :shot-by :none}
                                    {:has-enemy? false :shot-by :none}
                                    {:has-enemy? false :shot-by :none}
                                    {:has-enemy? false :shot-by :none}
                                    {:has-enemy? true  :shot-by "playerX"}
                                    {:has-enemy? true  :shot-by "playerX2"}
                                    {:has-enemy? true  :shot-by "playerX2"}
                                    {:has-enemy? true  :shot-by :none}
                                    {:has-enemy? false :shot-by :none}
                                    {:has-enemy? false :shot-by :none}
                                    {:has-enemy? false :shot-by :none}
                                    {:has-enemy? true  :shot-by "player1"}])]
      (is (= actual {:score  {"player1"  3
                              "player2"  3
                              "playerX"  1
                              "playerX2" 2}
                     :status :running})))))
