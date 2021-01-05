(ns blog-template.config.media)


(def media
  {:coming-soon {:url "coming_soon.jpg" :alt "Coming Soon!"}})

(defn get-image-map [key]
  ((keyword key) media))


