package ca.concordia.control.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import ca.concordia.controller.TournamentController;

/**
 * To test TournamentController
 */
public class TestTournamentController {
	TournamentController controller;
	String errorMessage;
	
	/**
     * Sets up the context for test.
     */
	@Before
	public void before() {
		controller = new TournamentController();
		errorMessage = "Command has to be in the form of 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'";
	}
	
	/**
     * Tests to check if user passes invalid tournament command
     */
	@Test
	public void testUserInputCommand() {
		String command = "tournament";
		controller.parseCommand(command);		
		Assert.assertEquals("Command has to be in the form of 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'", errorMessage);
	}
	
	/**
     * Tests to check if user missed to add mapFile command
     */
	@Test
	public void testMapFileCommand() {
		String command = "tournament -P Aggressive Cheater -G 2 -D 12";
		controller.parseCommand(command);		
		Assert.assertEquals("Command has to be in the form of 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'", errorMessage);
	}
	
	/**
     * Tests to check if user missed to add computer player strategies
     */
	@Test
	public void testPlayerStrategiesCommand() {
		String command = "tournament -M haiti -G 2 -D 12";
		controller.parseCommand(command);		
		Assert.assertEquals("Command has to be in the form of 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'", errorMessage);
	}
	
	/**
     * Tests to check if user missed to add maximum number of games to be played in each map
     */
	@Test
	public void testMaximumGamesCommand() {
		String command = "tournament -M haiti -P Aggressive Cheater -D 12";
		controller.parseCommand(command);		
		Assert.assertEquals("Command has to be in the form of 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'", errorMessage);
	}
	
	/**
     * Tests to check if user missed to add maximum number of turns for each game
     */
	@Test
	public void testTotalTurnsCommand() {
		String command = "tournament -M haiti -P Aggressive Cheater -G 2";
		controller.parseCommand(command);		
		Assert.assertEquals("Command has to be in the form of 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'", errorMessage);
	}
	
	/**
     * Tests to check if user failed to add map names in the command line
     */
	@Test
	public void inputMapNamesCommand() {
		String command = "tournament -M -P Aggressive Cheater -G 2 -D 12";
		controller.parseCommand(command);		
		Assert.assertEquals("Command has to be in the form of 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'", errorMessage);
	}
	
	/**
     * Tests to check if user failed to add computer player strategies value in the command line
     */
	@Test
	public void inputPlayerStrategiesCommand() {
		String command = "tournament -M haiti -P -G 2 -D 12";
		controller.parseCommand(command);		
		Assert.assertEquals("Command has to be in the form of 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'", errorMessage);
	}
	
	/**
     * Tests to check if user failed to add maximum number of games value in the command line
     */
	@Test
	public void inputMaxGameCommand() {
		String command = "tournament -M haiti -P Aggressive Cheater -G -D 12";
		controller.parseCommand(command);		
		Assert.assertEquals("Command has to be in the form of 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'", errorMessage);
	}
	
	/**
     * Tests to check if user failed to add maximum number of turns value in the command line
     */
	@Test
	public void inputMaxTurnsCommand() {
		String command = "tournament -M haiti -P Aggressive Cheater -G 2 -D";
		controller.parseCommand(command);		
		Assert.assertEquals("Command has to be in the form of 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'", errorMessage);
	}
}
