(ns battleship.test.game-manager-test
  (:require [battleship.game-manager :refer :all]
            [clojure.test            :refer :all]))


(deftest test-generate-game-id
  (testing "Generates a random identifier."
    (let [actual (generate-game-id)]
      (is (not= actual nil))
      (is (false? (.isEmpty actual))))))

(deftest test-register-new-game
  (testing "Adding new game into empty global game context"
    (let [actual-gid1 (register-new-game [{:a :b}])
          actual-gid2 (register-new-game [{:a :b}])]
      (is (not= actual-gid1 nil))
      (is (not= actual-gid2 nil))
      (is (not= actual-gid1 actual-gid2)))))

(deftest test-terminated-games
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
