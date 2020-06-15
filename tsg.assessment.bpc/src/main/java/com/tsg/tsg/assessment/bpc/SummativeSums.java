/*Write a static method that takes in an array of ints, adds them together, and 
returns the result.
Call your new method inside a main method and print out the result for the 
following three example arrays:
{ 1, 90, -33, -55, 67, -16, 28, -55, 15 }
{ 999, -60, -77, 14, 160, 301 }
{ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 
140, 150, 160, 170, 180, 190, 200, -99 } 
 */
package com.tsg.tsg.assessment.bpc;

/**
 *
 * @author shirl
 */
public class SummativeSums {

    public static void main(String[] args) {
        //declare three arrays with an int datatype
        int[] array1 = {1, 90, -33, -55, 67, -16, 28, -55, 15};
        int[] array2 = {999, -60, -77, 14, 160, 301};
        int[] array3 = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130,
            140, 150, 160, 170, 180, 190, 200, -99};
        //print text and sum of each array respectively
        System.out.println("#1 Array Sum: " + sumofArray(array1));
        System.out.println("#2 Array Sum: " + sumofArray(array2));
        System.out.println("#3 Array Sum: " + sumofArray(array3));
    }
    
    //created method and for loop to add the sum of an array with return add
    public static int sumofArray(int[] sum) {
        int add = 0;
        /*within the for loop it will go through each element in the 3 arrays
        and calculate the sum into the return value */
        for (int i = 0; i < sum.length; i++) {
            add += sum[i];
        }
        return add;
    }

}
