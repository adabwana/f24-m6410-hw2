(ns assignments.hw2.q4
  (:require
    [assignments.hw2.utils :refer :all]
    [fastmath.core :as m]))

(question "Question 4")
(sub-question "4.1) If P(A|B) < P(A), show that P(B|A) < P(B).")

(md "To prove this, we'll use the definition of conditional probability and algebraic manipulation:")


(formula "P(A|B) = P(A ∩ B) / P(B)")
(formula "P(B|A) = P(A ∩ B) / P(A)")


(md "**Given:** $P(A|B) < P(A)$")
(md "**Step 1:** Express the inequality using the definition of conditional probability")
(formula "P(A ∩ B) / P(B) < P(A)")
(md "**Step 2:** Multiply both sides by $P(B)$")
(formula "P(A ∩ B) < P(A) * P(B)")
(md "**Step 3:** Divide both sides by $P(A)$")
(formula "P(A ∩ B) / P(A) < P(B)")
(md "**Step 4:** Recognize the left side as $P(B|A)$")
(formula "P(B|A) < P(B)")


(answer
  "Thus, we have shown that if $P(A|B) < P(A)$, then $P(B|A) < P(B)$.")


(sub-question "4.2) If A and B are mutually exclusive, find P(A|B). If A and B are independent, find P(A|B).")


(md "**Case 1: A and B are mutually exclusive**")
(md "Mutually exclusive events have no overlap, meaning $A ∩ B = ∅$")
(formula "P(A|B) = P(A ∩ B) / P(B) = 0 / P(B) = 0")

(md "**Case 2: A and B are independent**")
(md "For independent events, $P(A ∩ B) = P(A) * P(B)$")
(formula "P(A|B) = P(A ∩ B) / P(B) = (P(A) * P(B)) / P(B) = P(A)")

(answer "If A and B are mutually exclusive: $P(A|B) = 0$")
(answer "If A and B are independent: $P(A|B) = P(A)$")

(md "**Implementation:**")

(defn demonstrate-conditional-probability-inequality [p-a p-b p-a-and-b]
  (let [p-a-given-b (/ p-a-and-b p-b)
        p-b-given-a (/ p-a-and-b p-a)]
    (md (str "Given:"))
    (md (str "P(A) = " p-a))
    (md (str "P(B) = " p-b))
    (md (str "P(A ∩ B) = " p-a-and-b))
    (md (str "P(A|B) = " (m/approx p-a-given-b 4)))
    (md (str "P(B|A) = " (m/approx p-b-given-a 4)))
    (md (str "P(A|B) < P(A): " (< p-a-given-b p-a)))
    (md (str "P(B|A) < P(B): " (< p-b-given-a p-b)))))

(md "Demonstration of the proof:")
(demonstrate-conditional-probability-inequality 0.5 0.4 0.1)


(md "In this example:

 - P(A) = 0.5
 - P(B) = 0.4
 - P(A ∩ B) = 0.1
 - P(A|B) = P(A ∩ B) / P(B) = 0.1 / 0.4 = 0.25

 Since P(A|B) = 0.25 < P(A) = 0.5, this example satisfies the condition P(A|B) < P(A).")


(defn conditional-probability [p-a p-b intersection type]
  (case type
    :mutually-exclusive 0
    :independent p-a
    (/ intersection p-b)))

(md "Demonstration for mutually exclusive events:")
(let [p-a 0.3 p-b 0.4]
  (answer (str "P(A|B) for mutually exclusive events: "
               (conditional-probability p-a p-b 0 :mutually-exclusive))))

(md "Demonstration for independent events:")
(let [p-a 0.3 p-b 0.4]
  (answer (str "P(A|B) for independent events: "
               (conditional-probability p-a p-b (* p-a p-b) :independent))))

(md "**Conclusion:**")
(md "We have demonstrated both algebraically and numerically that:
1. If P(A|B) < P(A), then P(B|A) < P(B).
2. For mutually exclusive events, P(A|B) = 0.
3. For independent events, P(A|B) = P(A).")

(md "These results are fundamental in understanding the relationships between events in probability theory.")


