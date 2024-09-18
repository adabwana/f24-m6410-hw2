(ns assignments.hw1.cw6-q5
  (:require [assignments.hw1.utils :refer :all]
            [scicloj.kindly.v4.kind :as kind]))


(question "EXTRA: CW6 Q5")

(sub-question
 "5) Your phone rings 12 times each week, the calls being randomly distributed among 7 days. What is the probability that you get at least one call each day?")

(md "This problem is equivalent to the Coupon collector's problem, as described in the [MathStackExchange answer](https://math.stackexchange.com/questions/2421875/what-is-the-probability-i-get-at-least-one-call-each-day-if-my-telephone-rings). It can be thought of as 'the probability that a random 12-letter word written with a 7-letter alphabet contains all 7 letters.'")

(md "**1. Calculate the binomial coefficient (n choose k):**")

(comment
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
             (inc i))))))))

(md "**2. Calculate the inclusion-exclusion terms:**")

(defn calculate-inclusion-exclusion-terms
  "Calculates the terms for the inclusion-exclusion principle, corresponding to the formula:
   7^12 - (7c6)6^12 + (7c5)5^12 - (7c4)4^12 + (7c3)3^12 - (7c2)2^12 + (7c1)1^12
   
   num-items: Number of items to distribute (e.g., calls)
   num-groups: Number of groups to distribute into (e.g., days)
   
   Returns a sequence of terms for the inclusion-exclusion sum."
  [num-items num-groups]
  (map (fn [k]
         (let [sign (if (odd? k) 1 -1)
               combinations (nck num-groups k)       ; This computes (7 choose k)
               ways (Math/pow (- num-groups k) num-items)] ; This computes (7-k)^12
           (* sign combinations ways)))
       (range 1 (inc num-groups))))

(md "Explanation of `calculate-inclusion-exclusion-terms`:
     
1. We iterate over k from 1 to num-groups, representing the number of groups not receiving any item.
2. For each k, we calculate:
   - `(nck num-groups k)`: The number of ways to choose k groups out of num-groups.
   - `(Math/pow (- num-groups k) num-items)`: The number of ways to distribute num-items to the remaining (num-groups - k) groups.
   - `(if (odd? k) 1 -1)`: Alternating sign for the inclusion-exclusion principle.
3. The product of these three factors gives us each term in the inclusion-exclusion sum.")


(md "**Examples of `calculate-inclusion-exclusion-terms`:**")

(md "***Example 1:*** 3 items, 2 groups")
(let [terms (calculate-inclusion-exclusion-terms 3 2)]
  (kind/md (str "Terms: " (vec terms))))

(md "**Explanation:**
- For k=1: (2 choose 1) * 1^3 * 1 = 2
- For k=2: (2 choose 2) * 0^3 * -1 = 0

 These terms correspond to the inclusion-exclusion principle as described in the MathStackExchange solution. Here, we're calculating the probability of not using all groups (days/letters), which we'll later subtract from 1 to get the desired probability.")

(md "***Example 2:*** 4 items, 3 groups")
(let [terms (calculate-inclusion-exclusion-terms 4 3)]
  (kind/md (str "Terms: " (vec terms))))

(md "**Explanation:**
     
- For k=1: (3 choose 1) * 2^4 * 1 = 48
- For k=2: (3 choose 2) * 1^4 * -1 = -3
- For k=3: (3 choose 3) * 0^4 * 1 = 0

 These terms directly relate to the MathStackExchange solution's formula: 
$7^{12} - \\binom{7}{6}6^{12} + \\binom{7}{5}5^{12} - ... + \\binom{7}{1}1^{12}$. In our function:
     
- (3 choose k) corresponds to $\\binom{7}{k}$ in the solution
- $(3-k)^4$ corresponds to $k^{12}$ in the solution (where k is the complement)
- The alternating signs match the solution's inclusion-exclusion principle

Our function generalizes this approach for any number of items (calls/letters) and groups (days/alphabet letters).")


(md "**3. Calculate the probability using inclusion-exclusion:**")

(defn coupon-probability
  "Calculates the probability that all `num-groups` (e.g., days) will be used
   when distributing `num-items` (e.g., calls) using the inclusion-exclusion principle."
  [num-items num-groups]
  (let [terms (calculate-inclusion-exclusion-terms num-items num-groups)
        total-ways (Math/pow num-groups num-items)
        excluded-ways (reduce + terms)
        probability (- 1 (/ excluded-ways total-ways))]
    probability))

(md "Explanation of `coupon-probability`:
     
1. We calculate the inclusion-exclusion terms using our `calculate-inclusion-exclusion-terms` function.
2. total-ways represents the total number of ways to distribute num-items among num-groups.
3. We sum up the terms and divide by total-ways to get the probability of not using all groups.
4. Finally, we subtract this from 1 to get the probability of using all groups.")


(md "Examples of `coupon-probability`:")
(md "***Example 1:*** 3 items, 2 groups")
(let [prob (coupon-probability 3 2)]
  (kind/md (str "Probability: " (format "%.4f" prob))))

(md "**Explanation:**
     
- Total ways: $2^3 = 8$
- Sum of terms: 2 (from previous example)
- $Probability = 1 - (2 / 8) = 0.75$
This means there's a 75% chance of using both groups having at least one item when distributing 3 items.")

(md "***Example 2:*** 4 items, 3 groups")
(let [prob (coupon-probability 4 3)]
  (kind/md (str "Probability: " (format "%.4f" prob))))

(md "**Explanation:**
     
- Total ways: $3^4 = 81$
- Sum of terms: $48 + -3 + 0 = 45$ (from previous example)
- $Probability = 1 - (45 / 81) ≈ 0.4444$
This means there's about a 44.44% chance of using all 3 groups having at least one item when distributing 4 items.")

(md "***Example 3:*** 5 items, 5 groups")
(let [prob (coupon-probability 5 5)]
  (kind/md (str "Probability: " (format "%.4f" prob))))

(md "**Explanation:**
     
- Total ways: $5^5 = 3125$
- Sum of terms: $5 * 4^5 - 10 * 3^5 + 10 * 2^5 - 5 * 1^5 = 3005$
- $Probability = 1 - (3005 / 3125) ≈ 0.0384$
     
This means there's about a 3.84% chance of using all 5 groups having at least one item when distributing 5 items.")

(md "***Example 4:*** 10 items, 3 groups")
(let [prob (coupon-probability 10 3)]
  (kind/md (str "Probability: " (format "%.4f" prob))))

(md "**Explanation:**
     
- Total ways: $3^{10} = 59049$
- Sum of terms: $3 * 2^{10} - 3 * 1^{10} = 3069$
- $Probability = 1 - (3069 / 59049) ≈ 0.9480$
     
This means there's about a 94.80% chance of using all 3 groups having at least one item when distributing 10 items.")


(md "**4. Calculate the result for 12 calls and 7 days:**")

(let [result (coupon-probability 12 7)]
  (answer (str "The probability of getting at least one call each day is approximately "
               (format "%.4f" result))))