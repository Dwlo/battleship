(ns battleship.test.core
  (:use clojure.test
        battleship.core))

(deftest test-battlefield-length
  (testing "battlefield length"
    (let [battlefield [:a :b :c :d :e :f :g :h :i :j :k :l :m :n :o :p :q :r :s :t :u :v :w :x :y]
          actual      (battlefield-length battlefield)]
      (is (= actual 5)))))


(deftest test-locate-cell
  (testing "nxn matrice: element identified by row=0 and col=0"
    (let [actual (locate-cell 0 0 5)]
      (is (= actual 0))))

  (testing "nxn matrice: element identified by row=0 and col=3"
    (let [actual (locate-cell 0 3 5)]
      (is (= actual 3))))

  (testing "nxn matrice: element identified by row=0 and col=4"
    (let [actual (locate-cell 0 4 5)]
      (is (= actual 4))))

  (testing "nxn matrice: element identified by row=1 and col=0"
    (let [actual (locate-cell 1 0 5)]
      (is (= actual 5))))

  (testing "nxn matrice: element identified by row=1 and col=1"
    (let [actual (locate-cell 1 1 5)]
      (is (= actual 6))))

  (testing "nxn matrice: element identified by row=1 and col=4"
    (let [actual (locate-cell 1 4 5)]
      (is (= actual 9))))

  (testing "nxn matrice: element identified by row=2 and col=0"
    (let [actual (locate-cell 2 0 5)]
      (is (= actual 10))))

  (testing "nxn matrice: element identified by row=3 and col=2"
    (let [actual (locate-cell 3 2 5)]
      (is (= actual 17)))))

(deftest test-select-cell
  (let [battlefield [:a :b :c :d :e :f :g :h :i :j :k :l :m :n :o :p :q :r :s :t :u :v :w :x :y]]
    (testing "nxn matrice: Retrieving the fist element of the battlefield"
      (let [actual (select-cell 0 0 battlefield)]
        (is (= actual :a))))

    (testing "nxn matrice: Retrieving the last element of the battlefield"
      (let [actual (select-cell 4 4 battlefield)]
        (is (= actual :y))))

    (testing "nxn matrice: Retrieving a middle element of the battlefield"
      (let [actual (select-cell 2 1 battlefield)]
        (is (= actual :l))))))

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

(deftest test-battlefield-string
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
    (testing "nxn matrice: draw ."
      (let [actual (battlefield-string battlefield)]
        (is (= actual "player1|-|-|-|player1\nplayer2|player2|player2|-|-\n-|-|-|-|-\n-|-|playerX|playerX2|playerX2\n-|-|-|-|player1"))))))
