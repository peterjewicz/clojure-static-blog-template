(ns blog-template.web
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [stasis.core :as stasis]
            [optimus.assets :as assets]
            [optimus.export]
            [optimus.optimizations :as optimizations]
            [optimus.prime :as optimus]
            [optimus.strategies :refer [serve-live-assets]]
            [blog-template.templates.page-template :refer [page-template]]
            [blog-template.templates.post-template :refer [post-template]]))


(defn prepare-page [page req]
  (if (string? page) page (page req)))

(defn prepare-pages [pages]
  (zipmap (keys pages)
          (map #(partial prepare-page %) (vals pages))))

(defn pages-pages [pages]
  (zipmap (map #(str/replace % #"\.clj$" "/") (keys pages))
          (map #(fn [req] (page-template req %)) (vals pages))))

(defn posts-pages [pages]
  (zipmap (map #(str/replace % #"\.clj$" "/") (keys pages))
          (map #(fn [req] (post-template req %)) (vals pages))))

(defn index-page [pages]
  "special case for our index page - need the .html or it treats it like other pages"
  (zipmap (map #(str/replace % #"\.clj$" ".html") (keys pages))
          (map #(fn [req] (post-template req %)) (vals pages))))

(defn get-raw-pages []
  (stasis/merge-page-sources
   {:public (index-page (stasis/slurp-directory "resources/public" #".*\.clj$"))
    :pages (pages-pages (stasis/slurp-directory "resources/pages" #".*\.clj$"))
    :posts (posts-pages (stasis/slurp-directory "resources/posts" #".*\.clj$"))}))

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
