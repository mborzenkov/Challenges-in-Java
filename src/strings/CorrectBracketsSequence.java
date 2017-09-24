package strings;

import java.util.Stack;

/** Checking if brackets sequence is correct.
 * Correct brackets sequence is a string, containing only brackets like ()[]{}, where all brackets can be split
 * to pairs such as:
 *      Each pair has open and close bracket and open is left from close.
 *      For each 2 pairs either one is fully inside in other ([]) or they are not crossing each other ()[]
 *      Each pair consists of similar type of brackets - ( for ), [ for ], { for }
 * Input: string of brackets
 * Output: index of error or CORRECT if everything is correct
 */
public class CorrectBracketsSequence {

    /** Output value for isCorrectSequence if input is correct. */
    public static final int CORRECT = -1;

    /** Check if input is correct brackets sequence.
     *
     * @param input not null, string empty or containing only brackets characters ()[]{}
     *
     * @return index of error (length of max brackets subsequence that is correct or can be continued to correct)
     *          or CORRECT if input is correct brackets sequence
     *
     * @throws NullPointerException if input is null
     */
    public static int isCorrectSequence(final String input) {

        // Stack for counting opening brackets
        Stack<Character> bracketsStack = new Stack<>();
        final char[] charSequence = input.toCharArray();

        // For each character in input:
        // if it is opening bracket, put it in stack;
        // else check if stack is not empty and top is similar bracket as current closing, pop stack or return error
        for (int i = 0; i < charSequence.length; i++) {
            switch (charSequence[i]) {
                case '(':
                case '[':
                case '{':
                    bracketsStack.push(charSequence[i]);
                    break;
                case ')':
                    if (!bracketsStack.empty() && bracketsStack.peek().equals('(')) {
                        bracketsStack.pop();
                    } else {
                        return i;
                    }
                    break;
                case ']':
                    if (!bracketsStack.empty() && bracketsStack.peek().equals('[')) {
                        bracketsStack.pop();
                    } else {
                        return i;
                    }
                    break;
                case '}':
                    if (!bracketsStack.empty() && bracketsStack.peek().equals('{')) {
                        bracketsStack.pop();
                    } else {
                        return i;
                    }
                    break;
                default:
                    break;
            }
        }

        // if stack is not empty then not all opening brackets were closed
        return bracketsStack.empty() ? CORRECT : charSequence.length;
    }

}
