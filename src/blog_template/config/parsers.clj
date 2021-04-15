(ns blog-template.config.parsers
  (:require [blog-template.config.media :refer [get-image-map]]
            [clojure.string :as str]
            [stasis.core :as stasis]
            [blog-template.config.times :refer [find-published-time time-compare]]))

(defn generate-image [name]
  (let [image-map (get-image-map name)]
    [:img {:src (str "/images/" (:url image-map)) :alt (:alt image-map) :width "100%"}]))

(defn generate-single-post [url page]
  [:div.Single-post
   [:a {:href (str/replace url #"\.clj$" "/")}
    (generate-image (:primary-image page))
    [:div.Single-post-text
     [:h2 (:title (:header page))]]]])

(defn generate-posts-page []
  "This will generate a view of all your posts and sort them by 'published' field on the post file"
  (let [posts (sort-by find-published-time time-compare (stasis/slurp-directory "resources/posts" #".*\.clj$"))]
    (map (fn [page] (generate-single-post (first page) (clojure.edn/read-string (second page) ))) posts)))

(defn evaluable? [x]
  (and (vector? x)
       (= :eval (first x))))

(defn evaluate-tree-nodes [tree evaluator-fn]
  (clojure.walk/postwalk
   #(if (evaluable? %)
      (apply evaluator-fn (rest %))
      %)
   tree))

(defn handle-eval-tags [f & args]
  (case f
    :get-image (apply generate-image args)
    :all-posts (apply generate-posts-page args)))
