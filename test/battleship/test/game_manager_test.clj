(ns battleship.test.game-manager-test
  (:require [battleship.game-manager :refer :all]
            [clojure.test            :refer :all]))


(deftest test-generate-game-id
  (testing "Generates a random identifier."
    (let [actual (generate-game-id)]
      (is (not= actual nil))
      (is (false? (.isEmpty actual))))))

;; (deftest test-score
;;   (let [battlefield [{:has-enemy? true  :shot-by "player1"}
;;                      {:has-enemy? false :shot-by :none}
;;                      {:has-enemy? true  :shot-by :none}
;;                      {:has-enemy? false :shot-by :none}
;;                      {:has-enemy? true  :shot-by "player1"}
;;                      {:has-enemy? true  :shot-by "player2"}
;;                      {:has-enemy? true  :shot-by "player2"}
;;                      {:has-enemy? true  :shot-by "player2"}
;;                      {:has-enemy? false :shot-by :none}
;;                      {:has-enemy? false :shot-by :none}
;;                      {:has-enemy? false :shot-by :none}
;;                      {:has-enemy? true  :shot-by :none}
;;                      {:has-enemy? true  :shot-by :none}
;;                      {:has-enemy? false :shot-by :none}
;;                      {:has-enemy? false :shot-by :none}
;;                      {:has-enemy? true  :shot-by :none}
;;                      {:has-enemy? false :shot-by :none}
;;                      {:has-enemy? true  :shot-by "playerX"}
;;                      {:has-enemy? true  :shot-by "playerX2"}
;;                      {:has-enemy? true  :shot-by "playerX2"}
;;                      {:has-enemy? true  :shot-by :none}
;;                      {:has-enemy? false :shot-by :none}
;;                      {:has-enemy? false :shot-by :none}
;;                      {:has-enemy? false :shot-by :none}
;;                      {:has-enemy? true  :shot-by "player1"}]]
;;     (testing "nxn matrice: Current score."
;;       (let [actual (score battlefield)]
;;         (is (= actual {"player1" 3 "player2" 3 "playerX" 1 "playerX2" 2}))))

;;     (testing "nxn matrice: No player scored yet."
;;       (let [actual (score [{:has-enemy? true  :shot-by :none}
;;                            {:has-enemy? false :shot-by :none}
;;                            {:has-enemy? true  :shot-by :none}
;;                            {:has-enemy? false :shot-by :none}
;;                            {:has-enemy? true  :shot-by :none}
;;                            {:has-enemy? true  :shot-by :none}
;;                            {:has-enemy? true  :shot-by :none}
;;                            {:has-enemy? true  :shot-by :none}
;;                            {:has-enemy? false :shot-by :none}
;;                            {:has-enemy? false :shot-by :none}
;;                            {:has-enemy? false :shot-by :none}
;;                            {:has-enemy? true  :shot-by :none}
;;                            {:has-enemy? true  :shot-by :none}
;;                            {:has-enemy? false :shot-by :none}
;;                            {:has-enemy? false :shot-by :none}
;;                            {:has-enemy? true  :shot-by :none}
;;                            {:has-enemy? false :shot-by :none}
;;                            {:has-enemy? true  :shot-by :none}
;;                            {:has-enemy? true  :shot-by :none}
;;                            {:has-enemy? true  :shot-by :none}
;;                            {:has-enemy? true  :shot-by :none}
;;                            {:has-enemy? false :shot-by :none}
;;                            {:has-enemy? false :shot-by :none}
;;                            {:has-enemy? false :shot-by :none}
;;                            {:has-enemy? true  :shot-by :none}])]
;;         (is (= actual {}))))))
