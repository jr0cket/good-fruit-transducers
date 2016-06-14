;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Fruity Transducers
;;
;; Exploring transducers at the uSwitch Clojure Dojo June 2016
;;
;; Copyright (C) 2016 by jr0cket et al.
;; License: Creative Commons Attribution Share-Alike 4.0 International
;; Original content: https://funcool.github.io/clojurescript-unraveled/
;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(ns good-fruit.core)

;; Defining a data structure that will represent our fruit, including whether that fruit is rotten or clean.

;; We have two collections of grapes, one green, one black.  Each cluster has 2 grapes on it (not a very big cluster in this example)

(def grape-clusters
  [{:grapes [{:rotten? false :clean? false}
             {:rotten? true  :clean? false}]
    :colour :green}
   {:grapes [{:rotten? true  :clean? false}
             {:rotten? false :clean? false}]}
   :colour :black])

