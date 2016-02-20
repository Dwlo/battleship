(ns battleship.test.battlefield-test
  (:require [battleship.battlefield :refer :all]
            [clojure.test :refer :all]))

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
      (let [actual (show-battlefield-status battlefield)]
        (is (= actual "player1|-|-|-|player1\nplayer2|player2|player2|-|-\n-|-|-|-|-\n-|-|playerX|playerX2|playerX2\n-|-|-|-|player1"))))))

(deftest test-show-enemies-in-battlefield
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
      (let [actual (show-enemies-in-battlefield battlefield)]
        (is (= actual "E|-|E|-|E\nE|E|E|-|-\n-|E|E|-|-\nE|-|E|E|E\nE|-|-|-|E"))))))

(deftest test-describe-battlefield
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
    (testing "testing description"
      (let [actual (describe-battlefield battlefield)]
        (is (= actual [{:row 0 :col 0 :shot-by "player1"}
                       {:row 0 :col 1 :shot-by :none}
                       {:row 0 :col 2 :shot-by :none}
                       {:row 0 :col 3 :shot-by :none}
                       {:row 0 :col 4 :shot-by "player1"}
                       {:row 1 :col 0 :shot-by "player2"}
                       {:row 1 :col 1 :shot-by "player2"}
                       {:row 1 :col 2 :shot-by "player2"}
                       {:row 1 :col 3 :shot-by :none}
                       {:row 1 :col 4 :shot-by :none}
                       {:row 2 :col 0 :shot-by :none}
                       {:row 2 :col 1 :shot-by :none}
                       {:row 2 :col 2 :shot-by :none}
                       {:row 2 :col 3 :shot-by :none}
                       {:row 2 :col 4 :shot-by :none}
                       {:row 3 :col 0 :shot-by :none}
                       {:row 3 :col 1 :shot-by :none}
                       {:row 3 :col 2 :shot-by "playerX"}
                       {:row 3 :col 3 :shot-by "playerX2"}
                       {:row 3 :col 4 :shot-by "playerX2"}
                       {:row 4 :col 0 :shot-by :none}
                       {:row 4 :col 1 :shot-by :none}
                       {:row 4 :col 2 :shot-by :none}
                       {:row 4 :col 3 :shot-by :none}
                       {:row 4 :col 4 :shot-by "player1"}]))))))
