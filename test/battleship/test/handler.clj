(ns battleship.test.handler
  (:use clojure.test
        ring.mock.request
        battleship.handler))

(deftest test-app
  (testing "main route"
    (let [response (app (request :get "/"))]
      (is (= (:status response) 200))
      (is (= (:body response) "<br/><h1>Battleship ... </h1> boom ! fire !"))))

  (testing "not-found route"
    (let [response (app (request :get "/invalid"))]
      (is (= (:status response) 404)))))

(deftest test-generate-game-id
  (testing "Generates a random identifier."
    (let [actual (generate-game-id)]
      (is (not= actual nil))
      (is (false? (.isEmpty actual))))))
