(ns battleship.test.web-handler-test
  (:require [battleship.web-handler :refer :all]
            [clojure.test :refer :all]
            [ring.mock.request :refer :all]))

(deftest test-app
  (testing "main route"
    (let [response (app (request :get "/"))]
      (is (= (:status response) 200))))

  (testing "not-found route"
    (let [response (app (request :get "/invalid"))]
      (is (= (:status response) 404)))))
