package lis;

/** This class is solving stacking boxes problem.
 * One box can be placed in another if every dimension of first box is strictly less than of other box.
 * Input: array of Box objects, sorted ASC by dimensions
 * Output: size of maximum possible stack of boxes
 */
public class NestingBoxes {

    /** Immutable ADT representing a box as 3D figure. */
    public static class Box {

        private final int height;
        private final int width;
        private final int length;

        // Rep invariant:
        //      height - height of box, positive
        //      width - width of box, positive
        //      length - length of box, positive
        //
        // Abstraction function:
        //      Represents a 3-dimensional box.
        //
        // Safety from rep exposure:
        //      All fields are immutable and declared final. No mutators.
        //
        // Thread safety argument:
        //      This class is thread safe.

        /** Creates new box with specified dimensions.
         *
         * @param height height of box, >0
         * @param width width of box, >0
         * @param length length of box, >0
         *
         * @throws IllegalArgumentException if height or width or length <= 0
         */
        public Box(final int height, final int width, final int length) {
            this.height = height;
            this.width = width;
            this.length = length;
        }

        /** Check if this box can hold otherBox.
         * One box can hold another only if all dimensions of 1st is greater than 2nd.
         *
         * @param otherBox other box to put in this box
         *
         * @return true if all dimensions of this box is greater than otherBox, false otherwise
         *
         * @throws NullPointerException if otherBox is null
         */
        public boolean canHold(final Box otherBox) {
            return height > otherBox.height && width > otherBox.width && length > otherBox.length;
        }

        /** Check if two boxes are equal.
         * Two boxes are equal if all of their dimensions are equal.
         *
         * @param object other box for checking
         *
         * @return true if this box equals to object box
         */
        @Override
        public boolean equals(final Object object) {
            if (this == object) {
                return true;
            }
            if (object == null || getClass() != object.getClass()) {
                return false;
            }
            Box otherBox = (Box) object;
            return height == otherBox.height && width == otherBox.width && length == otherBox.length;
        }

        @Override
        public int hashCode() {
            int result = height;
            result = 31 * result + width;
            result = 31 * result + length;
            return result;
        }

        @Override
        public String toString() {
            return String.format(
                    "Box (height=%s, width=%s, length=%s)", this.height, this.width, this.length);
        }

    }

    /** Finds the size of maximum possible stack of boxes.
     * Correctness of result is guaranteed only if boxes are sorted.
     *
     * @param boxes not null, array of boxes sorted ASC by dimensions:
     *              height of every other box is equal or bigger than previous
     *              and for every box height >= width >= length
     *
     * @return size of maximum possible stack of boxes
     *
     * @throws NullPointerException if boxes == null
     */
    public static int getMaxStack(final Box[] boxes) {

        // Array of maximum stacks that i'th box can hold
        int[] maxStackForBox = new int[boxes.length];
        // Resulting maximum stack
        int maxStack = 0;

        // For each box calculate maximum stack that it can hold
        for (int i = 0; i < boxes.length; i++) {
            for (int j = 0; j < i; j++) {
                if (boxes[i].canHold(boxes[j])) {
                    // Maximum stack for box[i] is holding some box[j] with maximum stack
                    if (maxStackForBox[i] < maxStackForBox[j]) {
                        maxStackForBox[i] = maxStackForBox[j];
                    }
                }
            }
            // Adding current i'th box to stack (so for each box there are at least 1 box in stack)
            maxStackForBox[i]++;
            // Calculating new max
            maxStack = Math.max(maxStack, maxStackForBox[i]);
        }

        return maxStack;
    }

}
