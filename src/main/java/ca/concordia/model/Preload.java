package ca.concordia.model;

import java.io.FileNotFoundException;
import java.util.Scanner;

import ca.concordia.controller.*;

/**
 * This class loads map during the preload phase
 */

public class Preload extends Edit {

	MapEditor d_mapEditor;
	GameEngine d_gameEngine;

	/**
	 * PreLoad constructor
	 * 
	 * @param p_ge GameController obj
	 */
	public Preload(GameController p_ge) {
		super(p_ge);
	}

	/**
	 * PreLoad constructor
	 * 
	 * @param p_mapEditor object of the current map editor
	 * @param p_ge GameController obj
	 */
	public Preload(MapEditor p_mapEditor, GameController p_ge) {
		super(p_ge);
		d_mapEditor = p_mapEditor;

	}

	/**
	 * Load Map
	 */
	public void loadMap() {

		Scanner l_scan = new Scanner(System.in);
		System.out.println("1.Start a new Game" +
				           "\n2.Load saved Game");
		String l_command = l_scan.nextLine();
		while(true){
			if(l_command.equals("1")||l_command.equals("2"))
				break;
			System.out.println("wrong input. try again");
			l_command = l_scan.nextLine();
		}
		if(l_command.equals("1")) {
			System.out.println("Enter the type of file : Warzone or  Conquest");
			String l_type_file = l_scan.nextLine().toLowerCase();

			System.out.println("Please enter map file name: (e.g haiti/asia)");
			String l_entry = l_scan.nextLine();

			// Warzone File
			if (l_type_file.equalsIgnoreCase("warzone")) {

				MapReader d_reader = new MapReader();
				try {
					d_mapEditor = d_reader.loadMap(l_entry);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				// Conquest File
			} else if (l_type_file.equalsIgnoreCase("conquest")) {

				//ConquestMapReader d_conquest_reader = new ConquestMapReader();
				MapReader d_conquest_reader = new Adapter(new ConquestMapReader());

				try {
					d_mapEditor = d_conquest_reader.loadMap(l_entry);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

			}

			ge.setPhase(new PostLoad(ge, d_mapEditor, d_gameEngine));
		}
		else{
			System.out.println("Enter the name of the saved game");
			String l_path = l_scan.nextLine();
			GameStatusController l_statusController = new GameStatusController();
			GameStatus l_gameStatus;

			l_gameStatus = l_statusController.loadGame(l_path);
			StartUpController l_start = new StartUpController(l_gameStatus.getMap());
			for(Player p: l_gameStatus.getPlayers()){
				l_start.addPlayer(p.getPlayerName());
			}
			GameEngine l_gameEngine  = new GameEngine(l_gameStatus.getMap(), l_gameStatus.getPlayers());
			l_gameEngine.setD_player_army(l_gameStatus.getPlayerArmies());
			ge.setPhase(new Reinforcement(ge, l_start, l_gameEngine));
		}
	}

	/**
	 * Edit phase handles countries/continent/Neighbor
	 */
	public void edit() {
		printInvalidCommandMessage();
	}

	/**
	 * Edit country
	 */
	public void editCountry() {
		printInvalidCommandMessage();
	}

	/**
	 * Edit continent
	 */
	public void editContinent() {
		printInvalidCommandMessage();
	}

	/**
	 * Saves the map
	 */
	public void saveMap() {
		printInvalidCommandMessage();
	}

	/**
	 * Edits neighbors
	 */
	public void editNeighbor() {
		printInvalidCommandMessage();
	}

	/**
	 * validates map
	 */
	public void validateMap() {
		printInvalidCommandMessage();
	}

	/**
	 * Transition to next phase
	 */
	public void next() {
		System.out.println("must load map");
	}

	/**
	 * print invalid msg commands for saveGame() in preload stage
	 */
	@Override
	public void saveGame() {
		printInvalidCommandMessage();
	}
}
