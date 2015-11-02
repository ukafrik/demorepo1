package com.demo.game;

import static org.junit.Assert.*;

import java.util.regex.Pattern;

import org.junit.Test;

public class NumberGameTest {

	@Test
	/**
	 * This JUnit method tests the processCmd method that prompts a player for a
	 * valid command allowed by the game. If the player types in invalid command,
	 * the method will continue to prompt the player until a valid command is typed.
	 * 
	 * The processCmd takes a java.util.regex.Pattern argument to define the allowed 
	 * commands to be typed by the player
	 */
	public void testProcessCmd() {
		NumberGame numberGame = new NumberGame();
		
		//The following pattern ensures only commands matching the pattern are allowed.
		//If the player types a command not matched by the pattern, the program will continue
		//prompting player until a valid command is typed
		Pattern expectedCmd = Pattern.compile("^ready$|^yes$|^higher$|^lower$|^end$");
		
		//The processCmd takes in one argument that defines the pattern of commands allowed in
		//the game and will keep prompting player until one of the valid commands is typed.
		String cmd = numberGame.processCmd(expectedCmd);
		assertEquals("ready", cmd);
	}

}
