(ns blog-template.config.times
  (:require [clj-time.core :as time]
            [clj-time.format :as f]))


(def custom-formatter (f/formatter "yyyy-MM-dd'T'HH:mm:ss"))

(defn find-published-time [post]
  (if (not (string? post))
    (-> post
        (second)
        (clojure.edn/read-string)
        (get :header)
        (get :published))
    post))

(defn time-compare [x y]
  (let [first-publish (f/parse custom-formatter (find-published-time x))
        second-publish (f/parse custom-formatter (find-published-time y))]
    (time/after? first-publish second-publish)))