(ns blog-template.config.parsers
  (:require [blog-template.config.media :refer [get-image-map]]))


(defn generate-image [name]
  (let [image-map (get-image-map name)]
    [:img {:src (str "/images/" (:url image-map)) :alt (:alt image-map) :width "100%"}]))


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
    :get-image (apply generate-image args)))