(ns battleship.test.core
  (:use clojure.test
        battleship.core))

(deftest test-compute-index
  (testing "5x5 matrice: element identified by row=0 and col=0"
    (let [actual (compute-index 0 0)]
      (is (= actual 0))))
  (testing "5x5 matrice: element identified by row=0 and col=3"
    (let [actual (compute-index 0 3)]
      (is (= actual 3))))
  (testing "5x5 matrice: element identified by row=0 and col=4"
    (let [actual (compute-index 0 4)]
      (is (= actual 4))))
  (testing "5x5 matrice: element identified by row=1 and col=0"
    (let [actual (compute-index 1 0)]
      (is (= actual 5))))
    (testing "5x5 matrice: element identified by row=1 and col=1"
    (let [actual (compute-index 1 1 )]
      (is (= actual 6))))
    (testing "5x5 matrice: element identified by row=1 and col=4"
    (let [actual (compute-index 1 4)]
      (is (= actual 9))))
    (testing "5x5 matrice: element identified by row=2 and col=0"
    (let [actual (compute-index 2 0)]
      (is (= actual 10))))
    (testing "5x5 matrice: element identified by row=3 and col=2"
    (let [actual (compute-index 3 2)]
      (is (= actual 17)))))

(deftest test-get-cell
  (let [battlefield [:a :b :c :d :e :f :g :h :i :j :k :l :m :n :o :p :q :r :s :t :u :v :w :x :y]]
    (testing "5x5 matrice: Retrieving the fist element of the battlefield"
      (let [actual (get-cell 0 0 battlefield)]
        (is (= actual :a))))
    (testing "5x5 matrice: Retrieving the last element of the battlefield"
      (let [actual (get-cell 4 4 battlefield)]
        (is (= actual :y))))
    (testing "5x5 matrice: Retrieving a middle element of the battlefield"
      (let [actual (get-cell 2 1 battlefield)]
        (is (= actual :l))))))

(deftest test-found-enemy?
  (let [battlefield [:a :b {:has-enemy? false :shot-by :none} :d :e :f :g :h :i :j :k {:has-enemy? true :shot-by :none} :m :n :o :p :q :r :s :t :u :v :w :x :y]]
    (testing "5x5 matrice: Enemy found at location 2 1"
      (let [actual (found-enemy? 2 1 battlefield)]
        (is (= actual true))))
    (testing "5x5 matrice: Enemy not found at location 0 2"
      (let [actual (found-enemy? 0 2 battlefield)]
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
                     {:has-enemy? true  :shot-by "player1"}
                     {:has-enemy? true  :shot-by :none}]]
    (testing "5x5 matrice: Current score."
      (let [actual (score battlefield)]
        (is (= actual {"player1" 3 "player2" 3 "playerX" 1 "playerX2" 2}))))

    (testing "5x5 matrice: No player scored yet."
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
