(ns blog-template.templates.page-template
  (:require [hiccup.page :refer [html5]]
            [blog-template.partials.Header :refer [Header]]
            [blog-template.config.parsers :refer [handle-eval-tags evaluate-tree-nodes]]))


(defn page-template [request page]
  (let [page-struct (evaluate-tree-nodes (clojure.edn/read-string page) handle-eval-tags)]
    (html5
     (Header request (:header page-struct))
     [:body
      [:div.logo "Logo"]
      [:p "Page Template"]
      [:div.body (:page page-struct)]])))
