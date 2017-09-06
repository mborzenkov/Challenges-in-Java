---
layout: post
title: 
description: Fast search by prefix within array of class names and array of modification dates, resulting matching class names ordered by modification dates.
date: 2017-09-06 18:16:01 +0500
category: search
tags: [search, cv]
---

## Search class by name
_Fast search by prefix within array of class names and array of modification dates, resulting matching class names ordered by modification dates._

[Source of specification](http://www.naumen.ru/career/trainee/)

Imagine that you are the developer of ancient times, when IDE first came out. You have a big project in Java with hundreds of thousands of files.

You planned to implement the search function of a class by its name. For convenience, You just need to type the first letters of the class name, then IDE offers you a list of 12 classes that begin with entered characters. The list of classes is ordered by last modification date (newer at the beginning). If classes are modified at same time, ordered by name.

Your task is to implement the guessing algorithm in the Java.
It is assumed that you call refresh once when opening a project, and then all searches are fast.

API specs:
```java
public interface ISearcher { 

/** Updates inner data structures for fast search.
 * @param classNames names of classes in project * @param modificationDates modification dates in ms */public void refresh(String[] classNames, long[] modificationDates);/** Find matching class names.
 * Class name must start from prefix * @param prefix prefix of class name * @return array size from 0 to 12 containing class names, ordered 
 * 	by modified dates DESC and class names ASC. */public String[] guess(String prefix);

}
```

Additional conditions:
 * guess must execute within 300 ms
 * refresh must execute within 3000 ms
 * Number of classes in test data: [0, 100000]
 * Class names: <= 32 chars, only latin letters and digits, all unique

### Solution
I have created a ClassData object that contains class name and modification date. ClassData implements Comparable, allowing to order objects alphabetically and by modification date DESC.

refresh iterates through arrays of class names and modification dates, creating the list of ClassData objects. Then the list is sorted as described above with Collections.sort(…).

As we sorted list of ClassData earlier, we can use binary search in guess. First we need to find first index of first matching element (that equals prefix or next to it). For example, when searching for "aa" in "Aaa, Aab, Aac, Aba, Abb", first binary search will return 0.

Then we need to find first not matching index by incrementing last char of prefix by 1 and using binary search again. With previous example, result would be 2.

All objects between first and second indexes are matching, so we need to extract them to separate list and sort by modification date, using special Comparator. Then return first 12 of sorted list.

### Testing
All tests are in **search.classbyname** package.

**testData.txt** contain 100000 unique class names.

Testing strategy:
* execution time of refresh <= 3000 ms
* execution time of guess <= 300 ms
* number of class names: 0, 100000
* matches: 0, 1, >=12
* check correct order
* border values: first, last
* query case: lower, mixed
* query ends on z
* class name start from lowercase

For every test case:
 * Creating new instance of search class.
 * Calling refresh once.
 * Calling guess, check running time.

