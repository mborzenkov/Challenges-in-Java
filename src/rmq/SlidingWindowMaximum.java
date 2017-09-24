package rmq;

import java.util.Stack;

/** This class is designed to find maximum in sliding window.
 * Input: sequence of numbers, incrementing indexes
 * Output: maximum in subsequence from leftIndex to rightIndex in O(1)
 * Also moving indexes must have amortized cost of O(1).
 */
public class SlidingWindowMaximum {

    /** Mutable container for pairs of values and maximums used in stacks in SlidingWindow. */
    private static class SlidingWindowEntry {
        private final int value;
        private final int maximum;

        private SlidingWindowEntry(final int value, final int maximum) {
            this.value = value;
            this.maximum = maximum;
        }
    }

    /** Mutable ADT representing a sequence of numbers with sliding window.
     * Sliding window is subsequence between leftIndex and rightIndex.
     * Both leftIndex and rightIndex can move only towards the end of sequence.
     * rightIndex will never exceed the last element of array and leftIndex will never exceed rightIndex.
     * When initialized, leftIndex = rightIndex = 0
     * <br>
     * This class is specifically designed to support O(1) amortized cost in all operations.
     */
    public static class SlidingWindow {

        private final int[] data;
        private final Stack<SlidingWindowEntry> pushStack;
        private final Stack<SlidingWindowEntry> popStack;
        private int leftIndex;
        private int rightIndex;

        // Rep invariant:
        //      data - original sequence
        //      pushStack - stack that pushed when adding new values to sliding window
        //      popStack - stack that popped when removing values from sliding window
        //      leftIndex - left border of sliding window
        //      rightIndex - right border of sliding window
        //
        // Abstraction function:
        //      Represents a sequence of numbers with sliding window.
        //
        // Safety from rep exposure:
        //      This class have only one accessor getMaximum, that returns immutable int.
        //      Constructor takes int array and not making defensive copying by design due to increasing of performance.
        //
        // Thread safety argument:
        //      This class is not thread safe.

        /** Creates new object with provided array.
         * This class is not making defensive copying of data. Modifying data will result in wrong answers.
         *
         * @param data sequence of numbers, must not be modified, not null, not empty
         * @throws NullPointerException if data == null
         * @throws IndexOutOfBoundsException if data is empty
         */
        public SlidingWindow(int[] data) {
            this.data = data;
            pushStack = new Stack<>();
            popStack = new Stack<>();
            leftIndex = 0;
            rightIndex = 0;
            pushStack.push(new SlidingWindowEntry(data[0], data[0]));
        }

        /** Returns current maximum in data from leftIndex to rightIndex.
         * This operation is performed in O(1).
         *
         * @return maximum in data from leftIndex to rightIndex
         */
        public int getMaximum() {
            // Maximums for each stack is stored with top element, so current maximum is max of these maximums
            if (pushStack.empty() || popStack.empty()) {
                return pushStack.empty() ? popStack.peek().maximum : pushStack.peek().maximum;
            } else {
                return Math.max(pushStack.peek().maximum, popStack.peek().maximum);
            }
        }

        /** Moves left border of sliding window to right by 1.
         * This operation have amortized cost of O(1).
         */
        public void incrementLeft() {
            if (leftIndex < rightIndex) {
                // moving left border by 1 element right
                leftIndex++;
                // if popStack is empty, copy all elements from pushStack in the opposite order
                // calculating new maximums in each iteration
                if (popStack.empty()) {
                    while (!pushStack.empty()) {
                        int element = pushStack.pop().value;
                        popStack.push(new SlidingWindowEntry(element,
                                popStack.empty() ? element : Math.max(element, popStack.peek().maximum)));
                    }
                }
                // remove top of popStack
                popStack.pop();
            }
        }

        /** Moves right border of sliding window to right by 1.
         * This operation is performed in O(1).
         */
        public void incrementRight() {
            if (rightIndex < (data.length - 1)) {
                // moving right border by 1 element right
                rightIndex++;
                // adding new element to pushStack, calculating new maximum
                pushStack.push(new SlidingWindowEntry(data[rightIndex],
                        pushStack.empty() ? data[rightIndex] : Math.max(data[rightIndex], pushStack.peek().maximum)));
            }
        }

    }

}
