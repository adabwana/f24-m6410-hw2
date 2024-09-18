(ns assignments.hw2.cw6-intuition
  (:require
    [assignments.hw2.utils :refer :all]
    [scicloj.kindly.v4.kind :as kind]))


(question "EXTRA: CW6 Intuition")

(md
 "Intuition text produced by OpenAI's Wolfram module.")

(md 
 "# Intuition Behind the Inclusion-Exclusion Formula

The inclusion-exclusion principle helps solve problems where overlapping cases exist. The intuition is that when trying to count events where certain outcomes are required (like ensuring every day gets at least one call), you start by counting all the possible outcomes and then systematically remove those where some outcomes violate your requirements. However, due to overlaps in what you remove, you must adjust by adding back those overlaps. This alternating process of subtracting and adding ensures that you don't overcount or undercount any cases.

## Example: Distributing 12 Calls Over 7 Days

Let's break it down using the example of distributing 12 calls over 7 days and wanting to ensure each day gets at least one call.

### Total Number of Distributions:

If we don't care about how the calls are distributed, the total number of ways to assign 12 calls to 7 days is: $7^{12}$

Each call has 7 possibilities, one for each day. This is the starting point — all possible ways to assign the calls, without any constraints.

### Step-by-Step Reasoning for Inclusion-Exclusion:

The issue is that many of these ways result in some days getting no calls. We want to exclude these cases, which leads to the inclusion-exclusion method.

#### Step 1: Subtract the Cases Where Some Days Get No Calls

We start by subtracting the cases where at least one day gets no call. For example, if 6 days receive calls and 1 day doesn't, the number of such distributions is:
$\\binom{7}{6} * 6^{12}$

Here:

- $\\binom{7}{6}$: We choose 6 days to receive calls (1 day will not get any).
- $6^{12}$: The calls are distributed among the 6 chosen days.

We subtract this number because these are cases where not all 7 days get calls, violating the requirement.

#### Step 2: Add Back the Cases Where We Over-Subtracted

When subtracting the cases where one day is left out, we may have over-subtracted cases where two or more days are left out. For example, if 5 days receive calls and 2 days get none, we've subtracted that case twice — once for each day being left out. So, we add it back:
$\\binom{7}{5} * 5^{12}$

Here:

- $\\binom{7}{5}$: We choose 5 days to receive calls (2 days will not get any).
- $5^{12}$: The calls are distributed among the 5 chosen days.

#### Step 3: Continue Alternating

The process continues, alternating between subtraction and addition, for cases where 3, 4, and more days are left out. This ensures that overlaps are properly handled.

### Final Formula:

By the time we’ve alternated through all the cases — subtracting and adding at each step — we’ve handled all possible overlaps. The formula we use looks like this:

$7^{12} - \\binom{7}{6} * 6^{12} + \\binom{7}{5} * 5^{12} - \\binom{7}{4} * 4^{12} + \\dots + \\binom{7}{1} * 1^{12}$


### Key Intuition:

1. **First Subtract**: You start by subtracting the cases where fewer than all days are used, which includes invalid cases.
2. **Then Add Back Overlaps**: When multiple days are left out, they were subtracted too many times, so we need to add back those overlaps to avoid over-subtraction.
3. **Keep Alternating**: This ensures that each case is counted the correct number of times — neither too many nor too few.

### Visual Example (Simplified):

Imagine distributing 5 calls to 3 days:

- Total number of ways (without restrictions) = $3^5$.
- Now subtract the cases where only 2 days get calls: $\\binom{3}{2} * 2^5$.
- But, cases where only 1 day gets calls were subtracted twice, so you add back $\\binom{3}{1} * 1^5$.

This process of inclusion (adding back) and exclusion (subtracting) continues until all overlaps are handled correctly.

### Why Inclusion-Exclusion Works:

Inclusion-exclusion ensures that:

- **You don't overcount**: You subtract cases where fewer days get calls.
- **You don't undercount**: You add back cases where multiple days are excluded simultaneously.

By systematically alternating between subtraction and addition, you can correctly count the number of ways to distribute items such that no group (or day) is left out.

This principle generalizes to any problem where overlapping subsets need to be considered. In this case, it helps calculate the probability of distributing calls to ensure each day gets at least one.")
