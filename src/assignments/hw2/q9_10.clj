(ns assignments.hw2.q9-10
  (:require
    [assignments.hw2.utils :refer :all]
    [scicloj.hanamicloth.v1.api :as haclo]
    [tablecloth.api :as tc]))

(question "Question 9 to 10")
(question "Question 9")

(sub-question
  "9) Events $A1$ and $A2$ are such that $A1 ∪ A2 = S$ (the sample space), and $A1 ∩ A2 = ∅$ (the empty set). Find $p1$ if $P(A1) = p1$ , $P(A2) = p2$, and $3p_1 − p_2 = 0.5$.")

(md "**Given:**")
(formula "A1 ∪ A2 = S")
(formula "A1 ∩ A2 = ∅")
(formula "P(A1) = p1")
(formula "P(A2) = p_2")
(formula "3p_1 − p_2 = 0.5")

(md "**Steps:**")

(md "1. Since A1 and A2 are mutually exclusive and cover the entire sample space:")
(formula "P(A1) + P(A2) = 1")
(formula "p_1 + p_2 = 1")

(md "2. We now have a system of two equations:")
(formula "p_1 + p_2 = 1")
(formula "3p_1 - p_2 = 0.5")

(md "3. We can solve this system using linear algebra:")

(formula "A = \\begin{bmatrix} 1 & 1 \\\\ 3 & -1 \\end{bmatrix}")
(formula "x = \\begin{bmatrix} p1 \\\\ p2 \\end{bmatrix}")
(formula "b = \\begin{bmatrix} 1 \\\\ 0.5 \\end{bmatrix}")

(md "We'll solve this using LU decomposition and forward/backward substitution. Similar to Question 1:")

(comment
  (defn solve-probabilities [b1 b2]
    (let [A (dge 2 2 [1 1
                      3 -1]
                 {:layout :row})
          b (dv [b1 b2])
          LU (trf! A)
          x (mv (tri! LU) b)]
      x)))

(let [solution (solve-probabilities 1 0.5)
      p1 (first solution)
      p2 (second solution)]
  (str [(str "p1 = " p1)
        (str "p2 = " p2)]))

(md "**Answer:**")

(let [solution (solve-probabilities 1 0.5)
      p1 (first solution)]
  (answer (str "p1 = " p1)))

(md "**Verification:**
 
 Let's verify our solution satisfies both equations:")

(let [solution (solve-probabilities 1 0.5)
      p1 (first solution)
      p2 (second solution)]
  (answer (str "p1 + p2 = " (+ p1 p2)
               ",   \n"
               "3p1 - p2 = " (- (* 3 p1) p2))))

(md "**Interpretation:**
1. $p_1$ represents the probability of event $A1$ occurring.
2. $p_2$ represents the probability of event $A2$ occurring.
3. The sum of $p_1$ and $p_2$ equals 1, confirming that $A1$ and $A2$ cover the entire sample space.
4. The equation $3p_1 - p_2 = 0.5$ is satisfied by our solution.")

(md "**Conclusion:**
We have successfully found $p_1$, which satisfies all the given conditions. This solution demonstrates how mutually exclusive events that cover the entire sample space relate to each other probabilistically.")

(question "Question 10")
(sub-question
  "10) Suppose that two fair dice are tossed. What is the probability that the sum equals ten given that it exceeds eight?")

(md "**Given:**
     
 - Two fair dice are tossed
 - We need to find $P(Sum = 10 | Sum > 8)$")

(md "**Steps:**")

(md "**1. Sample space for sums exceeding 8:**")
(def sums-exceeding-8 [9 10 11 12])

(md "**2. Count the number of ways to get each sum:**")
(defn count-ways [sum]
  (count (for [d1 (range 1 7)
               d2 (range 1 7)
               :when (= (+ d1 d2) sum)]
           [d1 d2])))

(let [sums (range 2 13)
      frequencies (map count-ways sums)
      data (tc/dataset {:sum       sums
                        :frequency frequencies})]
  (haclo/layer-bar data
                   {:=x :sum :=x-title "Sum of Dice" :=mark-size 25
                    :=y :frequency :=y-title "Frequency"}))



(def ways-for-sums
  (map #(vector % (count-ways %)) sums-exceeding-8))


(md "Number of ways for each sum:")
(into [] (map (fn [[sum ways]]
                {:sum sum :ways ways}) ways-for-sums))

(md "**3. Calculate the probability of sum exceeding 8:**")
(def total-ways-exceeding-8 (reduce + (map second ways-for-sums)))
(def p-sum-exceeding-8 (/ total-ways-exceeding-8 36))

(md (str "P(Sum > 8) = " total-ways-exceeding-8 "/36 = " (float p-sum-exceeding-8)))

(md "**4. Calculate the probability of sum equals 10:**")
(def ways-sum-10 (count-ways 10))
(def p-sum-10 (/ ways-sum-10 36))

(md (str "P(Sum = 10) = " ways-sum-10 "/36 = " (float p-sum-10)))

(md "**5. Calculate the conditional probability:**")
(def p-sum-10-given-exceeding-8 (/ p-sum-10 p-sum-exceeding-8))

(md "**Answer:**")
(answer (str "P(Sum = 10 | Sum > 8) = " (float p-sum-10-given-exceeding-8)))

(md "**Verification:**")
(md "We can verify this result by calculating the probability directly from our counted ways:")
(def direct-probability (/ (count-ways 10) total-ways-exceeding-8))

(answer (str "Direct calculation: " (float direct-probability)))

(md "**Interpretation:**")
(md "1. The probability of getting a sum of 10, given that the sum exceeds 8, is about 0.4.")
(md "2. This means that if we know the sum is greater than 8, there's a 40% chance it's exactly 10.")
(md "3. This probability is higher than the unconditional probability of getting a sum of 10 (which is about 0.0833 or 8.33%) because we've eliminated all the outcomes where the sum is 8 or less.")

(md "**Conclusion:**")
(md "We've successfully calculated the conditional probability using the definition of conditional probability: P(A|B) = P(A ∩ B) / P(B). This problem demonstrates how additional information (in this case, knowing the sum exceeds 8) can significantly change the probability of an event")

