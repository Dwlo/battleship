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
                           {:has-enemy? true  :shot-by "playerX"}
                           {:has-enemy? true  :shot-by "playerX2"}
                           {:has-enemy? true  :shot-by "playerX2"}
                           {:has-enemy? true  :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? true  :shot-by "player1"}])]
    (testing "5x5 matrice: Fire on enemy located at row=0 col=0"
      (let [actual (launch-attack 0 0 "plx" battlefield)]
        (is (= actual {:attack-status :failure :game-status :running} ))))

    (testing "5x5 matrice: Fire on enemy located at row=0 col=1"
      (let [actual (launch-attack 0 1 "plx" battlefield)]
        (is (= actual {:attack-status :failure :game-status :running}))))

    (testing "5x5 matrice: Fire on enemy located at row=0 col=2"
      (let [actual (launch-attack 0 2 "plx" battlefield)]
        (is (= actual {:attack-status :success :game-status :running}))))

    (testing "5x5 matrice: Fire on enemy located at row=0 col=2"
      (let [actual (launch-attack 4 0 "plx" battlefield)]
        (is (= actual {:attack-status :success :game-status :over}))))))

(deftest test-generate-game-id
  (testing "Generates a random identifier."
    (let [actual (generate-game-id)]
      (is (not= actual nil))
      (is (false? (.isEmpty actual))))))

(deftest test-show-context
  (testing "5x5 matrices: 2 games context"
    (let [actual (show-global-context (atom
                                {"game-id1" (atom [{:has-enemy? true  :shot-by "player1"}
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
