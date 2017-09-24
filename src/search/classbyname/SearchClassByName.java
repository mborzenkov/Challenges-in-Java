package search.classbyname;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/** Find classes by prefix.
 * @see <a href="http://www.naumen.ru/career/trainee/">Specs from here</a>
 * <br>
 *     This class implements fast search in parallel arrays of names and modification dates by specified prefix.
 *     Usage:
 *          Call refresh with array of names and modification dates.
 *          Call guess to search for names started with prefix, sorted by modification dates DESC (12 results max).
 */
class SearchClassByName {

    /** Max results returned from guess. */
    private static final int MAX_GUESS = 12;

    /** Represents information about class: name and modification date. */
    private static class ClassData implements Comparable<ClassData> {

        /** Date format. */
        private static final String FORMAT_DATE = "yyyy-MM-dd'T'hh:mm:ss.SSSZZZZZ";

        /** Name of class. */
        private final String name;
        /** Modification date in ms. */
        private final long modDate;

        private ClassData(String name, long modDate) {
            this.name = name;
            this.modDate = modDate;
        }

        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + (int) (modDate ^ (modDate >>> 32));
            return 31 * result + name.hashCode();
        }

        /** Two ClassData objects are equal if names are equal (ignoring case) and modification dates.
         *
         * @param obj compared object
         * @return True, if equal; False otherwise
         */
        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof ClassData)) {
                return false;
            }
            ClassData thatObject = (ClassData) obj;
            return (modDate == thatObject.modDate) && name.equalsIgnoreCase(thatObject.name);
        }

        /** Return human-readable representation of ClassData, including name and modified date.
         *
         * @return example: "ArrayList (mod: 2017-06-01'T'10:03:33.235+05:00)
         */
        @Override
        public String toString() {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(FORMAT_DATE, Locale.US);
            return String.format("%s (mod: %s)", name, dateFormatter.format(modDate));
        }

        /** Compares this object with the specified object for order.
         * Objects ordered by name ASC case insensitive.
         * If two objects have same names, they are ordered by modification date DESC.
         * Example:
         *      name    modDate     order
         *      АaА     1           AAa 2
         *      AAa     2           АaА 1
         *      aAB     1           aAB 1
         *
         * @param obj object for comparison
         *
         * @return Negative integer, zero, or a positive integer as this object is less than, equal to, or greater
         *          than the specified object.
         */
        @Override
        public int compareTo(ClassData obj) {
            int result = name.compareToIgnoreCase(obj.name);
            if (result == 0) {
                result = Long.compare(obj.modDate, modDate);

            }
            return result;
        }

    }

    /** Comparator for comparing two ClassData objects by modification date. */
    private static class ClassDataModDateComparator implements Comparator<ClassData> {

        /** Compares this object with the specified object for order.
         * Objects ordered by modification date DESC.
         * If two objects have same modification dates, they are ordered by name ASC case insensitive.
         * Example:
         *      name    modDate     order
         *      АaА     1           АaА 1
         *      AAa     2           aAB 1
         *      aAB     1           AAa 2
         *
         * @param obj1 first object
         * @param obj2 second object
         *
         * @return Negative integer, zero, or a positive integer as this object is less than, equal to, or greater
         *         than the specified object.
         */
        @Override
        public int compare(ClassData obj1, ClassData obj2) {
            int result = Long.compare(obj2.modDate, obj1.modDate);
            if (result == 0) {
                result = obj1.name.compareToIgnoreCase(obj2.name);

            }
            return result;
        }

    }

    /** List of all classes for searching, natural ordering determined in ClassData object. */
    private final List<ClassData> classData = new ArrayList<>();

    /** Updates inner data structures for fast searching.
     *
     * @param classNames class names, all unique, contain only latin letters and digits.
     * @param modificationDates modification dates in ms (the difference, measured in milliseconds,
     *                          between the current time and midnight, January 1, 1970 UTC.)
     *
     * @throws NullPointerException if any of parameters is null
     */
    void refresh(String[] classNames, long[] modificationDates) {
        classData.clear();
        for (int i = 0; i < classNames.length; i++) {
            classData.add(new ClassData(classNames[i], modificationDates[i]));
        }
        Collections.sort(classData);
    }

    /** Searches for class names by prefix.
     *
     * @param prefix prefix for class name, case insensitive
     *
     * @return array of class names, started with prefix, size 0 to 12, sorted by modification date DESC and name ASC
     *
     * @throws NullPointerException if prefix == null
     */
    String[] guess(String prefix) {

        prefix = prefix.trim().toLowerCase();
        final int prefLen = prefix.length();

        // Find index of first matching element (equals prefix or next)
        int start = Collections.binarySearch(classData, new ClassData(prefix, Long.MAX_VALUE));
        if (start < 0) {
            start = Math.abs(start + 1);
        }
        // Incrementing last char of prefix by 1 - this is first not matching prefix
        // Find index of first not matching element (equals not matching prefix or next)
        int end   = Collections.binarySearch(classData, new ClassData(
                prefix.substring(0, prefLen - 1) + (char) (prefix.charAt(prefLen - 1) + 1), Long.MAX_VALUE));
        if (end < 0) {
            end = Math.abs(end + 1);
        }

        // Choose elements from start to end, within classData size
        final int classDataSize = classData.size();
        final List<ClassData> matches = classData.subList(
                start > classDataSize ? classDataSize : start,
                end > classDataSize ? classDataSize : end);
        // Sort new list by modification date
        matches.sort(new ClassDataModDateComparator());

        // Return first 12
        String[] result = new String[matches.size() > MAX_GUESS ? MAX_GUESS : matches.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = matches.get(i).name;
        }

        return result;

    }

}
