(ns assignments.hw1.q7-8
  (:require
    [assignments.hw1.utils :refer :all]
    [fastmath.core :as m]))

(question "Question 7 to 8")

(question "Question 7")
(sub-question
  "7) Suppose that three fair dice are tossed. Let $A_i$ be the event that a 6 shows on the $i^{th}$ die, i = 1, 2, 3. Does $P(A_1 ∪ A_2 ∪ A_3) = 1/2$? Explain.")

(md
  "To solve this problem, we'll use the following approach:")

(md "**Step 1:** Probability of getting a 6 on a single die")
(formula "P(A_i) = 1/6")

(md "**Step 2:** Probability of not getting a 6 on a single die")
(formula "P(A_i^c) = 1 - P(A_i) = 5/6")

(md "**Step 3:** Probability of getting at least one 6 in three tosses, using the complement rule")
(formula "P(A_1 ∪ A_2 ∪ A_3) = 1 - P(A_1^c ∩ A_2^c ∩ A_3^c)")
(formula "P(A_1 ∪ A_2 ∪ A_3) = 1 - (5/6)^3")


(defn probability-at-least-one-six [num-dice]
  (- 1 (m/pow (/ 5 6) num-dice)))


(let [result (probability-at-least-one-six 3)]
  (answer (str "$P(A_1 ∪ A_2 ∪ A_3) = "
               (m/approx result 4)
               "$")))


(md "**Step 4:** Compare the result with 1/2")
(let [result (probability-at-least-one-six 3)
      difference (- result 0.5)]
  (md (str "The difference between the calculated probability and 1/2 is " (m/approx difference 4))))

(answer "No, P(A_1 ∪ A_2 ∪ A_3) ≠ 1/2. The probability is slightly less than 1/2.")

(md "**Explanation:**
 The probability of getting at least one 6 when tossing three fair dice is approximately 0.4213, which is not equal to 1/2 (0.5). The difference is about 0.0787 or 7.87%.

 This result can be interpreted as follows:
     
 1. The probability of getting at least one 6 in three tosses is higher than one might intuitively expect.
 
 2. It's more likely to get at least one 6 than to get no 6s at all in three tosses.
 
 3. The probability is closer to 1/2 than it is to either 0 or 1, which might explain why someone might guess it to be exactly 1/2.")


(md "The probability is slightly less than 1/2 because:

 1. The chance of getting at least one 6 increases with each additional die roll.
 
 2. However, the increase is not linear, and it gets smaller with each additional die.
 
 3. With three dice, we're just short of the 50% mark, but adding more dice would eventually bring us closer to (but never exactly) 100%.")


(question "Question 8")
(sub-question "8) A box contains 3 red balls and 2 white balls. Two balls are drawn at random without replacement. Let A be the event that the first ball drawn is red, and B the event that the second ball drawn is red. Are A and B independent? Explain.")

(md "To determine if events A and B are independent, we need to check if P(A ∩ B) = P(A) * P(B). Let's calculate these probabilities:")

(md "**Step 1:** Calculate P(A)")
(formula "P(A) = 3/5")

(md "**Step 2:** Calculate P(B)")
(formula "P(B) = (2/4) * (3/5) + (3/4) * (2/5) = 3/5")

(md "**Step 3:** Calculate P(A ∩ B)")
(formula "P(A ∩ B) = (3/5) * (2/4) = 3/10")

(md "**Step 4:** Check if P(A ∩ B) = P(A) * P(B)")
(formula "P(A) * P(B) = (3/5) * (3/5) = 9/25")

(defn are-events-independent? [p-a p-b p-a-and-b]
  (m/approx-eq p-a-and-b (* p-a p-b)))

(let [p-a (/ 3 5)
      p-b (/ 3 5)
      p-a-and-b (/ 3 10)
      independent? (are-events-independent? p-a p-b p-a-and-b)]
  (answer (str "A and B are "
               (if independent? "" "not ")
               "independent.")))

(md "**Explanation:**

 - **$P(A)$:** The probability that the first ball is red is $3/5$.
 - **$P(B)$:** The probability that the second ball is red is also $3/5$.
 - **$P(A ∩ B)$:** The probability that both balls are red is $3/10$.
 - Since $P(A ∩ B) = 3/10$ and $P(A) * P(B) = 9/25$, and these are not equal, events $A$ and $B$ are **not independent**.")

(md "**Conclusion:**

 - The outcome of the first draw affects the probability of the second draw.
 - Therefore, events $A$ and $B$ are dependent.")
