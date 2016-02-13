(ns battleship.test.logic-test
  (:require [battleship.logic :refer :all]
            [clojure.test     :refer :all]))

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


(deftest test-get-score
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
      (let [actual (get-score battlefield)]
        (is (= actual {"player1" 3 "player2" 3 "playerX" 1 "playerX2" 2}))))

    (testing "nxn matrice: No player scored yet."
      (let [actual (get-score [{:has-enemy? true  :shot-by :none}
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

(deftest test-describe-game
  (testing "The game is not over."
    (let [actual (describe-game [{:has-enemy? true  :shot-by "player1"}
                                 {:has-enemy? false :shot-by :none}
                                 {:has-enemy? true  :shot-by :none}
                                 {:has-enemy? true  :shot-by "player1"}
                                 {:has-enemy? true  :shot-by :none}])]
      (is (= actual {:size 2, :score {"player1" 2}, :live true}))))

  (testing "The game is not over."
    (let [actual (describe-game [{:has-enemy? true  :shot-by :none}
                                 {:has-enemy? false :shot-by :none}
                                 {:has-enemy? true  :shot-by :none}
                                 {:has-enemy? true  :shot-by :none}
                                 {:has-enemy? true  :shot-by :none}])]
      (is (= actual {:size 2, :score {}, :live true}))))

  (testing "The game is over."
    (let [actual (describe-game [{:has-enemy? true  :shot-by "player1"}
                                 {:has-enemy? false :shot-by :none}
                                 {:has-enemy? true  :shot-by "player-101"}
                                 {:has-enemy? true  :shot-by "player1"}
                                 {:has-enemy? true  :shot-by "player-X"}])]
      (is (= actual {:size 2, :score {"player1" 2, "player-101" 1, "player-X" 1}, :live false})))))
