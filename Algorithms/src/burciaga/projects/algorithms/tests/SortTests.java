package burciaga.projects.algorithms.tests;

import burciaga.projects.algorithms.BubbleSort;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by bmb0205 on 5/22/16.
 * JUnit test for bubble sort algorithm implemented in BubbleSort.java
 * assertEquals false comparing unsorted myArray to bubble sorted bubble
 * assertEquals true comparing sorted list myArray to bubble sorted list
 */
public class SortTests {

    @Test
    public void bubbleSortTest() {
        Integer[] myArray = {6, 12, 3, 10, 4, 8, 13, 1};
        Integer[] tempArray = Arrays.copyOf(myArray, myArray.length);
        Integer[] bubble = BubbleSort.bubbleSort(tempArray);
        assertEquals(false, Arrays.equals(bubble, myArray));
        Collections.sort(Arrays.asList(myArray));
        assertEquals(true, Arrays.equals(bubble, myArray));
    }

}
