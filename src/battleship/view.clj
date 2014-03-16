(ns battleship.view
  "This package contains the web app pages (views)."
  (:use hiccup.core))

(def how-to-play "Sorry needs to be  defined.")

(defn index-page "Displays the index page."
  []
  (html [:head
         [:title "--Battleship--"]]
        [:body
         [:h1 "Battleship... version 0.1.0"]
         "How to play ? " [:a {:href "https://github.com/Dwlo/battleship"} "check the github repository"]
         [:br]
         [:br]
         [:img {:src "cmdline-white.png"}]
         [:bf]
         [:h1]
         [:div {:align "center"}]]))
