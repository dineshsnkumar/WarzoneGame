package ca.concordia.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import ca.concordia.controller.*;
import ca.concordia.view.LogPrintHandler;

/**
 * This class inherits Play class and is responsible for functionalities such as setting players
 */
public class PlaySetup extends Play {

	StartUpController d_startUpController;
	MapEditor d_mapEditor;
	GameEngine d_gameEngine;
	GameStatusController d_gameStatusController;
	TournamentController d_tournamentController;

	LogEntryBuffer d_log = new LogEntryBuffer();
	LogPrintHandler d_logPrintHandler = new LogPrintHandler(d_log);

	/**
	 * constructor
	 * @param p_ge GameController obj
	 */
	public PlaySetup(GameController p_ge) {
		super(p_ge);
	}

	/**
	 * Constructor
	 * @param p_gc GameController obj
	 * @param p_me MapEditor Obj
	 */
	public PlaySetup(GameController p_gc, MapEditor p_me) {
		super(p_gc);
		d_startUpController = new StartUpController(p_me);
		d_mapEditor = p_me;
		d_gameStatusController = new GameStatusController();
	}

	/**
	 * Loads the map
	 */
	public void loadMap() {
		d_mapEditor.loadMap();
	}


	/**
	 * Assigns players
	 */
	public void setPlayers() {
		System.out.println("follow the structure below\n" + "gameplayer [-options] [name]\n"
				+ "-options:\t-add [name], -remove[name]\n" + "\t\t\t-auto [number of players]");

		Scanner scan = new Scanner(System.in);
		String l_command = scan.nextLine();

		String[] l_inputs = l_command.split(" ");
		for (int i = 1; i < l_inputs.length; i++) {
			if (l_inputs[i].equals("-add")) {
				d_startUpController.addPlayer(l_inputs[i + 1]);

				try {
					d_logPrintHandler.LogFileWriter("Players " + l_inputs[i + 1] + " created.");
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (l_inputs[i].equals("-remove")) {
				// remove continent
				d_startUpController.removePlayer(l_inputs[i + 1]);

				try {
					d_logPrintHandler.LogFileWriter("Players " + l_inputs[i + 1] + " removed.");
				} catch (IOException e) {
					e.printStackTrace();
				}

			} else if (l_inputs[i].equals("-auto")) {
				d_startUpController.addAutoPlayer(Integer.parseInt(l_inputs[i + 1]));
			}
		}

		ArrayList<HumanPlayer> l_players = d_startUpController.getPlayers();
		d_gameEngine = new GameEngine(d_mapEditor, d_startUpController.getPlayers());

	}

	/**
	 * Assign Countries
	 */
	public void assignCountries() {
		try {
			d_startUpController.assignCountries();
//			d_gameEngine = new GameEngine(d_mapEditor, d_startUpController.getPlayers());
			next();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Assign Reinforcements
	 */
	public void reinforce() {
		printInvalidCommandMessage();
	}

	/**
	 * Issues Orders
	 */
	public void issueOrder() {
		printInvalidCommandMessage();
	}

	/**
	 * Execute Orders
	 */
	public void executeOrder() {
		printInvalidCommandMessage();
	}

	/**
	 * End Game
	 */
	public void endGame() {
		printInvalidCommandMessage();
	}

	/**
	 * Tournament mode method to get the commands related to this method as in input
	 */
	@Override
	public void tournamentMode() {
		d_tournamentController = new TournamentController();
		System.out.println("Enter a tournament game play commands");
		System.out.println("tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns");

		Scanner scan = new Scanner(System.in);
		String l_command = scan.nextLine();

		String message = d_tournamentController.parseCommand(l_command);
		System.out.println(message);
		scan.close();
	}

	/**
	 * Transition to the next
	 */
	public void next() {
		ge.setPhase(new Reinforcement(ge, d_startUpController, d_gameEngine));
	}

	/**
	 * Save Game
	 */
	public void saveGame() {
	}

	/**
	 * Load Current Game
	 * 
	 */
	public void loadCurrentGame() {

	}

}
