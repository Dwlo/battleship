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
         [:h2 "How to play ?"]
         how-to-play
         [:br]
         [:br]
         [:table
          [:tr
           [:td [:img {:src "battleship.gif"}]]
           [:td]
           [:td {:rowspan "3"} [:img {:src "battleship-game.png"}]]
           ]
          [:tr
           [:td]
           [:td [:br] "FIIIIRE ! BOOM !"]]
          [:tr
           [:td [:img {:src "battleship.gif"}]]
           [:td]]
          ]
         [:bf]
         [:h1]
         [:div {:align "center"}]]))
