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
  (let [battle-field [:a :b :c :d :e :f :g :h :i :j :k :l :m :n :o :p :q :r :s :t :u :v :w :x :y]]
    (testing "5x5 matrice: Retrieving the fist element of the battlefield"
      (let [actual (get-cell 0 0 battle-field)]
        (is (= actual :a))))
    (testing "5x5 matrice: Retrieving the last element of the battlefield"
      (let [actual (get-cell 4 4 battle-field)]
        (is (= actual :y))))
    (testing "5x5 matrice: Retrieving a middle element of the battlefield"
      (let [actual (get-cell 2 1 battle-field)]
        (is (= actual :l))))))

(deftest test-found-enemy?
  (let [battle-field [:a :b {:has-enemy? false :shot-by :none} :d :e :f :g :h :i :j :k {:has-enemy? true :shot-by :none} :m :n :o :p :q :r :s :t :u :v :w :x :y]]
    (testing "5x5 matrice: Enemy found at location 2 1"
      (let [actual (found-enemy? 2 1 battle-field)]
        (is (= actual true))))
    (testing "5x5 matrice: Enemy not found at location 0 2"
      (let [actual (found-enemy? 0 2 battle-field)]
        (is (= actual false))))))
