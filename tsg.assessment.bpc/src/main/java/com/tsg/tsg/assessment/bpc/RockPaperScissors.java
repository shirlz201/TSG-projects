/*
 The program first asks the user how many rounds he/she wants to play.
Maximum number of rounds = 10, minimum number of rounds = 1.  If the user asks
for something outside this range, the program prints an error message and quits.
If the number of rounds is in range, the program plays that number of rounds. 
Each round is played according to the requirements below.
For each round of Rock, Paper, Scissors, the program does the following:
The computer asks the user for his/her choice (Rock, Paper, or Scissors).
After the computer asks for the user’s input, the computer randomly chooses 
Rock, Paper, or Scissors and displays the result of the round (tie, user win,
or computer win). The program must keep track of how many rounds are ties, user 
wins, or computer wins.

The program must print out the number of ties, user wins, and computer wins 
and declare the overall winner based on who won more rounds.
After all rounds have been played and the winner declared, the program must 
ask the user if he/she wants to play again. If the user says No, the program 
prints out a message saying, “Thanks for playing!” and then exits.
If the user says Yes, the program starts over, asking the user how many rounds 
he/she would like to play.
 */

package com.tsg.tsg.assessment.bpc;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author shirl
 */
public class RockPaperScissors {
    public static void main(String[] args) {
        // declare string variable for player's answer at the end of the game
        String answer;
        // declare and initialized scanner for user input
        Scanner game = new Scanner(System.in);
    //created a do while loop for each round until game is finished 
do{        
    // random class to assist with CPU choice in game
    Random cpuRandom = new Random();
    //declare int variables for player and CPU
    int playerChoice;
    int cpuChoice;
    int numOfRounds;
    int playerWins= 0;
    int cpuWins = 0;
    int ties = 0;
    
            // created an array of the player's & CPU choices in the game
                String [] choices = {"Rock", "Paper","Scissors"};
   
        System.out.println("Hello there! How many round(s) would you like to "
                + "play?" + "(Enter a number from 1 to 10).");
        numOfRounds = game.nextInt();
        // statement & player know their min(1) and max(10) to input are to play
        if (numOfRounds <=0 || numOfRounds > 10) {
            System.out.println("Invalid choice. Ending Program.");
            System.exit(0); //program termination due to invalid input
        } else {
            for(int i =0; i < numOfRounds; i++) {
                System.out.println("Let's play! Enter a number:"
                + " ( 1=Rock, 2=Paper, 3=Scissors)");
                playerChoice = game.nextInt();
                
                cpuChoice = cpuRandom.nextInt(3);
                System.out.println("CPU chose:" + choices[cpuChoice]);
            //start - rules of the game and how player or CPU win/loses
            if( playerChoice==1 && cpuChoice==2){
                playerWins ++; System.out.println("You win!");
            }else if(playerChoice==2 && cpuChoice==0){
                playerWins ++; System.out.println("You win!");
            }else if(playerChoice==3 && cpuChoice==1){
                playerWins ++; System.out.println("You win!");
            }else if(playerChoice==3 && cpuChoice==0){
                cpuWins++; System.out.println("CPU wins!");
            }else if(playerChoice==1&& cpuChoice==1){
                cpuWins++; System.out.println("CPU wins!");
            }else if(playerChoice==2 && cpuChoice==2){
                cpuWins++; System.out.println("CPU wins!");
            }else if(playerChoice==1 && cpuChoice==0){
                ties++; System.out.println("Woah, it's a tie!");
            }else if(playerChoice==2 && cpuChoice==1){
                ties ++; System.out.println("Woah, it's a tie!");
            }else{
                ties ++; System.out.println("Woah it's a tie!");
            }
            /*end- rules of the game and how player or CPU win/loses
            after each rule - player/CPU wins and ties are calculated*/
           } 
            //overrall score between player and CPU
            System.out.println("P1 won " + playerWins + " time(s).");
            System.out.println("CPU won " + cpuWins + " time(s).");
            System.out.println("There were " + ties + " ties.");
            //Score comparison and winner is determined in each round
            if (playerWins > cpuWins) {
                System.out.println("You are the winner!");
            } else{
                System.out.println("Computer is the winner!" );
            }
            
         }
             System.out.println("Do you want to play again? (y/n)");
             answer = game.next();
        
           } while (answer.equalsIgnoreCase("y"));
                if (answer.equalsIgnoreCase("n")){
                    //player's answer can input uppercase too
                    System.out.println("Thanks for playing!");
                    System.exit(0);
                    //program ends here after player puts "n"
                }
        
    }
}