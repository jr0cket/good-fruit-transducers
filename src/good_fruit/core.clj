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

;; We want to split the grap clusters into individual grapes, discarding the rotten grapes.  The remaing grapes will be checked to see if they are clean.  We should be left with one green and one black grape.

;; pass a cluster data structure like
#_{:grapes [{:rotten? false :clean? false}
          {:rotten? true  :clean? false}]
 :colour :green}
;; we only ask for the first key's value, so colour doent matter
(defn split-cluster
"Takes a grape cluster and returns the vector of all the grapes in that cluster"
  [cluster]
  (:grapes cluster))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; testing code in the REPL

;; The grape-clusters data structure is a vector of two grape clusters.
;; To see what a grape cluster is, get the first element of that data structure
(first grape-clusters)
;; => {:grapes [{:rotten? false, :clean? false} {:rotten? true, :clean? false}], :colour :green}

;; For each cluster in grape-clusters, return just the :grapes data, ignoring the colour informtion
(split-cluster {:grapes [{:rotten? false :clean? false}
                         {:rotten? true  :clean? false}]
                :colour :green})
;; =>[{:rotten? false, :clean? false} {:rotten? true, :clean? false}]

;; end of testing code
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


(defn not-rotten
  "Given a grape, only return it if it is not rotten.  A grape is defined as {:rotten? true|false :clean? true|false}"
  [grape]
  (not (:rotten? grape)))


(defn clean-grape
  "Given a grape, updating the grapes :clean? value to true regardless of its current value.  A grape is defined as {:rotten? true|false :clean? true|false}"
  [grape]
  (assoc grape :clean? true))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; testing in REPL
(clean-grape {:rotten? false :clean? false})
;; => {:rotten? false, :clean? true}

;; end of testing code
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Functional composition using the thread last macro.
;; Each line passes its evaluate value to the next line as its last argument

;; Algorithm
;; * evaluate the name grape-clusters and return the data structure it points to.
;; * use mapcat to map the split-clusters function over each element in grape-clusters, returning 4 grapes concatinated into one collection
;; * filter the 4 grapes, dropping the grapes where :rotten? equals true, returning 2 grapes
;; * update each grape to have a :clean? value of true

(->> grape-clusters
     (mapcat split-cluster)
     (filter not-rotten)
     (map clean-grape))

;; => ({:rotten? false, :clean? true} {:rotten? false, :clean? true})

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Using partial to compose functions together

;; composing functions are read in the lisp way, so we pass the grape-clusters collection to the last composed function first
(def process-clusters
  "Takes clusters of grapes and returns only the nice ones, that have been cleaned.  Using comp, read the function from the bottom up to understand the argument."
  (comp
   (partial map clean-grape)
   (partial filter not-rotten)
   (partial mapcat split-cluster)))

(process-clusters grape-clusters)
;; => ({:rotten? false, :clean? true} {:rotten? false, :clean? true})

;; the process-clusters definition above uses the lisp way of evaluation - inside-out.

;; Here is a simple example of evaluating a maths expression from inside-out.  Each line is the same expression, but with the innermost expression replaced by its value.
(+ 2 3 (+ 4 5 (/ 24 6)))
(+ 2 3 (+ 4 5 4))
(+ 2 3 13)
18

