(ns assignments.hw1.q3
  (:require [assignments.hw1.utils :refer :all]
            [clojure.set :as set]
            [fastmath.core :as m]))

(question "Question 3")
(sub-question "3) Find two sigma fields with probability assignments for the sample space Ω = {1, 2, 3}.")


(md "A sigma field (also known as a sigma-algebra) is a collection of subsets of the sample space that satisfies certain properties. For Ω = {1, 2, 3}, we can define two different sigma fields:")

(md "**Sigma Field 1: Trivial Sigma Field**")
(md "Probability assignment for the trivial sigma field:")
(md "Let's verify that these sigma fields satisfy the required properties:")


(defn verify-sigma-field [sigma-field]
  (and
    ; Contains empty set and entire sample space
    (contains? sigma-field #{})
    (contains? sigma-field #{1 2 3})
     ; Closed under complementation
    (every? #(contains? sigma-field (set/difference #{1 2 3} %)) sigma-field)
     ; Closed under countable unions (for finite sets, this is equivalent to being closed under all unions)
    (every? #(contains? sigma-field (apply set/union %))
            (power-set sigma-field))))


(let [trivial-sigma-field #{#{} #{1 2 3}}
      verified? (verify-sigma-field trivial-sigma-field)]
  (answer (str
            "Trivial sigma field is valid: "
            verified?)))

(md "Let's also verify that the probability assignments are valid:")

(defn verify-prob-assign [prob-assignment sample-space]
  (let [power-set (power-set sample-space)
        singleton-events (set (map #(set [%]) sample-space))
        singleton-probs (map #(get prob-assignment % 0.0) singleton-events)
        includes-singletons? (some #(contains? prob-assignment %) singleton-events)]
    (and
      ; All probabilities are between 0 and 1
      (every? #(<= 0 % 1) (vals prob-assignment))
       ; All sets in the probability assignment are in the power set
      (every? #(contains? power-set %) (keys prob-assignment))
       ; Probability of the empty set is 0
      (m/approx-eq 0.0 (get prob-assignment #{} 0.0))
       ; Probability of the entire sample space is 1
      (m/approx-eq 1.0 (get prob-assignment sample-space))
       ; If singleton events are included, sum of their probabilities equals 1
      (or (not includes-singletons?)
          (m/approx-eq 1.0 (reduce + singleton-probs))))))



(md "Probability assignment verification results:")
(let [sample-space #{1 2 3}
      trivial-probs {#{}      0.0
                     #{1 2 3} 1.0}
      verified? (verify-prob-assign trivial-probs sample-space)]
  (answer (str
            "Trivial sigma field probability assignment is valid: "
            verified?)))


(md "**Sigma Field 2: Power Set**")

(comment
  (defn power-set
    "Returns the power set of a given collection (set or vector)."
    [coll]
    (set (map set (combo/subsets (seq coll))))))


(def power-set-sigma-field
  (power-set #{1 2 3}))


(md "Probability assignment for the power set sigma field (example):")

(let [verified? (verify-sigma-field power-set-sigma-field)]
  (answer (str
            "Power set sigma field is valid: "
            verified?)))

(let [sample-space #{1 2 3}
      power-set-probs
      {#{}      0.0
       #{1}     0.3
       #{2}     0.3
       #{3}     0.4
       #{1 2}   0.6
       #{1 3}   0.7
       #{2 3}   0.7
       #{1 2 3} 1.0}
      verified? (verify-prob-assign power-set-probs sample-space)]
  (answer (str
            "Power set sigma field probability assignment is valid: "
            verified?)))

(md "These sigma fields and their respective probability assignments satisfy the required properties of sigma fields and probability measures")

(md "The trivial sigma field satisfies the properties because:
1. It contains the empty set and the entire sample space.
2. The complement of the empty set is the entire sample space, and vice versa.
3. Any union of these sets is either the empty set or the entire sample space.

The power set sigma field satisfies the properties because:
1. It contains all possible subsets of the sample space, including the empty set and the entire sample space.
2. For any set in the power set, its complement is also in the power set.
3. Any union of sets in the power set is also in the power set.")

