(ns dev
  (:require [scicloj.clay.v2.api :as clay]))

(defn build []
  (clay/make!
   {:format              [:quarto :html]
    :book                {:title "Homework 1"}
    :subdirs-to-sync     ["notebooks" "data"]
    :source-path         ["src/index.clj"
                          "src/assignments/hw1/utils.clj"
                          "src/assignments/hw1/q1.clj"
                          "src/assignments/hw1/q2.clj"
                          "src/assignments/hw1/q3.clj"
                          "src/assignments/hw1/q4.clj"
                          "src/assignments/hw1/q5_6.clj"
                          "src/assignments/hw1/q7_8.clj"
                          "src/assignments/hw1/q9_10.clj"
                          "src/assignments/hw1/cw6_q5.clj"]
    :base-target-path    "docs"
    :clean-up-target-dir true}))

(comment
  (build))
