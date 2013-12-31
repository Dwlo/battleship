(ns battleship.view
  (:use hiccup.core))

(defn index-page "Displays the index page."
  []
  (html [:head
         [:title "--Battleship--"]]
        [:body
         [:br]
         [:h1 "Battleship..."]
         "boom ! fire !"
         [:bf]
         [:h1]
         [:div {:align "center"} [:img {:src "logo.png"}]]]))
