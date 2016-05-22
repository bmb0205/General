package burciaga.projects.algorithms;

import java.util.Arrays;

/**
 * Created by bmb0205 on 5/22/16.
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
//            System.out.println(Arrays.toString(myArray));
        }
        System.out.println("done");
        return myArray;
    }
}
