(ns document-browser.test.util
  (:use document-browser.util
        midje.sweet))


(fact "qualified-symbol"
  (qualified-symbol 'map 'clojure.core)      => 'clojure.core/map
  (qualified-symbol "hoge" 'clojure.core)    => nil
  (qualified-symbol (var map) 'clojure.core) => nil
  (qualified-symbol nil 'clojure.core)       => nil)

(fact "doc-str"
  (doc-str 'map 'clojure.core)            => (-> clojure.core/map var meta :doc)
  (doc-str 'no-such-symbol 'clojure.core) => nil?
  (doc-str nil 'clojure.core)             => nil?)

(fact "source-str"
  (source-str 'map 'clojure.core)            => string?
  (source-str 'no-such-symbol 'clojure.core) => nil
  (source-str nil 'clojure.core)             => nil)

(fact "document: should apply appropriate function"
  (document 'map 'clojure.core :doc)    => (doc-str 'map 'clojure.core)
  (document 'map 'clojure.core :source) => (source-str 'map 'clojure.core))

(fact "document: should return nil if other than :doc or :source"
  (document 'no-such-symbol 'clojure.core :doc) => nil
  (document nil 'clojure.core :doc)             => nil
  (document 'map 'clojure.core nil)             => nil)
