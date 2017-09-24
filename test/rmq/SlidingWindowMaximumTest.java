package rmq;

import org.junit.Test;
import rmq.SlidingWindowMaximum.SlidingWindow;

import static org.junit.Assert.assertEquals;

/* Test SlidingWindowMaximum. */
public class SlidingWindowMaximumTest {

    /* Testing strategy:
     *      content: positive nums, repeating nums, negative nums
     *      right border reveals new maximum
     *      left border exceed last maximum
     *      right border try to exceed size of sequence
     *      left border try to exceed right border
     *      when initialized max is first
     */

    // covers: positive nums
    @Test
    public void testNormalExample() {
        final SlidingWindow example = new SlidingWindowMaximum.SlidingWindow(new int[] {1, 4, 2, 3, 5, 8, 6, 7, 9, 10});
        example.incrementRight();
        assertEquals(4, example.getMaximum());
        example.incrementRight();
        assertEquals(4, example.getMaximum());
        example.incrementLeft();
        assertEquals(4, example.getMaximum());
        example.incrementRight();
        assertEquals(4, example.getMaximum());
        example.incrementRight();
        assertEquals(5, example.getMaximum());
        example.incrementRight();
        assertEquals(8, example.getMaximum());
        example.incrementLeft();
        assertEquals(8, example.getMaximum());
        example.incrementLeft();
        assertEquals(8, example.getMaximum());
        example.incrementLeft();
        assertEquals(8, example.getMaximum());
        example.incrementRight();
        assertEquals(8, example.getMaximum());
        example.incrementLeft();
        assertEquals(8, example.getMaximum());
        example.incrementLeft();
        assertEquals(6, example.getMaximum());
    }

    // covers: repeating nums
    @Test
    public void testRepeatingNumbers() {
        final SlidingWindow example = new SlidingWindowMaximum.SlidingWindow(new int[] {1, 4, 4, 1});
        assertEquals(1, example.getMaximum());
        example.incrementRight();
        assertEquals(4, example.getMaximum());
        example.incrementRight();
        assertEquals(4, example.getMaximum());
        example.incrementRight();
        assertEquals(4, example.getMaximum());
        example.incrementLeft();
        assertEquals(4, example.getMaximum());
        example.incrementLeft();
        assertEquals(4, example.getMaximum());
        example.incrementLeft();
        assertEquals(1, example.getMaximum());

    }

    // covers: negative nums
    @Test
    public void testNegativeNumbers() {
        final SlidingWindow example = new SlidingWindowMaximum.SlidingWindow(new int[] {-2, -4, -4, -1});
        assertEquals(-2, example.getMaximum());
        example.incrementRight();
        assertEquals(-2, example.getMaximum());
        example.incrementRight();
        assertEquals(-2, example.getMaximum());
        example.incrementRight();
        assertEquals(-1, example.getMaximum());
        example.incrementLeft();
        assertEquals(-1, example.getMaximum());
        example.incrementLeft();
        assertEquals(-1, example.getMaximum());
        example.incrementLeft();
        assertEquals(-1, example.getMaximum());
    }

    // covers: when initialized max is first
    @Test
    public void testInitialization() {
        final SlidingWindow example = new SlidingWindowMaximum.SlidingWindow(new int[] {0, 4, 2});
        assertEquals(0, example.getMaximum());
    }

    // covers right border reveals new maximum
    @Test
    public void testRightBorderRevealsNewMaximum() {
        final SlidingWindow example = new SlidingWindowMaximum.SlidingWindow(new int[] {1, 4, 2});
        example.incrementRight();
        assertEquals(4, example.getMaximum());
    }

    // covers left border try to exceed right border
    @Test
    public void testLeftBorderExceedRight() {
        final SlidingWindow example = new SlidingWindowMaximum.SlidingWindow(new int[] {1, 4, 2});
        example.incrementLeft();
        assertEquals(1, example.getMaximum());
    }

    // covers right border try to exceed size of sequence
    @Test
    public void testRightBorderExceedSize() {
        final SlidingWindow example = new SlidingWindowMaximum.SlidingWindow(new int[] {1, 4, 2});
        example.incrementRight();
        example.incrementRight();
        example.incrementRight();
        assertEquals(4, example.getMaximum());
        example.incrementLeft();
        example.incrementLeft();
        example.incrementLeft();
        assertEquals(2, example.getMaximum());
    }

    // covers left border exceed last maximum
    @Test
    public void testLeftBorderExceedLastMaximum() {
        final SlidingWindow example = new SlidingWindowMaximum.SlidingWindow(new int[] {1, 4, 2});
        example.incrementLeft();
        assertEquals(1, example.getMaximum());
    }

}
