package strings;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/** Test CorrectBrackerSequence. */
public class CorrectBracketsSequenceTest {

    /* Testing strategy:
     *      correct/incorrect
     *          round brackets
     *          square brackets
     *          curly brackets
     *          mixed brackets
     */

    // covers round brackets
    //        correct
    @Test
    public void testCorrectRoundBrackets() {
        assertEquals(CorrectBracketsSequence.CORRECT, CorrectBracketsSequence.isCorrectSequence("(())"));
        assertEquals(CorrectBracketsSequence.CORRECT, CorrectBracketsSequence.isCorrectSequence("()"));
        assertEquals(CorrectBracketsSequence.CORRECT, CorrectBracketsSequence.isCorrectSequence("()()"));
        assertEquals(CorrectBracketsSequence.CORRECT, CorrectBracketsSequence.isCorrectSequence("()()()"));
        assertEquals(CorrectBracketsSequence.CORRECT, CorrectBracketsSequence.isCorrectSequence("(())()"));
        assertEquals(CorrectBracketsSequence.CORRECT, CorrectBracketsSequence.isCorrectSequence("((()))"));
    }

    // covers square brackets
    //        mixed brackets
    //        correct
    @Test
    public void testCorrectSquareBrackets() {
        assertEquals(CorrectBracketsSequence.CORRECT, CorrectBracketsSequence.isCorrectSequence("[]"));
        assertEquals(CorrectBracketsSequence.CORRECT, CorrectBracketsSequence.isCorrectSequence("[()]"));
        assertEquals(CorrectBracketsSequence.CORRECT, CorrectBracketsSequence.isCorrectSequence("[[([])]()]"));
    }

    // covers curly brackets
    //        mixed brackets
    //        correct
    @Test
    public void testCorrectCurlyBrackets() {
        assertEquals(CorrectBracketsSequence.CORRECT, CorrectBracketsSequence.isCorrectSequence("[{(())}({})]"));
        assertEquals(CorrectBracketsSequence.CORRECT, CorrectBracketsSequence.isCorrectSequence("[]{}()"));
        assertEquals(CorrectBracketsSequence.CORRECT, CorrectBracketsSequence.isCorrectSequence("{}"));
    }

    // covers round brackets
    //        incorrect
    @Test
    public void testIncorrectRoundBrackets() {
        assertEquals(0, CorrectBracketsSequence.isCorrectSequence(")("));
        assertEquals(0, CorrectBracketsSequence.isCorrectSequence("))"));
        assertEquals(0, CorrectBracketsSequence.isCorrectSequence(")"));
        assertEquals(2, CorrectBracketsSequence.isCorrectSequence("(("));
        assertEquals(2, CorrectBracketsSequence.isCorrectSequence("())()("));
        assertEquals(2, CorrectBracketsSequence.isCorrectSequence("())"));
        assertEquals(0, CorrectBracketsSequence.isCorrectSequence("))(("));
    }

    // covers square brackets
    //        mixed brackets
    //        incorrect
    @Test
    public void testIncorrectSquareBrackets() {
        assertEquals(1, CorrectBracketsSequence.isCorrectSequence("[)"));
        assertEquals(2, CorrectBracketsSequence.isCorrectSequence("([)]"));
        assertEquals(8, CorrectBracketsSequence.isCorrectSequence("(())()[]]["));
    }

    // covers curly brackets
    //        mixed brackets
    //        incorrect
    @Test
    public void testIncorrectCurlyBrackets() {
        assertEquals(4, CorrectBracketsSequence.isCorrectSequence("(([{"));
        assertEquals(2, CorrectBracketsSequence.isCorrectSequence("[(]())]{}"));
        assertEquals(3, CorrectBracketsSequence.isCorrectSequence("[{(})]"));
    }

}
