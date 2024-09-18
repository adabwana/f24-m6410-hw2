(ns assignments.hw1.q5-6
  (:require
    [assignments.hw1.utils :refer :all]
    [fastmath.core :as m]))

(question "Question 5 to 6")

(question "Question 5")
(sub-question "5) Let A and B be two events such that $P((A ∪ B)^c) = 0.6$ and $P(A ∩ B) = 0.1$. Let E be the event that either A or B but not both occurs. Find $P(E|A ∪ B)$.")

(md "This problem invites us to explore the intricacies of set theory and conditional probability. Let's break it down step-by-step, much like we would approach a complex sociocultural phenomenon.")

(md "First, let's define our events and their known probabilities:")
(formula "P((A ∪ B)^c) = 0.6")
(formula "P(A ∩ B) = 0.1")

(md "Now, let's calculate $P(A ∪ B)$ using the complement rule:")
(formula "P(A ∪ B) = 1 - P((A ∪ B)^c) = 1 - 0.6 = 0.4")

(md "Event (E) is defined as (A ∪ B) setminus (A ∩ B), which is the symmetric difference or `XOR` of A and B.")
(md "We can calculate $P(E)$ as follows:")
(formula "P(E) = P(A ∪ B) - P(A ∩ B) = 0.4 - 0.1 = 0.3")

(md "Now, to find (P(E|A ∪ B)), we use the definition of conditional probability:")
(formula "P(E|A ∪ B) = \\frac{P(E ∩ (A ∪ B))}{P(A ∪ B)}")

(md "Since $E | A ∪ B$, we have $P(E ∩ (A ∪ B)) = P(E)$.")
(formula "P(E|A ∪ B) = \\frac{P(E)}{P(A ∪ B)} = \\frac{0.3}{0.4} = 0.75")
(md "**Note:** E is a subset of A ∪ B because E represents the elements that are in A or B but not both, which is necessarily a subset of all elements in A or B.")


(defn calculate-conditional-probability
  [p-a-union-b-complement p-a-intersect-b]
  (let [p-a-union-b (- 1 p-a-union-b-complement)
        p-e (- p-a-union-b p-a-intersect-b)
        p-e-given-a-union-b (/ p-e p-a-union-b)]
    p-e-given-a-union-b))

(let [result (calculate-conditional-probability 0.6 0.1)]
  (answer (str "$P(E|A ∪ B) = " (m/approx result 4) "$")))

(question "Question 6")

(sub-question "6) Let A and B be two events  on a sample space S. Suppose that $P(A) = 0.4$, $P(B) = 0.5$, and $P(A ∩ B) = 0.1$. What is the probability that A or B but not both occur?")

(md "This problem is asking for the probability of the symmetric difference of events A and B, also known as the exclusive OR (XOR) of A and B. Let's approach this step-by-step.")

(md "Given:")
(formula "P(A) = 0.4")
(formula "P(B) = 0.5")
(formula "P(A ∩ B) = 0.1")

(md "The probability that A or B but not both occur can be calculated as:")
(formula "P(A △ B) = P(A ∪ B) - P(A ∩ B)")

(md "First, let's calculate P(A ∪ B) using the addition rule of probability:")
(formula "P(A ∪ B) = P(A) + P(B) - P(A ∩ B)")
(formula "P(A ∪ B) = 0.4 + 0.5 - 0.1 = 0.8")

(md "Now we can calculate the probability of A or B but not both:")
(formula "P(A △ B) = P(A ∪ B) - P(A ∩ B) = 0.8 - 0.1 = 0.7")

(defn calculate-symmetric-difference
  [p-a p-b p-a-intersect-b]
  (let [p-a-union-b (+ p-a p-b (- p-a-intersect-b))
        p-symmetric-difference (- p-a-union-b p-a-intersect-b)]
    p-symmetric-difference))

(let [result (calculate-symmetric-difference 0.4 0.5 0.1)]
  (answer (str "The probability that A or B but not both occur is " (m/approx result 4))))
