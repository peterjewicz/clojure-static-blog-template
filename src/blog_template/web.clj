(ns blog-template.web
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [stasis.core :as stasis]
            [hiccup.page :refer [html5]]
            [optimus.assets :as assets]
            [optimus.link :as link]
            [optimus.export]
            [optimus.optimizations :as optimizations]
            [optimus.prime :as optimus]
            [optimus.strategies :refer [serve-live-assets]]))

(defn header [request header]
  [:head
   [:meta {:charset "utf-8"}]
   [:meta {:name "viewport"
           :content "width=device-width, initial-scale=1.0"}]
   [:meta {:name "description"
           :content (:meta-desc header)}]
   [:title (:title header)]
   [:link {:rel "stylesheet" :href (link/file-path request "/styles/main.css")}]])

(defn layout-page [request page]
  (let [page-struct (clojure.edn/read-string (str "{" page "}"))]
    (html5
     (header request (:header page-struct))
     [:body
      [:div.logo "Logo"]
      [:div.body (:page page-struct)]])))

(defn prepare-page [page req]
  (if (string? page) page (page req)))

(defn prepare-pages [pages]
  (zipmap (keys pages)
          (map #(partial prepare-page %) (vals pages))))


(defn posts-pages [pages]
  (zipmap (map #(str/replace % #"\.html$" "/") (keys pages))
          (map #(fn [req] (layout-page req %)) (vals pages))))

(defn get-raw-pages []
  (stasis/merge-page-sources
   {:public (stasis/slurp-directory "resources/public" #".*\.(html|css|js)$")
    :posts (posts-pages (stasis/slurp-directory "resources/posts" #".*\.html$"))}))

(defn get-pages []
  (prepare-pages (get-raw-pages)))

(defn get-assets []
  (assets/load-assets "public" [#".*"]))

(def app
  (optimus/wrap (stasis/serve-pages get-pages)
                get-assets
                optimizations/all
                serve-live-assets))


(def export-dir "dist")

(defn export []
  (let [assets (optimizations/all (get-assets) {})]
    (stasis/empty-directory! export-dir)
    (optimus.export/save-assets assets export-dir)
    (stasis/export-pages (get-pages) export-dir {:optimus-assets assets})))
