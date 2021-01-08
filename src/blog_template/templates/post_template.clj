(ns blog-template.templates.post-template
  (:require [hiccup.page :refer [html5]]
            [blog-template.partials.Header :refer [Header]]))


(defn post-template [request page]
  (let [page-struct (clojure.edn/read-string page)]
    (html5
     (Header request (:header page-struct))
     [:body
      [:div.logo "Logo"]
      [:p "Post Template"]
      [:div.body (:page page-struct)]
      [:div#last-update (str "Last Updated on " (:last-updated (:header page-struct)))]])))
