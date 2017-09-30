---
layout: post
title:
description: Find longest zigzag subsequence such as a[i-1] < a[i] > a[i+1] or a[i-1] > a[i] < a[i+1].
date: 2017-09-25 23:55:01 +0500
category: lis
tags: [lis]
math: true
---

## Longest zigzag subsequence
Find longest zigzag subsequence $$ a_{i1}, a_{i2}, ..., a_{ik} $$ in sequence $$ a_1, a_2, ..., a_n $$, such as:
* $$ i1 < i2 < ... < ik $$
* every two adjacent elements are different
* every three adjacent elements $$ a_{i-1}, a_i, a_{i+1} $$ either $$ a_{i-1} < a_i > a_{i+1} $$ or  $$ a_{i-1} > a_i < a_{i+1} $$.

_If there are multiple answers with same length, pick one with elements that appear earlier in input._

### Solution
This is LIS (longest increasing sequence) type problem, so we will use dynamic programming.

Create 2 arrays same length as input array.
1. Contain longest zigzag subsequences, ending with i'th element and this element is increasing.
2. Contain longest zigzag subsequences, ending with i'th element and this element is decreasing.

Since we need not only to calculate the length, but also return the subsequence itself, we need to store lists for each element in the arrays.
For simplicity, we will store indexes of elements in input sequence instead of elements itself.

```java
public static int[] find(final int[] input) {
    List<List<Integer>> greaterSubsequence = new ArrayList<>();
    List<List<Integer>> smallerSubsequence = new ArrayList<>();
    List<Integer> maxSubsequence = new ArrayList<>();
    // ...
```

Using two cycles of i [0, n) and j [0, i), iterate through each element of the input sequence and check if it could continue the longest alternating subsequence.
* If input[i] > input[j], then we should check smallerSubsequence for longest subsequence (because input[i] will be increasing).
* If input[i] < input[j], then we should check greaterSubsequence for longest subsequence (because input[i] will be descending).

Next we need to compare subsequence of j'th element and the previously found subsequence for i'th element. Save better for i'th element.

```java
    // ...
    // For each element in input calculate longest zz subsequence
    // ending with this element
    for (int i = 0; i < input.length; i++) {
        greaterSubsequence.add(new ArrayList<>());
        smallerSubsequence.add(new ArrayList<>());
        for (int j = 0; j < i; j++) {
            // If current input[i] is greater than input[j],
            // look at smallerSubsequence list
            if (input[i] > input[j]) {
                List<Integer> best = pickBest(
                    greaterSubsequence.get(i),
                    smallerSubsequence.get(j));
                if (best != greaterSubsequence.get(i)) {
                    greaterSubsequence.set(i,
                        new ArrayList<>(best));
                }
            // If current input[i] is smaller than input[j],
            // look at greaterSubsequence list
            } else if (input[i] < input[j]) {
                List<Integer> best = pickBest(
                    smallerSubsequence.get(i),
                    greaterSubsequence.get(j));
                if (best != smallerSubsequence.get(i)) {
                    smallerSubsequence.set(i,
                        new ArrayList<>(best));
                }
            }
            // Equal input[i] and input[j] are skipped
        }
        // Add i'th element to both subsequence,
        // so each subsequence contain at least 1 element
        greaterSubsequence.get(i).add(i);
        smallerSubsequence.get(i).add(i);
        // ...
```

Check whether the found subsequence is the best of all. If Yes, save it separately as an intermediate result.

```java
public static int[] find(final int[] input) {
        // ...
        // Pick new longest subsequence
        maxSubsequence = pickBest(
            maxSubsequence, greaterSubsequence.get(i));
        maxSubsequence = pickBest(
            maxSubsequence, smallerSubsequence.get(i));
    }

    int[] result = new int[maxSubsequence.size()];
    for (int i = 0; i < result.length; i++) {
        result[i] = input[maxSubsequence.get(i)];
    }

    return result;
}
```

When comparing subsequences it is necessary to rely not only on the length of the subsequences, but also on the indexes of the elements (to fulfill the condition of minimum i when equal lengths).

```java
private static List<Integer> pickBest(
        final List<Integer> first, final List<Integer> second) {
    List<Integer> result = first;
    // Compare first and second sizes
    final int firstSize = result.size();
    final int secondSize = second.size();
    if (firstSize < secondSize) {
        // second have greater size
        result = second;
    } else if (firstSize == secondSize) {
        // If sizes are equal, compare elements
        for (int i = 0; i < firstSize; i++) {
            final int curFirstElement = result.get(i);
            final int curSecondElement = second.get(i);
            if (curSecondElement < curFirstElement) {
                // second have smaller elements
                result = second;
                break;
            } else if (curSecondElement > curFirstElement) {
                // first have smaller elements
                break;
            }
        }
    }
    return result;
}
```

### Proof
The algorithm always ends because it iterates only from 0 to the length of the input sequence.

The algorithm divides the initial problem into smaller tasks, thus the correctness of the algorithm is confirmed by the correctness of these parts.
For every element input[i], the longest alternating subsequence is continuing the previous longest alternating subsequence.
For first element, subsequence will contain only this element. For the next elements, best will be the continuation of the best of previous ones.
Thus, each element uses the previously calculated subsequence, starting from base case.

### Time and memory complexity
The algorithm has time complexity of $$ O(n^2) $$ because it uses a double loop over the input data.

The algorithm consumes $$ O(n^2) $$ additional memory to store the longest subsequences at each step.

### Testing
Testing strategy:
 * numbers: positive, negative, repeating
 * several same length answers, but earlier have greater indexes
 * several same length answers, but earlier have smaller indexes
 * several same length answers, but earlier have greater indexes and maximum changing
 * result in the middle
 * single element
 * empty input
