(ns blog-template.partials.Social
  (:require [blog-template.config.config :refer [Site]]))

(defn Social []
  (let [social-urls (:social-urls Site)]
    [:div.Social
     [:ul
      [:li [:a {:href (:facebook social-urls) :target "_blank"}  [:i.fab.fa-facebook-f]]]
      [:li [:a {:href (:twitter social-urls) :target "_blank"}  [:i.fab.fa-twitter]]]
      [:li [:a {:href (:instagram social-urls) :target "_blank"}  [:i.fab.fa-instagram]]]
      [:li [:a {:href (:pinterest social-urls) :target "_blank"}  [:i.fab.fa-pinterest]]]]]))
