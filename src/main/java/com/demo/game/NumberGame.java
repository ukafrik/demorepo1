/**
 * 
 */
package com.demo.game;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * NumberGame -- A simple Java console program to guess a player's secret number
 * @author Jonathan S Solomon
 * @version 1.0.0
 */
public class NumberGame {
	
	private final String CMD_PROMPT = "Command";
	private Scanner sc;
	private String test1;

	/**
	 * The only constructor of the application and takes no parameters.
	 * The program uses this constructor to initialize a java.util.Scanner object
	 * that is  used to obtain the game commands typed by the player
	 */
	public NumberGame () {
		//Initialize single Scanner for this Console app..
		sc = new Scanner(System.in);
	}
	
	/**
	 * The main method is the entry into the application. It displays the game's instructions and 
	 * the valid commands allowed by the game. 
	 * @param args This holds an array of command-line args that is passed to the program
	 */
	public static void main(String[] args) {
		
		//Following sys-outs display a welcome screen and instructions for playing the game.
		System.out.println("Welcome to Number-Guessing Game!");
		System.out.println("In this game, you pick a secret number and let me guess that number with the fewest guesses.");
		System.out.println("Assumptions made are: Your secret number must be an absolute number.");
		System.out.println("Instructions are as follows:");
		System.out.println("\tFirst, think of your secret number");
		System.out.println("\tType \"ready\" command to begin playing the game");
		System.out.println("\tType \"yes\" or \"end\" command when my guess is correct or you want to end the game");
		System.out.println("\tType \"higher\" command when my guess is higger than your secret number");
		System.out.println("\tType \"lower\" command when my guess is lower than your secret nubmer");
		System.out.println("Have fum playing the game!");

		//This use of the Constructor initializes the only java.util.Scanner for
		//inputs from the player
		NumberGame numberGame = new NumberGame();
		
		try {
			//The startGame method starts the game
			String cmd = numberGame.startGame();
			if (cmd != null && cmd.trim().equals("ready")) {
				cmd = null;
				numberGame.guessNumber();
			}
			else if (cmd != null && cmd.trim().equals("end")) {
		        System.out.println("Sad you're ending the game before we got started, \nbut thank you for your interest to play. Let's play again soon.");
			}
		}
		finally {
			//The endGame method ensures the only java.util.Scanner is closed in a finally block by
			//guaranteeing it's closure
			numberGame.endGame();
		}
	}
	
	/**
	 * This method takes no parameters and is responsible for detecting and ensuring the 
	 * allowed command to start or end the game is type by the player at the start of the game.
	 * If the player types "ready", the game starts. If the player types "end", the gane ends.
	 */
	private String startGame() {
		String cmd;
		Pattern startPattern = Pattern.compile("^ready$|^end$");
	    do {
	        cmd = processCmd(startPattern);
	    } 
	    while (cmd == null);
	    
	    return cmd;
	}
	
	/**
	 * The endGame method takes no parameters and ensures that the only java.util.Scanner intialized
	 * in the Constructor is closed before the application exits
	 */
	private void endGame() {
		//Close single Scanner...
		sc.close();
	}
	
	/**
	 * The guessUp method allows the game to guess a number higher than the last number the game guessed.
	 * It does this by adding the absolute value of the difference of the last higher number
	 * and the last lower number guessed by the game divided in half.
	 * @param curMin This param holds the last lower number the game guessed
	 * @param curMax This param holds the last higher number the game guessed
	 */
	private int guessUp(int curMin, int curMax) {
		int cur = 0;
		
		int absVal = Math.abs((curMax - curMin)/2);
		
		if (curMax != 0 && ((curMax + absVal) > curMax)) {
			cur = curMax - absVal;
		}
		else {
			cur = curMax > curMin? (curMax + absVal) : (curMin + absVal);
		}
		
		return cur;
	}
	
	/**
	 * The guessDown method allows the game to guess a number lower than the last number the game guessed
	 * It does this by subtracting the absolute value of the difference of the last higher number
	 * and the last lower number guessed by the game divided in half.
	 * @param curMin This param holds the last lower number the game guessed
	 * @param curMax This param holds the last higher number the game guessed
	 */
	private int guessDown(int curMin, int curMax) {
		int cur = 0;
		
		int absVal = Math.abs((curMax - curMin)/2);
		
		cur = curMax > curMin? (curMax - absVal) : (curMin - absVal);
				
		return cur;
	}
	
	/**
	 * The guessNumber method takes no parameters is the main engine of the number guessing game.
	 * It uses a pattern match to ensure that the player types in an allowed game command and uses
	 * the guessUp and guessDown method to determine which direction to guess the players number. 
	 * It loops through the command typed by the player and the direction of the next guess until
	 * it arrives at the correct guess for the player's secret number
	 * 
	 * @return The method returns the command to determine the direction to guess for the player's number
	 */
	private String guessNumber() {
		String cmd;
		Pattern guessPattern = Pattern.compile("^yes$|^higher$|^lower$|^end$");
		
		int curMin = 0;
		int curMax = 0;
		int curGuessNum = 50;
		
		System.out.println("Game is on!");
		System.out.println("My first guess is: " + curGuessNum);
		
	    do {
	        cmd = processCmd(guessPattern);
	        if (cmd != null && cmd.trim().equals("yes")) {
	            System.out.println("Yes! I finally guessed it right!");
	        }
	        else if (cmd != null && cmd.trim().equals("lower")) {
	        	curMax = curGuessNum;
	            curGuessNum = this.guessDown(curMin, curMax);
	            System.out.println("My lower guess is: " + curGuessNum);
	            cmd = null;
	        }
	        else if (cmd != null && cmd.trim().equals("higher")) {
	        	curMin = curGuessNum;
	            curGuessNum = this.guessUp(curMin, curMax);
	            System.out.println("My higher guess is: " + curGuessNum);
	            cmd = null;
	        }
	    } 
	    while (cmd == null);
        System.out.println("Thank you for playing with me. Let's play again soon.");
	    
	    return cmd;
	}
	
	
	/**
	 * This method accepts a paramenter that it uses to determine if a command typed
	 * by the player is allowed. It loops through the player's console input until the 
	 * command typed by the player matches one of the allowed commands defined in the pattern.
	 * @param expectedCmd This parameter holds the pattern to be matched for commands typed the player
	 * 
	 * @return This method returns the allowed command the player typed at the console
	 * 
	 */
	public String processCmd(Pattern expectedCmd) {
		//This holds the command typed by the Player
        String cmd;
        
        //Present a prompt to Player for typing commands...
        System.out.printf("\n%s > ", CMD_PROMPT);
        
        //Get and store command typed by Player...
        cmd = sc.next();
        
        //Only allow defined commands to be entered by player...
		Matcher matcher = expectedCmd.matcher(cmd);
		
		//Loop until an allowed command is typed by the Player...
        while (!matcher.find()) {
            System.out.println("Please type allowed game command.");
            System.out.printf("\n%s > ", CMD_PROMPT);
            
            //Get new command typed by Player..
	        cmd = sc.next();
	        
	        //Pass command to matcher for matching allowed commands...
	        matcher = expectedCmd.matcher(cmd);
        }
        
        return cmd;
	}

}
