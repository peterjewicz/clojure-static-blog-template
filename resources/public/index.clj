{:header {
          :title "Index"
          :meta-desc "Indx Page"}
 :page [:div.Page
         [:h1 {:onclick "fireAlert()"} "Welcome to my site - click me to do something"]
         [:a {:href "/posts"} "Click for posts page"]
         [:eval
           :get-recent-posts 2]
         [:p "this is your home page"]]}
