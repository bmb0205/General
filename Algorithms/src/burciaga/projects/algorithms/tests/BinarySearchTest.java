package burciaga.projects.algorithms.tests;

import burciaga.projects.algorithms.BinarySearch;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by bmb0205 on 5/22/16.
 * Tests binary search algorithm implemented in BinarySearch.java using JUnit
 * assertTrue for three separate numbers contained in myArray
 * assertFalse for number not contained in myArray
 */
public class BinarySearchTest {

    @Test
    public void testbinarySearch() {
        Integer[] myArray = {1, 2, 5, 6, 7, 8, 10, 13, 14};
        assertTrue(BinarySearch.binarySearch(myArray, 2));
        assertTrue(BinarySearch.binarySearch(myArray, 8));
        assertTrue(BinarySearch.binarySearch(myArray, 14));
        assertFalse(BinarySearch.binarySearch(myArray, 3));
    }
}
