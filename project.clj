(defproject battleship "0.1.0-SNAPSHOT"
  :description "Community game: Rest Battleship"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.6"]
                 [ring/ring-json "0.2.0"]]
  :plugins [[lein-ring "0.8.8"]]
  :ring {:handler battleship.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})
