package ca.concordia.controller;

import java.util.*;
import java.io.IOException;

import ca.concordia.model.Country;
import ca.concordia.model.HumanPlayer;
import ca.concordia.model.LogEntryBuffer;
import ca.concordia.model.MapEditor;
import ca.concordia.view.LogPrintHandler;

/**
 * This class manages start-up related commands for the game.
 *
 * @author Parsa
 * @author Saraswati Saud
 * @since 1.0.0
 * @version 1.2.0
 */
public class StartUpController {
	static Scanner scan = new Scanner(System.in);
	static ArrayList<HumanPlayer> d_players = new ArrayList<HumanPlayer>();
	//	private static Country country;
	HashMap<String, Country> d_map_hash = new HashMap<String, Country>();
	MapEditor d_map;
	boolean d_check = true;
	HashMap<HumanPlayer, Integer> d_player_army = new HashMap<>();

	LogEntryBuffer d_log = new LogEntryBuffer();
	LogPrintHandler d_logPrintHandler = new LogPrintHandler(d_log);

	/**
	 * Constructor
	 */
	public StartUpController() {
	}

	/**
	 * Constructor to initiate mapEditor object
	 * @param p_map
	 */
	public StartUpController(MapEditor p_map){
		this.d_map = p_map;
	}
	/**
	 * This method displays the menu for the game player and assign countries
	 *
	 */
	public void parseCommand() {
		System.out.println("1. Game Player");
		System.out.println("2. Exit");
		System.out.println("\nPlease enter your choice: ");

		try {
			int option = scan.nextInt();
			switch (option) {
				case 1:
					gamePlayer();
					break;
				case 2:
					exitGame();
					break;
				default:
					System.out.println("Invalid Option! Try again");
					break;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * This method displays the menu for adding/removing the player
	 *
	 */
	public void gamePlayer() {

		while(true) {
			System.out.println("0. Add Automatic Player");
			System.out.println("1. Add Player");
			System.out.println("2. Remove Player");
			System.out.println("3. Assign Countries");
			System.out.println("4. Show Map");
			System.out.println("5. Validate Map");
			System.out.println("6. Start game");
			System.out.println("7. Back to main menu");
			System.out.println("\nPlease enter your choice: ");

			try {
				int option = scan.nextInt();
				switch (option) {
					case 0:
						addAutoPlayer();
						break;
					case 1:
						addPlayer();
						break;
					case 2:
						removePlayer();
						break;
					case 3:
						assignRandomCountry();
						break;
					case 4:
						showmap();
						break;
					case 6:
						start();
						break;
					case 7:
						parseCommand();
						break;
					case 5:
						validateMap();
						break;
					default:
						System.out.println("Invalid Option! Try again");
						break;
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	/**
	 * Randomly add desired number of players.
	 *
	 */
	public void addAutoPlayer() {
		System.out.println("Enter numbers of player to join game: ");
		int playerName = scan.nextInt();
		for (int i = 0; i < playerName; i++) {
			HumanPlayer l_player = new HumanPlayer("Player" + i);
			d_players.add(l_player);
			System.out.println("Player " + i + " is successfully added");
			//log auto player adding
			try {
				d_logPrintHandler.LogFileWriter("Auto Player "+ l_player.getPlayerName()+ " was successfully added to the list of players.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Randomly add desired number of players.
	 * @param p_numAutoPlayers number of players to create with name Player[counter]
	 *
	 */
	public void addAutoPlayer(int p_numAutoPlayers) {
		for (int i = 0; i < p_numAutoPlayers; i++) {
			HumanPlayer l_player = new HumanPlayer("Player" + i);
			d_players.add(l_player);
			System.out.println("Player " + i + " is successfully added");
		}
	}

	/**
	 * This method adds the player TODO: Add MapEditor to randomly assign countries
	 * to each player.
	 *
	 */
	public void addPlayer() {
		System.out.println("Enter players name: ");
		String l_playerName = scan.next();
		for(HumanPlayer players: d_players){
			if(players.getPlayerName().equals(l_playerName))
			{
				System.out.println("Player with name "+ l_playerName+ " already exists.");
				return;
			}
		}
		HumanPlayer l_player = new HumanPlayer(l_playerName);
		d_players.add(l_player);
		System.out.println("Player " + l_playerName + " is successfully added");
		//log add player
		try {
			d_logPrintHandler.LogFileWriter("Player "+ l_player.getPlayerName()+ " was successfully added to the list of players.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * add players with name, used in postload phase
	 * @param p_name name of the added player
	 */
	public void addPlayer(String p_name){
		for(HumanPlayer players: d_players){
			if(players.getPlayerName().equals(p_name))
			{
				System.out.println("Player with name "+ p_name+ " already exists.");
				return;
			}
		}
		HumanPlayer l_player = new HumanPlayer(p_name);
		d_players.add(l_player);
		System.out.println("Player " + p_name + " is successfully added");
		//addPlayer log for post load phase
		try {
			d_logPrintHandler.LogFileWriter("Player "+ l_player.getPlayerName()+ " was successfully added to the list of players.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method removes the desired player.
	 * player name will be taken inside the method.
	 *
	 */
	public void removePlayer() {
		System.out.println("Enter player name to remove: ");
		String l_playerName = scan.next();
		HumanPlayer l_remove= null;
		for(HumanPlayer player: d_players){
			if(player.getPlayerName().equals(l_playerName))
				l_remove = player;
		}
		if(l_remove == null)
		{
			System.out.println("No player with name " + l_playerName+ " exists.");
			return;
		}
		d_players.remove(l_remove);

		System.out.println("Player " + l_playerName + " is successfully removed");
		//log remove player
		try {
			d_logPrintHandler.LogFileWriter("Player "+ l_remove.getPlayerName()+ " was successfully removed from the list of players.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * remove player with specific name
	 * @param p_name name of player
	 */
	public void removePlayer(String p_name){
		HumanPlayer l_remove= null;
		for(HumanPlayer player: d_players){
			if(player.getPlayerName().equals(p_name))
				l_remove = player;
		}
		if(l_remove == null)
		{
			System.out.println("No player with name " + p_name+ " exists.");
			return;
		}
		d_players.remove(l_remove);
		System.out.println("Player " + p_name + " is successfully removed");
		//log remove player
		try {
			d_logPrintHandler.LogFileWriter("Player "+ l_remove.getPlayerName()+ " was successfully removed from the list of players.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is assign random country to players
	 * be done after number of the players have been set.
	 *
	 * @throws IOException
	 */
	public void assignCountries() throws IOException {
		if (d_players.size() < 1) {
			System.out.println("You must first assign at lease 2 players!");
			parseCommand();
		} else {
			assignRandomCountry();
		}

	}

	/**
	 * randomly assign countries to players. We used round robin method for assiging countries to players
	 * @throws IOException
	 */
	public void assignRandomCountry() throws IOException {
		if(d_map ==null){
			System.out.println("map not loaded. please load map!");
			return;
		}
		int l_counter = d_players.size()-1;
		for(String country: d_map.getCountries().keySet()){
			if(l_counter==-1){
				l_counter = d_players.size()-1;
			}

			d_players.get(l_counter).putCountry(country, d_map.getCountry(country));
			d_map.getCountry(country).setOwnerPlayer(d_players.get(l_counter));
			l_counter--;
		}

		for(HumanPlayer player: d_players){
			System.out.println("************");
			System.out.println(player.getPlayerName());
			for(String c: player.getCountries().keySet()){
				System.out.println(c);
			}
		}
	}

	/**
	 * This method exit the game
	 *
	 */
	public static void exitGame() {
		System.out.println("Good Bye");
		System.exit(1);
	}

	/**
	 * getting the list of all players inside the game
	 * @return ArrayList of RandomPlayer objects
	 * @see HumanPlayer
	 */
	public ArrayList<HumanPlayer> getPlayers() {
		return this.d_players;
	}


	/**
	 * show all countries and continents, armies on each country, ownership, and
	 * connectivity in a way that enables efficient game play
	 *
	 * @throws IOException
	 */
	public void showmap() throws IOException {
		if(d_map!=null){
			d_map.showmap();
		}else{
			System.out.println("Map not loaded");
		}
	}


	/**
	 * prints if the loaded map is valid
	 */
	public void validateMap() {
		if (d_map != null) {
			if (d_map.validateMap()) {
				System.out.println("Map valid");
			} else {
				System.out.println("Map invalid");
			}
		} else
			System.out.println("Map not loaded");
	}


	/**
	 * Method to check start GameEngine or not.
	 */
	public void start() {
		if (d_check == true){
			GameEngine l_gameEngine = new GameEngine(d_map, d_players);
			l_gameEngine.play();
		}else{
			System.out.println("Please create player or assign countries first!");
			parseCommand();
		}
	}
}