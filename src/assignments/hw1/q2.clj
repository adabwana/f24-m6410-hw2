(ns assignments.hw1.q2
  (:require
    [assignments.hw1.utils :refer :all]
    [fastmath.core :as m]))

(question "Question 2")
(md "**Consider three types of students: male, wearing glasses, senior in a high school.**")
(sub-question
  "2.1) If a principal claims that P(male)=0.45; P(glasses)=0.4; P(senior)=0.24, can that be a legitimate probability assignment? Explain")
(md "To determine if this is a legitimate probability assignment, we need to check if the sum of probabilities is less than or equal to 1.")


(let [p-male 0.45 p-glasses 0.4 p-senior 0.24
      total-prob (+ p-male p-glasses p-senior)]
  (answer
    (str "Sum of probabilities: " (m/approx total-prob 2))))


(md
  "This is not a legitimate probability assignment because:

  1. These events (being male, wearing glasses, and being a senior) are not mutually exclusive. A student can belong to more than one category.

  2. For non-mutually exclusive events, simply adding individual probabilities can result in a sum greater than 1, which is what we see here.

  3. This sum exceeding 1 violates the rule of probability for mutually exclusive events.")


(sub-question
  "2.2) How to assign a probability to students in the high school so that the statement of the principal is valid?")
(md
  "To assign probabilities that make the principal's statement valid, we need to consider the following:

  1. The given probabilities are for individual characteristics, not mutually exclusive events.

  2. We need to account for overlaps between these characteristics.

  3. We can use the principle of inclusion-exclusion to calculate the probability of a student having at least one of these characteristics")


(comment
  (defn probability-at-least-one
    "Calculate the probability of at least one event occurring using inclusion-exclusion. Assumes independence between events.
    
     Parameters:
     probs - A collection of individual probabilities.
     
     Returns:
     The probability of at least one event occurring."
    [probs]
    (let [p-none (reduce * (map #(- 1 %) probs))]
      (- 1 p-none))))

(let [p-male 0.45 p-glasses 0.4 p-senior 0.24
      p-at-least-one (probability-at-least-one [p-male p-glasses p-senior])]
  (answer
    (str
       "P(male OR glasses OR senior) = " (m/approx p-at-least-one 4) ";   &nbsp;"
       "P(none of the characteristics) = " (m/approx (- 1 p-at-least-one) 4))))

(md
  "This assignment is valid because:

  1. It maintains the individual probabilities stated by the principal.
  2. It accounts for potential overlaps between categories.
  3. The total probability for all students (with and without these characteristics) equals 1.")

(md "To make the principal's statement valid, we need to interpret these probabilities as the chance of a student having at least one of these characteristics. The probability that a student has none of these characteristics is the complement of the probability calculated above.")

(md
  "**Note:** This calculation assumes independence between the characteristics, which may not reflect reality. In practice, there could be correlations between these traits that would require more detailed data to model accurately.")