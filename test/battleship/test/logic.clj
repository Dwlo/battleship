(ns battleship.test.logic
  (:use clojure.test
        battleship.logic))


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
                           {:has-enemy? true  :shot-by "player1"}
                           {:has-enemy? true  :shot-by :none}])]
    (testing "5x5 matrice: Fire on enemy located at row=0 col=0"
      (let [actual (attempt-attack 0 0 "plx" battlefield)]
        (is (= actual :failure))))

    (testing "5x5 matrice: Fire on enemy located at row=0 col=1"
      (let [actual (attempt-attack 0 1 "plx" battlefield)]
        (is (= actual :failure))))

    (testing "5x5 matrice: Fire on enemy located at row=0 col=2"
      (let [actual (attempt-attack 0 2 "plx" battlefield)]
        (is (= actual :success))))))
