/*
 Write a program that asks the user for the name of their dog, and then 
 generates a fake DNA background report on the pet dog.
 It should assign a random percentage to 5 dog breeds
 (that should add up to 100%!) 
 */
package com.tsg.tsg.assessment.bpc;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author shirl
 */
public class DogGenetics {

    public static void main(String[] args) {
       // declare and initialize our scanner
       Scanner dogInfo = new Scanner(System.in);
        // declare variable for dog's name
        String dogName;
        System.out.println("What is your dog's name?");
        dogName = dogInfo.nextLine();
        // user input is stored so it can be used for reference 

        System.out.println("Well then, I have this highly reliable report on "
        + dogName + "'s prestigious background right here!");
        // concatenated both strings to print
        System.out.println(dogName + " is:");
        //created random method called dogDna
       Random dogDna = new Random();

        /*declared variables for each percentage of breed which equaled to 100
         generated a random number from 0-100 and subracted each variable from
        from 100*/
        int d1 = dogDna.nextInt(100);
        int d2 = dogDna.nextInt(100 - d1);
        int d3 = dogDna.nextInt(100 - d1 - d2);
        int d4 = dogDna.nextInt(100 - d1 - d2 - d3);
        int d5 = 100 - d1 - d2 - d3 - d4;
        /* d5 contains all of the previous variables subtracted from 100 & also
        printed out percentages along with their assigned breed for DNA report
         */
        System.out.println(d1 + "%" + " German Shepard");
        System.out.println(d2 + "%" + " King Doberman");
        System.out.println(d3 + "%" + " Pomeranian");
        System.out.println(d4 + "%" + " French BullDog");
        System.out.println(d5 + "%" + " Belgian Malinois");

    }

}
