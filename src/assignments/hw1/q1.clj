(ns assignments.hw1.q1
  (:require [assignments.hw1.utils :refer :all]
            [scicloj.kindly.v4.kind :as kind]))


(question "Question 1")
(sub-question "1) Find $P(A ∩ B)$ if $P(A) = 0.2$, $P(B) = 0.4$ and $P(A|B) + 0.5P(A^c|B) = 0.75$.")

(md "**Given:**")
(formula "P(A) = 0.2,    P(B) = 0.4,    P(A|B) + 0.5P(A^c|B) = 0.75")


(md "1. **Solve P(A|B):**")
(formula "P(A|B) + 0.5(1 - P(A|B)) = 0.75")
(formula "P(A|B) + 0.5 - 0.5 P(A|B) = 0.75")
(formula "0.5 P(A|B) + 0.5 = 0.75")
(formula "0.5 P(A|B) = 0.25")
(formula "P(A|B) = 0.5")


(md "2. **Solve P(A ∩ B):**")
(formula "P(A|B) = P(A ∩ B) / P(B)")
(formula "P(A ∩ B) = P(A|B) * P(B)")
(formula "P(A ∩ B) = 0.5 * 0.4 = 0.2")


(md "***Implementation:***")
(md "We'll use linear algebra to solve the system of equations:")

(formula "x + 0.5y = 0.75")
(formula "x + y = 1")

(md "Where $x = P(A|B)$ and $y = P(A^c|B)$ and by definition $P(A|B) + P(A^c|B) = 1$")

(md "This system can be represented in matrix form as:")
(formula "Ax = b")
(md "Where:")

(formula "A = \\begin{bmatrix} 1 & 0.5 \\\\ 1 & 1 \\end{bmatrix}")
(formula "x = \\begin{bmatrix} P(A|B) \\\\ P(A^c|B) \\end{bmatrix}")
(formula "b = \\begin{bmatrix} 0.75 \\\\ 1 \\end{bmatrix}")

(md "We'll solve this using LU decomposition and forward/backward substitution:")


(comment
  (defn p-a-gvn-b [target]
    (let [A (dge 2 2 [1 0.5                                 ; 2x2 Matrix [[1 0.5] [1 1]]
                      1 1]
                 {:layout :row})
          b (dv [target 1])                                 ; Vector [target 1]
          LU (trf! A)                                       ; Perform LU decomposition
          x (mv (tri! LU) b)]                               ; Solve using forward/backward substitution
      ; Return P(A|B)
      (first x))))


(let [target 0.75 p-a-given-b (p-a-gvn-b target)]
  (kind/tex (str "P(A|B) = " p-a-given-b)))


(md "**Answer:**")


(let [p-a 0.2 p-b 0.4
      p-a-given-b (p-a-gvn-b 0.75)
      p-a-and-b (* p-a-given-b p-b)]
  (answer (str "P(A ∩ B) = " p-a-and-b)))


(md
  "**Interpretation:**

  1. When the target is 0.75, we get $P(A|B) = 0.5$.
  2. The sum $P(A|B) + P(A^c|B) = 1$ holds true.
  3. The given equation $P(A|B) + 0.5 P(A^c|B) = 0.75$ is satisfied with the calculated probabilities.")


(md
  "**Implications for Probability:**

  1. $P(A|B)$ represents the probability of event A occurring given that B has occurred.
  2. $P(A^c|B) = 1 - P(A|B)$ is the probability that A does not occur given that B has occurred.
  3. The equation $P(A|B) + 0.5 P(A^c|B) = 0.75$ combines these conditional probabilities into a weighted sum.")


(md
  "**Limitations:**

  1. The target value must be between 0.5 and 1 for the probabilities to make sense.
  2. This method assumes that all events and probabilities are valid within the context of probability theory.")


(md "**Explanation:**")
(md "We solved for $P(A|B)$ using the given equation and found it to be 0.5. Then, we calculated $P(A ∩ B)$ using the formula $P(A ∩ B) = P(A|B) * P(B)$.")

(md "**Verification:**
  
 - Check the Given Equation:")
(formula "P(A|B) + 0.5 P(A^c|B) = 0.5 + 0.5 * 0.5 = 0.75")

(md "  - Check the Sum of Probabilities:")
(formula "P(A|B) + P(A^c|B) = 0.5 + 0.5 = 1")

(md
  "**Note on Implementation:**

  We used a simple linear system solver to find $P(A|B)$. The linear algebra approach illustrates how systems of equations can be solved using matrices, even for basic probability problems.")

(md
  "**Conclusion:**

  The solution correctly finds $P(A ∩ B) = 0.2$ by first determining $P(A|B) = 0.5$ and then applying the definition of conditional probability.")