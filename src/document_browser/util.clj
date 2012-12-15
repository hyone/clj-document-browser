(ns document-browser.util
  (:use [clojure.repl :only (source-fn)]))


(defn list-vars [ns]
  (if (symbol? ns)
    (-> (symbol ns) ns-publics keys sort)))

(defn qualified-symbol [sym ns]
  (if (and (symbol? sym) (symbol? ns))
    (symbol (name ns) (name sym))))

(defn doc-str [sym ns]
  (if (and sym ns)
    (-> (qualified-symbol sym ns) resolve meta :doc)))

(defn source-str [s ns]
  (if s (source-fn (qualified-symbol s ns))))

(defn document [s ns doc-type]
  (let [f (condp = doc-type
            :doc    doc-str
            :source source-str
            (constantly nil))]
    (f s ns)))
