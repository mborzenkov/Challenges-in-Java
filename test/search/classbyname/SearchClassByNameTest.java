package search.classbyname;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/* Test SearchClassByName.
 * Conditions:
 *      guess must execute within 300 ms
 *      refresh must execute within 3000 ms
 *      Number of classes in test data: [0, 100000]
 *      Class names: <= 32 chars, only latin letters and digits, all unique
 * <br>
 * For every test case:
 *      Create new instance of search class.
 *      Call refresh once.
 *      Call guess several times, check running time.
 */
public class SearchClassByNameTest {

    /* Testing strategy:
     *
     *      execution time of refresh <= 3000
     *      execution time of guess <= 300
     *
     *      number of class names: 0, 100000
     *      matches: 0, 1, >=12
     *      check correct order
     *      border values: first, last
     *      query case: lower, mixed
     *      query ends on z
     *      class name start from lowercase
     */

    /** Array of class names in test data, initialized once. */
    private static final String[] classNames = new String[100000];
    /** Array of modification dates in test data, initialized once. */
    private static final long[] classModificationDates = new long[100000];

    /** Load test data from file to arrays. */
    @BeforeClass
    public static void readTestData() throws IOException {

        InputStream inStream = null;
        InputStreamReader inReader = null;
        BufferedReader bufReader = null;
        int numClasses = 0;

        try {
            inStream = SearchClassByNameTest.class.getResourceAsStream("testData.txt");
            inReader = new InputStreamReader(inStream, "UTF-8");
            bufReader = new BufferedReader(inReader);
            String readString;
            String[] splitString;
            while ((readString = bufReader.readLine()) != null) {
                splitString = readString.split(",");
                classNames[numClasses] = splitString[0];
                classModificationDates[numClasses] = Long.parseLong(splitString[1]);
                numClasses++;
            }
            inReader.close();
            inStream.close();
        } finally {
            if (bufReader != null) {
                bufReader.close();
            }
            if (inReader != null) {
                inReader.close();
            }
            if (inStream != null) {
                inStream.close();
            }
        }

        if (numClasses < 100000) {
            throw new IOException("File testData.txt corrupted: numClasses < 100000 == " + numClasses);
        }

    }

    /** Search class instance. */
    private SearchClassByName classSearch;

    /** Updates data in search class. */
    @Before
    public void refreshSearcher() {
        classSearch = new SearchClassByName();
        classSearch.refresh(classNames, classModificationDates);
    }

    // covers: execution time of refresh <= 3000
    @Test(timeout = 3000)
    public void testRefreshTimeout() {
        classSearch = new SearchClassByName();
        classSearch.refresh(classNames, classModificationDates);
    }

    // covers: execution time of guess <= 300
    //         number of class names 100000
    //         matches >= 12   совпадения >= 12
    //         check correct order
    //         query case lower
    @Test(timeout = 300)
    public void testNormalPrefix() {
        final String[] expected = new String[] {
                "ConcernAgonizingSkate",
                "ConnectionStoveFit",
                "ConfuseDragBirthday",
                "ConcentrateCautiousRot",
                "Constructor",
                "ConnectionApproveNorth",
                "ConstraintViolationException",
                "ConfigurableNavigationHandlerWra",
                "ConnectionSpellTame",
                "ConsistLookPushy",
                "ConfirmationCallback",
                "ConditionPunctureOrganic"
        };
        String[] result = classSearch.guess("con");
        assertTrue(Arrays.equals(expected, result));
    }

    // covers: number of class names 100000
    //         matches 1
    //         class name start from lowercase
    @Test(timeout = 300)
    public void testClassStartFromLowercase() {
        final String[] expected = new String[] {
                "bearPhobicBreezy"
        };
        String[] result = classSearch.guess("bearPhobicBreezy");
        assertTrue(Arrays.equals(expected, result));
    }

    // covers: number of class names 0
    //         matches 0
    @Test(timeout = 300)
    public void testZeroClasses() {
        classSearch.refresh(new String[0], new long[0]);
        String[] result = classSearch.guess("any");
        assertEquals(0, result.length);
    }

    // covers: number of class names 100000
    //         matches 0
    @Test(timeout = 300)
    public void testNoMatch() {
        String[] result = classSearch.guess("courageousbetterprotest2");
        assertEquals(0, result.length);
    }

    // covers: number of class names 100000
    //         matches 1
    //         border values first
    @Test(timeout = 300)
    public void testFirst() {
        final String[] expected = new String[] {
                "AbackAfterthoughtMalicious"
        };
        String[] result = classSearch.guess("AbackAfterthoughtMalicious");
        assertTrue(Arrays.equals(expected, result));
    }

    // covers: number of class names 100000
    //         matches 0
    //         border values first
    @Test(timeout = 300)
    public void testFirstNoMatch() {
        String[] result = classSearch.guess("aa");
        assertEquals(0, result.length);
    }

    // covers: number of class names 100000
    //         matches 1
    //         border values last
    @Test(timeout = 300)
    public void testLast() {
        final String[] expected = new String[] {
                "ZooZonkedReason"
        };
        String[] result = classSearch.guess("ZooZonkedReason");
        assertTrue(Arrays.equals(expected, result));
    }

    // covers: number of class names 100000
    //         matches 0
    //         border values last
    //         query ends on z
    @Test(timeout = 300)
    public void testLastNoMatch() {
        String[] result = classSearch.guess("zz");
        assertEquals(0, result.length);
    }

    // covers: number of class names 100000
    //         matches 1
    //         query case mixed
    @Test(timeout = 300)
    public void testMixedCase() {
        final String[] expected = new String[] {
                "AbackAmusedCold"
        };
        String[] result = classSearch.guess("aBaCkAm");
        assertTrue(Arrays.equals(expected, result));
    }

}
