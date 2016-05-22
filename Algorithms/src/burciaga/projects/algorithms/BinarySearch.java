package burciaga.projects.algorithms;

import java.util.Arrays;

/**
 * Created by bmb0205 on 5/22/16.
 * Binary search algorithm implementation in Java
 */
public class BinarySearch {
    

    // accepts array and int arguments and performs a binary search on number and array specified
    public static boolean searchForNumber(Integer[] myArray, int myNumber) {
        if (myArray.length == 0) {
            return false;
        }

        int firstNumber = 0;
        int lastNumber = myArray.length - 1;

        //  binary search
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

