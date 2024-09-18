(ns assignments.hw1.utils
  (:require
   [clojure.math.combinatorics :as combo]
   [scicloj.kindly.v4.api :as kindly]
   [scicloj.kindly.v4.kind :as kind]
   (uncomplicate.neanderthal
    [core :refer [mv]]
    [linalg :refer [trf! tri!]]
    [native :refer [dge dv]])))

(kind/md "## Utils")

^:kindly/hide-code
(def md (comp kindly/hide-code kind/md))
^:kindly/hide-code
(def question (fn [content] ((comp kindly/hide-code kind/md) (str "## " content "\n---"))))
^:kindly/hide-code
(def sub-question (fn [content] ((comp kindly/hide-code kind/md) (str "#### *" content "*"))))
^:kindly/hide-code
(def sub-sub (fn [content] ((comp kindly/hide-code kind/md) (str "***" content "***"))))
^:kindly/hide-code
(def answer (fn [content] (kind/md (str "> <span style=\"color: black; font-size: 1.5em;\">**" content "**</span>"))))
^:kindly/hide-code
(def formula (comp kindly/hide-code kind/tex))


;; P(A|B) (problem specific context)
(defn p-a-gvn-b [target]
  (let [A (dge 2 2 [1 0.5
                    1 1] {:layout :row})                    ; 2x2 Matrix [[1 0.5] [1 -1]]
        b (dv [target 1])                                   ; Vector [target 0]
        LU (trf! A)                                         ; Perform LU decomposition
        x (mv (tri! LU) b)]                                 ; Solve using forward/backward substitution
    ; Return P(A|B)
    (first x)))

(let [target 0.75]
  (p-a-gvn-b target))


;; Helper functions
(defn joint-probability
  "Helper function to calculate the joint probability of a subset of events."
  [probs subset]
  (reduce * (map #(nth probs %) subset)))


(let [probs [0.45 0.4 0.24] subset [0 1]]                   ; Male and glasses
  (joint-probability probs subset))


(defn subsets
  "Generate all non-empty subsets of a set of indices."
  [s]
  (filter seq (combo/subsets s)))


(subsets (range 3))


(defn power-set
  "Returns the power set of a given collection (set or vector)."
  [coll]
  (set (map set (combo/subsets (seq coll)))))


(power-set (range 3))

(defn probability-at-least-one
  "Calculate the probability of at least one event occurring given a collection of probabilities.
     Uses the inclusion-exclusion principle for any number of events.
     
     Parameters:
     probs - A collection of individual probabilities.
     
     Returns:
     The probability of at least one event occurring."
  [probs]
  (let [p-none (reduce * (map #(- 1 %) probs))]
    (- 1 p-none)))


(comment
  (defn probability-at-least-one-3
    "Calculate the probability of at least one event occurring given individual probabilities.
   Correctly applies the inclusion-exclusion principle for three events.

   Parameters:
   p1 - Probability of the first event
   p2 - Probability of the second event
   p3 - Probability of the third event

   Returns:
   The probability of at least one event occurring."
    [p1 p2 p3]
    (+ (- (+ p1 p2 p3)
          (+ (* p1 p2) (* p1 p3) (* p2 p3)))
       (* p1 p2 p3)))

  (let [p-male 0.45 p-glasses 0.4 p-senior 0.24]
    (probability-at-least-one-3 p-male p-glasses p-senior))

  ;; P([A1, ..., An])
  ;; works with 3
  (defn probability-at-least-one
    "Calculate the probability of at least one event occurring given a collection of probabilities.
   Uses the inclusion-exclusion principle for any number of events.
   
   Parameters:
   probs - A collection of individual probabilities.
   
   Returns:
   The probability of at least one event occurring."
    [probs]
    (let [n (count probs)
          indices (range n)
          subset-indices (subsets indices)]
      ;; Apply inclusion-exclusion
      (reduce +
              (map (fn [subset]
                     (let [joint-prob (joint-probability probs subset)]
                       ;; Alternate addition and subtraction based on the size of the subset
                       (* (if (odd? (count subset)) 1 -1) joint-prob)))
                   ;(filter seq subset-indices)
                   subset-indices)))))

(defn solve-probabilities [b1 b2]
  (let [A (dge 2 2 [1 1
                    3 -1]
               {:layout :row})
        b (dv [b1 b2])
        LU (trf! A)
        x (mv (tri! LU) b)]
    x))

(md "2. Next, we'll calculate Stirling numbers of the second kind:")

(defn stirlings2
  "Calculates Stirling number of the second kind S(n, k),
   which is the number of ways to partition n items into k non-empty sets."
  [n k]
  (cond
    (and (= n 0) (= k 0)) 1
    (or (= k 0) (= n 0)) 0
    :else (+ (* k (stirlings2 (dec n) k)) (stirlings2 (dec n) (dec k)))))

(md "Let's examine some examples of Stirling numbers of the second kind:")

(md "Example 1: S(3, 2) - Ways to partition 3 items into 2 non-empty sets")

(let [result (stirlings2 3 2)]
  (kind/md (str "S(3, 2) = " result)))

(md "This means there are 3 ways to partition 3 items into 2 non-empty sets:
1. {1, 2}, {3}
2. {1, 3}, {2}
3. {2, 3}, {1}")

(md "Example 2: S(4, 2) - Ways to partition 4 items into 2 non-empty sets")
(let [result (stirlings2 4 2)]
  (kind/md (str "S(4, 2) = " result)))

(md "There are 7 ways to partition 4 items into 2 non-empty sets:
1. {1, 2, 3}, {4}
2. {1, 2, 4}, {3}
3. {1, 3, 4}, {2}
4. {2, 3, 4}, {1}
5. {1, 2}, {3, 4}
6. {1, 3}, {2, 4}
7. {1, 4}, {2, 3}")

(md "Example 3: S(4, 3) - Ways to partition 4 items into 3 non-empty sets")
(let [result (stirlings2 4 3)]
  (kind/md (str "S(4, 3) = " result)))
(md "There are 6 ways to partition 4 items into 3 non-empty sets:
1. {1}, {2}, {3, 4}
2. {1}, {3}, {2, 4}
3. {1}, {4}, {2, 3}
4. {2}, {3}, {1, 4}
5. {2}, {4}, {1, 3}
6. {3}, {4}, {1, 2}")

(md "These examples demonstrate how Stirling numbers of the second kind represent the number of ways to partition a set of items into a specific number of non-empty subsets. This concept is crucial for our probability calculation, as it helps us count the number of ways to distribute calls across days.")

(md "3. Now, we'll implement the generalized inclusion-exclusion principle for distribution problems:")


(comment
  (defn nck
    "Calculates the binomial coefficient (n choose k).
   n: total number of items
   k: number of items to choose"
    [n k]
    (/ (reduce * (range 1 (inc n)))
       (* (reduce * (range 1 (inc k)))
          (reduce * (range 1 (inc (- n k))))))))

(defn nck
    "Calculates the binomial coefficient (n choose k) using iterative and recusive method.
   n: total number of items
   k: number of items to choose"
    [n k]
    (if (or (< k 0) (> k n))
      0
      (let [k (min k (- n k))] ; Take advantage of symmetry
        (loop [result 1N, i 0]
          (if (= i k)
            result
            (recur
             (/ (* result (- n i)) (inc i))
             (inc i)))))))


(defn probability-distribution
  "Calculates the probability that all groups (of size `num-groups`) receive at least one item
   when distributing `num-items` items randomly across the groups."
  [num-items num-groups]
  (let [total-ways (Math/pow num-groups num-items) ;; Total number of ways to distribute items
        ;; Sum of cases where at least one group gets no item
        ways-without-full-distribution
        (reduce +
                (for [k (range 1 (inc num-groups))] ;; Iterate over k groups being empty
                  (* (nck num-groups k) (stirlings2 num-items (- num-groups k)))))]
    ;; Calculate and return the probability
    (- 1 (/ ways-without-full-distribution total-ways))))

(md "4. Finally, we'll calculate the result for 12 calls and 7 days:")

(let [result (probability-distribution 12 7)]
  (answer (str "The probability of getting at least one call each day is approximately " (format "%.4f" result))))
