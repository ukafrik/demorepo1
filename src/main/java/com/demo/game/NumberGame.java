/**
 * 
 */
package com.demo.game;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Jonathan S Solomon
 *
 */

public class NumberGame {
	
	private final String CMD_PROMPT = "Command";
	private Scanner sc;

	public NumberGame () {
		//Initialize single Scanner for this Console app..
		sc = new Scanner(System.in);
	}
	
	public static void main(String[] args) {
		
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

		NumberGame numberGame = new NumberGame();
		
		try {
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
			numberGame.endGame();
		}
	}
	
	private String startGame() {
		String cmd;
		Pattern startPattern = Pattern.compile("^ready$|^end$");
	    do {
	        cmd = processCmd(startPattern);
	    } 
	    while (cmd == null);
	    
	    return cmd;
	}
	
	private void endGame() {
		//Close single Scanner...
		sc.close();
	}
	
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
	
	private int guessDown(int curMin, int curMax) {
		int cur = 0;
		
		int absVal = Math.abs((curMax - curMin)/2);
		
		cur = curMax > curMin? (curMax - absVal) : (curMin - absVal);
				
		return cur;
	}
	
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
	
	
	private String processCmd(Pattern expectedCmd) {
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
