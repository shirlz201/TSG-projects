/*
Make a simple calculator that asks the user for their age. 
Then calculate the healthy heart rate range for that age, and display it.
Their maximum heart rate should be 220 - their age.
The target heart rate zone is the 50 - 85% of the maximum.
 */
package com.tsg.tsg.assessment.bpc;

import java.util.Scanner;

/**
 *
 * @author shirl
 */
public class HealthyHearts {

    public static void main(String[] args) {
        //declare and initialize scanner for user input
        Scanner ages = new Scanner(System.in);
        //declare int variables age and yourAge
        int age;
        String userAge;
        //asking the user what their age is
        System.out.println("What is your age?");
        //referenced yourAge to the scanner
        userAge = ages.nextLine();
        //since yourAge is a string-it needs to be converted to an int
        age = Integer.parseInt(userAge);
        //maximum HR should be (220-their age)
        System.out.println("Your maximum heart rate should be " + 
        (220 - age) + " beats per minute.");
        //target HR zone is 50%-85% of their max
        //Math.round would round the int to the nearest int value
        System.out.println("Your target HR Zone is " + ((220 - age) /2)+ " - " 
        + (Math.round((220 - age) * 0.85)) + " beats per minute.");
    }
}
