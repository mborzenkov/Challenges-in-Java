package lis;

import java.util.ArrayList;
import java.util.List;

/** Find longest zigzag subsequence.
 * @see LongestZigZagSubsequence#find(int[])
 */
public class LongestZigZagSubsequence {

    /** Find longest zigzag subsequence.
     *
     * @param input source array of numbers, not null
     *
     * @return longest zigzag subsequence such as a[i-1] < a[i] > a[i+1] or a[i-1] > a[i] < a[i+1].
     *     If there are multiple answers with same length, algorithm picks one with elements
     *     that appear earlier in input.
     *
     * @throws NullPointerException if input == null
     */
    public static int[] find(final int[] input) {

        // All three lists contain indexes of elements from input, not actual values
        // List of longest zz subsequences ending with i'th element, where this element is increasing (2 1 3)
        List<List<Integer>> greaterSubsequence = new ArrayList<>();
        // List of longest zz subsequences ending with i'th element, where this element is decreasing (1 3 2)
        List<List<Integer>> smallerSubsequence = new ArrayList<>();
        // Current longest zz subsequence
        List<Integer> maxSubsequence = new ArrayList<>();

        // For each element in input calculate longest zz subsequence ending with this element
        for (int i = 0; i < input.length; i++) {

            greaterSubsequence.add(new ArrayList<>());
            smallerSubsequence.add(new ArrayList<>());

            for (int j = 0; j < i; j++) {
                // If current input[i] is greater than input[j], look at smallerSubsequence list
                if (input[i] > input[j]) {
                    List<Integer> best = pickBest(greaterSubsequence.get(i), smallerSubsequence.get(j));
                    if (best != greaterSubsequence.get(i)) {
                        greaterSubsequence.set(i, new ArrayList<>(best));
                    }
                // If current input[i] is smaller than input[j], look at greaterSubsequence list
                } else if (input[i] < input[j]) {
                    List<Integer> best = pickBest(smallerSubsequence.get(i), greaterSubsequence.get(j));
                    if (best != smallerSubsequence.get(i)) {
                        smallerSubsequence.set(i, new ArrayList<>(best));
                    }
                }
                // Equal input[i] and input[j] are skipped
            }

            // Add i'th element to both subsequence, so each subsequence contain at least 1 element
            greaterSubsequence.get(i).add(i);
            smallerSubsequence.get(i).add(i);

            // Pick new longest subsequence
            maxSubsequence = pickBest(maxSubsequence, greaterSubsequence.get(i));
            maxSubsequence = pickBest(maxSubsequence, smallerSubsequence.get(i));
        }

        int[] result = new int[maxSubsequence.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = input[maxSubsequence.get(i)];
        }

        return result;
    }

    /** Picks longest zigzag subsequence from those presented in the input parameters.
     * Return picked list, not a copy.
     *
     * @param first first zigzag subsequence
     * @param second second zigzag subsequence
     *
     * @return subsequence with greatest length
     *      or if all subsequences have equal length, return subsequence with smallest i'th element
     *      or if all elements are equal, return first
     *
     * @throws NullPointerException if any of parameters is null
     */
    private static List<Integer> pickBest(final List<Integer> first, final List<Integer> second) {
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

}
