(defproject battleship "0.1.0-SNAPSHOT"
  :description         "Community game: Rest Battleship"
  :url                 "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure       "1.8.0"]
                 [compojure                 "1.1.8"]
                 [ring/ring-json            "0.3.1"]
                 [hiccup                    "1.0.4"]
                 [javax.servlet/servlet-api "2.5"]]
  :plugins [[lein-ring "0.8.11"]]
  :ring {:handler battleship.web-handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})
