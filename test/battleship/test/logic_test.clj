(ns battleship.test.logic-test
  (:require [battleship
             [battlefield :as core]
             [logic :refer :all]]
            [clojure.test :refer :all]))

(deftest test-found-enemy?
  (let [battlefield [{:has-enemy? true :shot-by "playerX"} :b {:has-enemy? false :shot-by :none} :d :e :f :g :h :i :j :k {:has-enemy? true :shot-by :none} :m :n :o :p :q :r :s :t :u :v :w :x :y]]
    (testing "nxn matrice: Active enemy found at location 2 1"
      (let [actual (found-enemy? 2 1 battlefield)]
        (is (= actual true))))

    (testing "nxn matrice: Active enemy not found at location 0 2"
      (let [actual (found-enemy? 0 2 battlefield)]
        (is (= actual false))))

    (testing "nxn matrice: Active Enemy not found at location 0 0"
      (let [actual (found-enemy? 0 0 battlefield)]
        (is (= actual false))))))

(deftest test-attack
  (let [battlefield [{:has-enemy? true  :shot-by "player1"}
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
                     {:has-enemy? true  :shot-by "player1"}]]
    (testing "nxn matrice: Fire on enemy located at row=0 col=0"
      (let [actual (attack 0 0 "plx" battlefield)]
        (is (= actual :failure))))

    (testing "nxn matrice: Fire on enemy located at row=0 col=1"
      (let [actual (attack 0 1 "plx" battlefield)]
        (is (= actual :failure))))

    (testing "nxn matrice: Fire on enemy located at row=0 col=2"
      (let [actual (attack 0 2 "plx" battlefield)]
        (is (= actual :success))))))


;; (deftest test-show-context
;;   (testing "nxn matrices: 2 games context"
;;     (let [actual (get-games-info (atom {"game-id1" (atom [{:has-enemy? true  :shot-by "player1"}
;;                                                                {:has-enemy? false :shot-by :none}
;;                                                                {:has-enemy? true  :shot-by :none}
;;                                                                {:has-enemy? false :shot-by :none}
;;                                                                {:has-enemy? true  :shot-by "player1"}
;;                                                                {:has-enemy? true  :shot-by "player2"}
;;                                                                {:has-enemy? true  :shot-by "player2"}
;;                                                                {:has-enemy? true  :shot-by "player2"}
;;                                                                {:has-enemy? false :shot-by :none}
;;                                                                {:has-enemy? false :shot-by :none}
;;                                                                {:has-enemy? false :shot-by :none}
;;                                                                {:has-enemy? false :shot-by :none}
;;                                                                {:has-enemy? false :shot-by :none}
;;                                                                {:has-enemy? false :shot-by :none}
;;                                                                {:has-enemy? false :shot-by :none}
;;                                                                {:has-enemy? false :shot-by :none}
;;                                                                {:has-enemy? false :shot-by :none}
;;                                                                {:has-enemy? true  :shot-by "playerX"}
;;                                                                {:has-enemy? true  :shot-by "playerX2"}
;;                                                                {:has-enemy? true  :shot-by "playerX2"}
;;                                                                {:has-enemy? true  :shot-by :none}
;;                                                                {:has-enemy? false :shot-by :none}
;;                                                                {:has-enemy? false :shot-by :none}
;;                                                                {:has-enemy? false :shot-by :none}
;;                                                                {:has-enemy? true  :shot-by "player1"}])

;;                                              "game-id2" (atom [{:has-enemy? false :shot-by :none}
;;                                                                {:has-enemy? false :shot-by :none}
;;                                                                {:has-enemy? true  :shot-by "pl3"}
;;                                                                {:has-enemy? false :shot-by :none}
;;                                                                {:has-enemy? false :shot-by :none}
;;                                                                {:has-enemy? false :shot-by :none}
;;                                                                {:has-enemy? false :shot-by :none}
;;                                                                {:has-enemy? false :shot-by :none}
;;                                                                {:has-enemy? false :shot-by :none}
;;                                                                {:has-enemy? false :shot-by :none}
;;                                                                {:has-enemy? false :shot-by :none}
;;                                                                {:has-enemy? false :shot-by :none}
;;                                                                {:has-enemy? false :shot-by :none}
;;                                                                {:has-enemy? true  :shot-by "pl1"}
;;                                                                {:has-enemy? false :shot-by :none}
;;                                                                {:has-enemy? false :shot-by :none}
;;                                                                {:has-enemy? false :shot-by :none}
;;                                                                {:has-enemy? false :shot-by :none}
;;                                                                {:has-enemy? false :shot-by :none}
;;                                                                {:has-enemy? false :shot-by :none}
;;                                                                {:has-enemy? true  :shot-by "pl1"}
;;                                                                {:has-enemy? false :shot-by :none}
;;                                                                {:has-enemy? false :shot-by :none}
;;                                                                {:has-enemy? false :shot-by :none}
;;                                                                {:has-enemy? false :shot-by :none}])}))]
;;       (is (= (count actual) 2))
;;       (is (= (nth actual 0) {"game-id1" {"player1" 3 "player2" 3 "playerX" 1 "playerX2" 2 :status :running}}))
;;       (is (= (nth actual 1) {"game-id2" {"pl3" 1 "pl1" 2 :status :over}})))))

;; (deftest test-get-game-stats
;;   (testing "nxn matrices: battlefield"
;;     (let [actual (get-game-stats [{:has-enemy? true  :shot-by "player1"}
;;                                     {:has-enemy? false :shot-by :none}
;;                                     {:has-enemy? true  :shot-by :none}
;;                                     {:has-enemy? false :shot-by :none}
;;                                     {:has-enemy? true  :shot-by "player1"}
;;                                     {:has-enemy? true  :shot-by "player2"}
;;                                     {:has-enemy? true  :shot-by "player2"}
;;                                     {:has-enemy? true  :shot-by "player2"}
;;                                     {:has-enemy? false :shot-by :none}
;;                                     {:has-enemy? false :shot-by :none}
;;                                     {:has-enemy? false :shot-by :none}
;;                                     {:has-enemy? false :shot-by :none}
;;                                     {:has-enemy? false :shot-by :none}
;;                                     {:has-enemy? false :shot-by :none}
;;                                     {:has-enemy? false :shot-by :none}
;;                                     {:has-enemy? false :shot-by :none}
;;                                     {:has-enemy? false :shot-by :none}
;;                                     {:has-enemy? true  :shot-by "playerX"}
;;                                     {:has-enemy? true  :shot-by "playerX2"}
;;                                     {:has-enemy? true  :shot-by "playerX2"}
;;                                     {:has-enemy? true  :shot-by :none}
;;                                     {:has-enemy? false :shot-by :none}
;;                                     {:has-enemy? false :shot-by :none}
;;                                     {:has-enemy? false :shot-by :none}
;;                                     {:has-enemy? true  :shot-by "player1"}])]
;;       (is (= actual {:score  {"player1"  3
;;                               "player2"  3
;;                               "playerX"  1
;;                               "playerX2" 2}
;;                      :status :running})))))

(deftest test-is-game-over
  (testing "The game is not over."
    (let [actual (is-game-over? [{:has-enemy? true  :shot-by "player1"}
                                 {:has-enemy? false :shot-by :none}
                                 {:has-enemy? true  :shot-by :none}
                                 {:has-enemy? true  :shot-by "player1"}
                                 {:has-enemy? true  :shot-by :none}])]
      (is (= actual false))))

  (testing "The game is not over."
    (let [actual (is-game-over? [{:has-enemy? true  :shot-by :none}
                                 {:has-enemy? false :shot-by :none}
                                 {:has-enemy? true  :shot-by :none}
                                 {:has-enemy? true  :shot-by :none}
                                 {:has-enemy? true  :shot-by :none}])]
      (is (= actual false))))

  (testing "The game is over."
    (let [actual (is-game-over? [{:has-enemy? true  :shot-by "player1"}
                                 {:has-enemy? false :shot-by :none}
                                 {:has-enemy? true  :shot-by "player-101"}
                                 {:has-enemy? true  :shot-by "player1"}
                                 {:has-enemy? true  :shot-by "player-X"}])]
      (is (= actual true)))))


(deftest test-score
  (let [battlefield [{:has-enemy? true  :shot-by "player1"}
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
                     {:has-enemy? true  :shot-by "player1"}]]
    (testing "nxn matrice: Current score."
      (let [actual (score battlefield)]
        (is (= actual {"player1" 3 "player2" 3 "playerX" 1 "playerX2" 2}))))

    (testing "nxn matrice: No player scored yet."
      (let [actual (score [{:has-enemy? true  :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? true  :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? true  :shot-by :none}
                           {:has-enemy? true  :shot-by :none}
                           {:has-enemy? true  :shot-by :none}
                           {:has-enemy? true  :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? true  :shot-by :none}
                           {:has-enemy? true  :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? true  :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? true  :shot-by :none}
                           {:has-enemy? true  :shot-by :none}
                           {:has-enemy? true  :shot-by :none}
                           {:has-enemy? true  :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? true  :shot-by :none}])]
        (is (= actual {}))))))
