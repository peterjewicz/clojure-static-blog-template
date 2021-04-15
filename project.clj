(defproject blog-template "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [stasis "2.5.0"]
                 [ring "1.8.2"]
                 [hiccup "1.0.5"]
                 [optimus "0.20.2"]
                 [optimus-sass "0.0.3"]
                 [clj-time "0.15.2"]]
  :repl-options {:init-ns blog-template.core}
  :ring {:handler blog-template.web/app}
  :profiles {:dev {:plugins [[lein-ring "0.12.5"]]}}
  :aliases {"build-site" ["run" "-m" "blog-template.web/export"]})
