package burciaga.projects.algorithms;

import java.util.Arrays;

/**
 * Created by bmb0205 on 5/22/16.
 * Bubble sort algorithm implemented in Java.
 * Worst and average case time efficiency: O(n^2)
 * Best case time efficiency: O(n).
 * Space complexity: constant O(1)
 * Algorithm compares adjacent elements and switches if e2 > e1
 */
public class BubbleSort {

    public static Integer[] bubbleSort(Integer[] myArray) {
        for (int a = 0; a < myArray.length - 1; a++) {
            for (int b = 1; b < (myArray.length - a); b++) {
                if (myArray[b - 1] > myArray[b]) {
                    int tempInt = myArray[b - 1];
                    myArray[b - 1] = myArray[b];
                    myArray[b] = tempInt;
                }
            }
//            System.out.println(Arrays.toString(myArray)); // print result of each iteration
        }
        return myArray;
    }
}
