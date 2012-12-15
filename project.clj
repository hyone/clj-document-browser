(defproject document-browser "1.0.0-SNAPSHOT"
  :description "Simple Clojure Documentation Browser"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [seesaw "1.4.2"]]
  :profiles {:dev {:plugins [[lein-midje "2.0.3"]]
                   :dependencies [[midje "1.4.0"]
                                  [com.stuartsierra/lazytest "1.2.3"]]}}
  :repositories { "stuartsierra-releases" "http://stuartsierra.com/maven2" }
  :main document-browser.core)
