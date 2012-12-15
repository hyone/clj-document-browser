(ns document-browser.core
  (:use seesaw.core
        [document-browser.util :only (list-vars document)]))


;; Widgets
;; -----------------------------------------------------------

(native!)

(def group (button-group))

(def stylesheets {
  [:JFrame] {
    :title "Clojure Document Browser"
    :size [1000 :by 600]
  }
  [:#textarea] {
    :font "MONOSPACED-PLAIN-14"
    :wrap-lines? true
    :text "" 
  }
  [:#doc] {
    :selected? true
  }
})


(defn set-document-text [root s ns type]
  (if (and s ns type)
    (-> (select root [:#textarea])
        (text! (document s ns type))
        (scroll! :to :top))))

(defn apply-behaviours [root group]
  (let [lb-ns   (select root [:#listbox-ns])
        lb-var  (select root [:#listbox-var])
        doctype (fn [] (if-let [t (selection group)]
                         (id-of t)))]
    (listen lb-ns :selection
            (fn [_]
              (config! lb-var :model (list-vars (selection lb-ns)))
              (config! (select root [:#textarea]) :text "")))
    (listen lb-var :selection
            (fn [_] (set-document-text root (selection lb-var) (selection lb-ns) (doctype))))
    (listen group :selection
            (fn [_] (set-document-text root (selection lb-var) (selection lb-ns) (doctype))))
    root))

(defn apply-stylesheets [root stylesheets]
  (doseq [[sel style] stylesheets]
    (apply config! (select root sel) (reduce concat style)))
  root)


(defn make-frame [group]
  (frame :content (border-panel
                    :north  (horizontal-panel
                              :items (for [id [:source :doc]]
                                       (radio :id id
                                              :class :type
                                              :text (name id)
                                              :group group)))
                    :west   (scrollable (listbox
                                          :id :listbox-ns
                                          :model (-> (map #(.getName %) (all-ns)) sort)))
                    :center (scrollable (text :id :textarea :multi-line? true))
                    :east   (scrollable (listbox :id :listbox-var)
                                        :preferred-size [150 :by 0])
                    :vgap 5 :hgap 5 :border 5)))


(defn -main [& args]
  (invoke-later
   (-> (make-frame group)
       (apply-stylesheets stylesheets)
       (apply-behaviours group) show!)))
