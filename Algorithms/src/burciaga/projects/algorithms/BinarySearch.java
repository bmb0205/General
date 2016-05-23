package burciaga.projects.algorithms;

import java.util.Arrays;

/**
 * Created by bmb0205 on 5/22/16.
 * Binary search algorithm implementation in Java.
 * The binary search algorithm must be used on a sorted array and runs worst case
 * O(log(n)) time and constant O(1) space.
 * The searched array is reduced in half until the searched number is found and
 * the array, searched number, and element index are all displayed.
 */
public class BinarySearch {

    public static boolean binarySearch(Integer[] myArray, int myNumber) {
        if (myArray.length == 0) {
            return false;
        }

        int firstNumber = 0;
        int lastNumber = myArray.length - 1;

        while (firstNumber <= lastNumber) {
            int middleNumber = (firstNumber + lastNumber) / 2;
            if (myNumber > myArray[middleNumber]) {
                firstNumber = middleNumber + 1;
            } else if (myNumber < myArray[middleNumber]) {
                lastNumber = middleNumber - 1;
            } else {
                System.out.println(Arrays.toString(myArray));
                System.out.println("Your number: " + myNumber);
                System.out.println("found the element at index " + Arrays.asList(myArray).indexOf(myNumber) + "\n");
                return true;
            }
        }
        return false;
    }
}

