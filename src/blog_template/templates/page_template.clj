(ns blog-template.templates.page-template
  (:require [hiccup.page :refer [html5]]
            [blog-template.partials.Header :refer [Header]]))


(defn post-template [request page]
  (let [page-struct (clojure.edn/read-string (str "{" page "}"))]
    (html5
     (Header request (:header page-struct))
     [:body
      [:div.logo "Logo"]
      [:div.body (:page page-struct)]])))