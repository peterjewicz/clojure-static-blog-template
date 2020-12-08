(ns blog-template.partials.Header
  (:require [optimus.link :as link]))

(defn Header [request header]
  [:head
   [:meta {:charset "utf-8"}]
   [:meta {:name "viewport"
           :content "width=device-width, initial-scale=1.0"}]
   [:meta {:name "description"
           :content (:meta-desc header)}]
   [:title (:title header)]
   [:link {:rel "stylesheet" :href (link/file-path request "/styles/main.css")}]])