package lis;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/** Test LongestZigZagSubsequence. */
public class LongestZigZagSubsequenceTest {

    /* Testing strategy:
     *      numbers: positive, negative, repeating
     *      several same length answers, but earlier have greater indexes
     *      several same length answers, but earlier have smaller indexes
     *      several same length answers, but earlier have greater indexes and maximum changing
     *      result in the middle
     *      single element
     *      empty
     */

    // covers: numbers negative
    @Test
    public void testNegativeNumbers() {
        final int[] input = new int[] { -1, -4, -2, -3, -5, -8, -6, -7, -9, -10 };
        final int[] expected = new int[] { -1, -4, -2, -8, -6, -7 };
        assertTrue(Arrays.equals(expected, LongestZigZagSubsequence.find(input)));
    }

    // covers: numbers positive
    //         several same length answers, but earlier have smaller indexes
    @Test
    public void testPositiveSequence() {
        final int[] input = new int[] { 1, 4, 2, 3, 5, 8, 6, 7, 9, 10 };
        final int[] expected = new int[] { 1, 4, 2, 8, 6, 7 };
        assertTrue(Arrays.equals(expected, LongestZigZagSubsequence.find(input)));
    }

    // covers: numbers repeating
    //         several same length answers, but earlier have greater indexes and maximum changing
    @Test
    public void testChangingMaximum() {
        final int[] input = new int[] { 1, 17, 5, 10, 13, 15, 10, 5 };
        final int[] expected = new int[] { 1, 17, 5, 10, 5 };
        assertTrue(Arrays.equals(expected, LongestZigZagSubsequence.find(input)));
    }

    // covers: numbers repeating
    //         several same length answers, but earlier have greater indexes
    @Test
    public void testCorrectResultAppearLater() {
        final int[] input = new int[] { 1, 17, 5, 10, 13, 15, 10, 5, 16, 8 };
        final int[] expected = new int[] { 1, 17, 5, 10, 5, 16, 8 };
        assertTrue(Arrays.equals(expected, LongestZigZagSubsequence.find(input)));
    }

    // covers: result in the middle
    @Test
    public void testMiddleSequence() {
        final int[] input = new int[] { 1, 2, 4, 3, 2 };
        final int[] expected = new int[] { 1, 4, 3 };
        assertTrue(Arrays.equals(expected, LongestZigZagSubsequence.find(input)));
    }

    // covers: single element
    @Test
    public void testOneElement() {
        final int[] input = new int[] { 100 };
        final int[] expected = new int[] { 100 };
        assertTrue(Arrays.equals(expected, LongestZigZagSubsequence.find(input)));
    }

    // covers: empty
    @Test
    public void testEmpty() {
        final int[] input = new int[] {};
        final int[] expected = new int[] {};
        assertTrue(Arrays.equals(expected, LongestZigZagSubsequence.find(input)));
    }

}
