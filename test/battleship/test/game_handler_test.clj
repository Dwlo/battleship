(ns battleship.test.game-handler-test
  (:require [battleship.game-handler :refer :all]
            [clojure.test :refer :all]))

(deftest test-generate-game-id
  (testing "Generates a random identifier."
    (let [actual (generate-game-id)]
      (is (not= actual nil))
      (is (false? (.isEmpty actual))))))

(deftest test-register-new-game
  (testing "Adding new game into empty global game context"
    (let [actual-gid1 (register-game 1 [])
          actual-gid2 (register-game 2 [])]
      (is (not= actual-gid1 nil))
      (is (not= actual-gid2 nil))
      (is (not= actual-gid1 actual-gid2)))))

(deftest test-terminated-games
  (testing "nxn matrices: 3 games context, 1 running and 2 terminated"
    (let [actual (terminated-games (atom {"game-id1" {:battlefield (atom [{:has-enemy? true  :shot-by "player1"}
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
                                                                          {:has-enemy? true  :shot-by "player1"}])}

                                          "game-id2" {:battlefield (atom [{:has-enemy? false :shot-by :none}
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
                                                                          {:has-enemy? false :shot-by :none}])}

                                          "game-id3" {:battlefield (atom [{:has-enemy? false :shot-by :none}
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
                                                                          {:has-enemy? false :shot-by :none}])}}))]
      (is (= (count actual) 2))
      (is (= actual  ["game-id2" "game-id3"])))))


(deftest test-describe
  (testing "nxn matrices: 3 games context, 1 running and 2 terminated"
    (let [actual (describe-global-status {"game-id1" {:battlefield (atom [{:has-enemy? true  :shot-by "player1"}
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
                                                                          {:has-enemy? true  :shot-by "player1"}])}

                                          "game-id2" {:battlefield (atom [{:has-enemy? false :shot-by :none}
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
                                                                          {:has-enemy? false :shot-by :none}])}

                                          "game-id3" {:battlefield (atom [{:has-enemy? false :shot-by :none}
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
                                                                          {:has-enemy? false :shot-by :none}])}})]
      (is (= actual {:total-games 3
                     :live-games  {:count    1
                                   :game-ids ["game-id1"]}})))))

;; Dw
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

(deftest test-fire
  (let [game {:battlefield (atom [{:has-enemy? true  :shot-by "player1"}
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
                                  {:has-enemy? true  :shot-by "player1"}])}]
    (testing "nxn matrice: Fire on enemy located at row=0 col=0"
      (let [actual (fire 0 0 "plx" game)]
        (is (= actual :failure))))

    (testing "nxn matrice: Fire on enemy located at row=0 col=1"
      (let [actual (fire 0 1 "plx" game)]
        (is (= actual :failure))))

    (testing "nxn matrice: Fire on enemy located at row=0 col=2"
      (let [actual (fire 0 2 "plx" game)]
        (is (= actual :success))))))

(deftest test-is-game-over
  (testing "The game is not over."
    (let [actual (is-game-over? {:battlefield (atom [{:has-enemy? true  :shot-by "player1"}
                                                     {:has-enemy? false :shot-by :none}
                                                     {:has-enemy? true  :shot-by :none}
                                                     {:has-enemy? true  :shot-by "player1"}
                                                     {:has-enemy? true  :shot-by :none}])})]
      (is (= actual false))))

  (testing "The game is not over."
    (let [actual (is-game-over? {:battlefield (atom [{:has-enemy? true  :shot-by :none}
                                                     {:has-enemy? false :shot-by :none}
                                                     {:has-enemy? true  :shot-by :none}
                                                     {:has-enemy? true  :shot-by :none}
                                                     {:has-enemy? true  :shot-by :none}])})]
      (is (= actual false))))

  (testing "The game is over."
    (let [actual (is-game-over? {:battlefield (atom [{:has-enemy? true  :shot-by "player1"}
                                                     {:has-enemy? false :shot-by :none}
                                                     {:has-enemy? true  :shot-by "player-101"}
                                                     {:has-enemy? true  :shot-by "player1"}
                                                     {:has-enemy? true  :shot-by "player-X"}])})]
      (is (= actual true)))))

(deftest test-get-score
  (let [game {:battlefield (atom [{:has-enemy? true  :shot-by "player1"}
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
                                  {:has-enemy? true  :shot-by "player1"}])}]
    (testing "nxn matrice: Current score."
      (let [actual (get-score game)]
        (is (= actual {"player1" 3 "player2" 3 "playerX" 1 "playerX2" 2}))))

    (testing "nxn matrice: No player scored yet."
      (let [actual (get-score {:battlefield (atom [{:has-enemy? true  :shot-by :none}
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
                                                   {:has-enemy? true  :shot-by :none}])})]
        (is (= actual {}))))))

(deftest test-describe-game
  (testing "The game is not over."
    (let [actual (describe-game {:battlefield       (atom [{:has-enemy? true  :shot-by "player1"}
                                                           {:has-enemy? false :shot-by :none}
                                                           {:has-enemy? true  :shot-by "player1"}
                                                           {:has-enemy? true  :shot-by :none}])
                                 :players           [{:name "P1"} {:name "P2"}]
                                 :next-player-index (atom (->> (cycle (range 3))
                                                               (rest)))})]
      (is (= actual {:size 2
                     :score {"player1" 2}
                     :live true
                     :battlefield [{:row 0 :col 0 :shot-by "player1"}
                                   {:row 0 :col 1 :shot-by :none}
                                   {:row 1 :col 0 :shot-by "player1"}
                                   {:row 1 :col 1 :shot-by :none}]
                     :players     [{:name "P1"} {:name "P2"}]
                     :next-player "P2"}))))

  (testing "The game is not over."
    (let [actual (describe-game {:battlefield       (atom [{:has-enemy? true  :shot-by :none}
                                                           {:has-enemy? true  :shot-by :none}
                                                           {:has-enemy? true  :shot-by :none}
                                                           {:has-enemy? true  :shot-by :none}])
                                 :players           [{:name "P1"} {:name "P2"}]
                                 :next-player-index (atom (cycle (range 3)))})]
      (is (= actual {:size 2
                     :score {}
                     :live true
                     :battlefield [{:row 0 :col 0 :shot-by :none}
                                   {:row 0 :col 1 :shot-by :none}
                                   {:row 1 :col 0 :shot-by :none}
                                   {:row 1 :col 1 :shot-by :none}]
                     :players     [{:name "P1"} {:name "P2"}]
                     :next-player "P1"}))))

  (testing "The game is over."
    (let [actual (describe-game {:battlefield       (atom [{:has-enemy? true  :shot-by "player1"}
                                                           {:has-enemy? true  :shot-by "player-101"}
                                                           {:has-enemy? true  :shot-by "player1"}
                                                           {:has-enemy? true  :shot-by "player-X"}])
                                 :players           [{:name "P1"} {:name "P2"}]
                                 :next-player-index (atom (->> (cycle (range 3))
                                                                 (rest)))})]
      (is (= actual {:size 2
                     :score {"player1" 2 "player-101" 1 "player-X" 1}
                     :live false
                     :battlefield [{:row 0 :col 0 :shot-by "player1"}
                                   {:row 0 :col 1 :shot-by "player-101"}
                                   {:row 1 :col 0 :shot-by "player1"}
                                   {:row 1 :col 1 :shot-by "player-X"}]
                     :players     [{:name "P1"} {:name "P2"}]
                     :next-player "P2"})))))


(deftest test-get-next-player
  (testing "next player is the second player"
    (let [actual (get-next-player {:players           [{:name "P1"}
                                                       {:name "P2"}
                                                       {:name "P3"}]
                                   :next-player-index (atom (->> (cycle (range 3))
                                                                 (rest)))})]
      (is (= actual "P2")))))
