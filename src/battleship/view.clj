(ns battleship.view
  (:use hiccup.core))

(defn show-index "Displays the index page."
  []
  (html [:head
         [:title "--Battleship--"]]
        [:body
         [:br]
         [:h1 "Battleship..."]
         "boom ! fire !"]))
