package lis;

import org.junit.Test;
import lis.NestingBoxes.Box;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/** Test NestingBoxes. */
public class NestingBoxesTest {

    /* Testing strategy:
     *    Box:
     *      normal box
     *      canHold true/false
     *      equality
     *      toString
     *
     *    Box exceptions:
     *      zero dimensions
     *      negative dimensions
     *
     *    Stack:
     *      normal increasing stack
     *      stack skipping boxes
     *      no stack (=1)
     *      empty stack (=0)
     */

    // covers normal box
    //        canHold true
    @Test
    public void testNormalBoxCanHold() {
        assertTrue(new Box(2, 2, 2).canHold(new Box(1, 1, 1)));
    }

    // covers normal box
    //        canHold false
    @Test
    public void testNormalBoxNotHold() {
        assertFalse(new Box(2, 1, 1).canHold(new Box(1, 1, 1)));
    }

    // covers equality
    @Test
    public void testBoxEquality() {
        final Box box1 = new Box(1, 1, 1);
        final Box box2 = new Box(1, 1, 1);
        final Box box3 = new Box(1, 1, 2);
        assertEquals(box1, box2);
        assertEquals(box1.hashCode(), box2.hashCode());
        assertFalse(box1.equals(box3));
        assertFalse(box2.equals(box3));
    }

    @Test
    public void testBoxToString() {
        assertEquals("Box (height=1, width=2, length=3)", new Box(1, 2, 3).toString());
    }

    // covers normal increasing stack
    @Test
    public void testNormalStack() {
        assertEquals(3, NestingBoxes.getMaxStack(new Box[] {
                new Box(1, 1, 1),
                new Box(2, 2, 2),
                new Box(3, 3, 3),
                new Box(3, 3, 4)}));
    }

    // covers stack skipping boxes
    @Test
    public void testSkippingBoxes() {
        assertEquals(3, NestingBoxes.getMaxStack(new Box[] {
                new Box(1, 1, 1),
                new Box(2, 2, 2),
                new Box(2, 2, 3),
                new Box(3, 3, 4)}));
        // stack of 111, 222, 334 skipping 223
    }

    // covers no stack
    @Test
    public void testNoStack() {
        assertEquals(1, NestingBoxes.getMaxStack(new Box[] {
                new Box(1, 1, 1),
                new Box(1, 2, 2),
                new Box(1, 3, 3),
                new Box(1, 3, 4)}));
    }

    // covers empty stack
    @Test
    public void testEmptyStack() {
        assertEquals(0, NestingBoxes.getMaxStack(new Box[0]));
    }

}
