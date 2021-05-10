package ca.concordia.controller;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import ca.concordia.model.*;
import ca.concordia.view.LogPrintHandler;
import ca.concordia.view.ShowGameStatus;

/** This class handles the deployment phase and parsing user commands
 * @author team memebr
 * @version 1.0.2
 */
public class GameEngine {
	Scanner d_scan = new Scanner(System.in);
	MapEditor d_map;
	static ArrayList<HumanPlayer> d_players = new ArrayList<HumanPlayer>();
	HashMap<HumanPlayer, Integer> d_player_army = new HashMap<>();
	ArrayList<HashMap<HumanPlayer, String>> d_orders = new ArrayList<>();
	HumanPlayer d_winner;


	LogEntryBuffer d_log = new LogEntryBuffer();
	LogPrintHandler d_logPrintHandler = new LogPrintHandler(d_log);
	/**
	 * constructor for GameEngine
	 *
	 * @param p_map     the loaded map for the game play
	 * @param p_players the players created and added for playing
	 */
	public GameEngine(MapEditor p_map, ArrayList<HumanPlayer> p_players) {
		d_map = p_map;
		d_players = p_players;
	}

	/**
	 * based on the handout, each player gets #territories/3 (rounded down) armies.
	 * this function assign the specific number of armies based on territory to players.
	 */
	public void reinforcement() {
		for (HumanPlayer player : d_players) {
			int l_numArmies;
			if ((int) (player.getOwnedCountries().size() / 3) > 3) {
				if(d_player_army.get(player)==null || d_player_army.get(player)==0)
					l_numArmies = 0;
				else
					l_numArmies = d_player_army.get(player);
				d_player_army.put(player,l_numArmies+ (int) (player.getOwnedCountries().size() / 3));
			} else {
				if(d_player_army.get(player)==null || d_player_army.get(player)==0)
					l_numArmies = 0;
				else
					l_numArmies = d_player_army.get(player);
				d_player_army.put(player, l_numArmies + 3);
			}
		}
	}

	/**
	 * first the reinforcement takes place and then for each player we assign specific number of armies to each territory.
	 */
	public void armyAssign() {
		reinforcement();
		System.out.println("reinforcement done");
		for (HumanPlayer player : d_players) {
			System.out.println("assign armies for player " + player.getPlayerName());
			assignArmies(player);
		}
	}

	/**
	 * Assign armies to each player. asks how many armies is wanted to be deployd in each country (territory)
	 *
	 * @param p_player the player which armies are deploying in his/her territory
	 */
	public void assignArmies(HumanPlayer p_player) {
		Scanner scan = new Scanner(System.in);
		int l_armiesLeft = d_player_army.get(p_player);
		int l_totalArmies = l_armiesLeft;
		Deploy l_deploy;
		for (String country : p_player.getOwnedCountries().keySet()) {
			if (l_armiesLeft <= 0) {
				break;
			} else {
				System.out.println("You have " + l_armiesLeft + " armies left.");
				System.out.println("How many armies to assign to the Territory " + country);
				int d_scan = scan.nextInt();
				if ((l_totalArmies - d_scan) < 0) {
					System.out.println("You can't assign more armies than armies left. Try again.");
				} else {
//					d_map.getCountry(country).setNumberOfArmies(d_map.getCountry(country).getNumberOfArmies() + d_scan);
					l_deploy= new Deploy(p_player, d_map.getCountry(country), d_scan, d_player_army.get(p_player));
					if(l_deploy.valid())
					{l_deploy.execute();
						l_armiesLeft -= d_scan;
						d_player_army.put(p_player, l_armiesLeft);}
					else{
						System.out.println("invalid deploy command.");
					}
				}
			}
			// Log for assigning armies
			try {
				d_logPrintHandler.LogFileWriter("Player "+p_player.getPlayerName()+" gets "+d_map.getCountry(country).getNumberOfArmies()+ " armies assigned ");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}


	/**
	 * taking the orders in a round-robin manner until all players finish their orders.
	 */
	public void takeOrders() {
		Scanner scan = new Scanner(System.in);
		HashMap<HumanPlayer, Boolean> l_ordering = new HashMap<>();
		String l_command;
		for (HumanPlayer player : d_players) {
			l_ordering.put(player, true);
		}
		while (l_ordering.values().contains(true)) {
			d_orders.add(new HashMap<>());
			for (HumanPlayer player : d_players) {
				if (l_ordering.get(player) == true) {
					System.out.println("Player " + player.getPlayerName() + " enter command");
					l_command = scan.nextLine();
					if (l_command.equals("finish"))
						l_ordering.put(player, false);
					else {
						if (l_command.startsWith("deploy")) {
							Deploy l_order = new Deploy(player, d_map.getCountry(l_command.split(" ")[1])
									, Integer.parseInt(l_command.split(" ")[2]));
							player.issue_order(l_order, Orders.DEPLOY);

							//Log deploy order
							try {
								d_logPrintHandler.LogFileWriter("Player "+ player.getPlayerName()+ " ordered Deploy command.");
							} catch (IOException e) {
								e.printStackTrace();
							}


						} else if (l_command.startsWith("advance")) {
							Advance l_order = new Advance(player, d_map.getCountry(l_command.split(" ")[1])
									, d_map.getCountry(l_command.split(" ")[2]),
									Integer.parseInt(l_command.split(" ")[3]), d_map);
							player.issue_order(l_order, Orders.ADVANCE);

							//Log for Advance Order
							try {
								d_logPrintHandler.LogFileWriter("Player "+ player.getPlayerName()+ " ordered an advance command.");
							} catch (IOException e) {
								e.printStackTrace();
							}

						} else if (l_command.startsWith("attack")) {
							Attack l_order = new Attack(player, (HumanPlayer) d_map.getCountry(l_command.split(" ")[2]).getOwnerPlayer(),
									d_map.getCountry(l_command.split(" ")[1]),
									d_map.getCountry(l_command.split(" ")[2]),
									Integer.parseInt(l_command.split(" ")[3]),
									d_map);
							player.issue_order(l_order, Orders.ADVANCE);

							//log for attack
							try {
								d_logPrintHandler.LogFileWriter("Player "+ player.getPlayerName()+ " issued attack command.");
							} catch (IOException e) {
								e.printStackTrace();
							}

						} else if (l_command.startsWith("airlift")) {
							Airlift l_order = new Airlift(player, d_map.getCountry(l_command.split(" ")[1]),
									d_map.getCountry(l_command.split(" ")[2]),
									Integer.parseInt(l_command.split(" ")[3]));
							player.issue_order(l_order, Orders.AIRLIFT);

							//log for airlift
							try {
								d_logPrintHandler.LogFileWriter("Player "+ player.getPlayerName()+ " issued Airlift command.");
							} catch (IOException e) {
								e.printStackTrace();
							}

						} else if (l_command.startsWith("bomb")) {
							Bomb l_order = new Bomb(player, d_map.getCountry(l_command.split(" ")[1]), d_map);
							player.issue_order(l_order, Orders.BOMB);

							//log for bomb
							try {
								d_logPrintHandler.LogFileWriter("Player "+ player.getPlayerName()+ " issued bomb command.");
							} catch (IOException e) {
								e.printStackTrace();
							}

						} else if (l_command.startsWith("negotiate")) {
							Negotiate l_order = new Negotiate(player);
							player.issue_order(l_order, Orders.NEGOTIATE);

							//log for negotiate command
							try {
								d_logPrintHandler.LogFileWriter("Player "+ player.getPlayerName()+ " issued negotiate command.");
							} catch (IOException e) {
								e.printStackTrace();
							}

						} else if (l_command.startsWith("blockade")) {
							Blockade l_order = new Blockade(player, d_map.getCountry(l_command.split(" ")[1]));
							player.issue_order(l_order, Orders.BLOCKADE);

							//log for blockade command
							try {
								d_logPrintHandler.LogFileWriter("Player "+ player.getPlayerName()+ " issued blockade command.");
							} catch (IOException e) {
								e.printStackTrace();
							}

						} else if(l_command.startsWith("Deploy")){
							Deploy l_order = new Deploy(player,d_map.getCountry(l_command.split(" ")[1]),
									Integer.parseInt(l_command.split(" ")[2]));
						}else if(l_command.startsWith("Showmap")){
							d_map.showmap();
							//log for showMap command
							try {
								d_logPrintHandler.LogFileWriter("Player "+ player.getPlayerName()+ " issued showmap command.");
							} catch (IOException e) {
								e.printStackTrace();
							}

						}

						d_orders.get(d_orders.size() - 1).put(player, l_command);
					}
				}
			}
		}
	}

	/**
	 * if a player conquers 3/4 of all territories we declare it as Winner
	 * @return true if a winner is found
	 */
	public boolean finish() {
		int l_winnerNumber = (int) d_map.getCountries().size() * 3 / 4;
		for (HumanPlayer player : d_players) {
			if (player.getCountries().size() >= l_winnerNumber) {
				d_winner = player;
				System.out.println("Player " + player.getPlayerName() + " won.");
				return true;
			}
			//log for finished game
//			try {
//				d_logPrintHandler.LogFileWriter("Player "+ player.getPlayerName()+ " won the game with "+ d_winner.getCountries().size()+" number of countries.");
//			} catch (IOException e) {
//				e.printStackTrace();
//			}

		}
		return false;
	}

	/**
	 * play method for playing the game
	 * it has 3 main phases, 1 army assignment, 2 taking orders from players, 3 executing the orders
	 *
	 */
	public void play() {
		int i = 1;
		while (true) {
			System.out.println("**************Round " + i + "**************");
			armyAssign();//initial army assignment
			takeOrders();
			executeAllOrders();
			i++;
			if (finish()) {
				break;
			}
		}
	}

	/**
	 * execute the issued orders.
	 * checks if the player has the card for special orders
	 * show an updated version of map at the end.
	 */
	public void executeAllOrders() {
		HashMap<HumanPlayer, Boolean> l_ordering = new HashMap<>();
		//adding the orders to players(HumanPlayer objects)
		for (HumanPlayer player : d_players) {
			l_ordering.put(player, true);
		}
		//calling next_order for an object of order if the order is in the list of player's orders
		while (l_ordering.containsValue(true)) {
			for (HumanPlayer player : d_players) {
				Order l_order = player.next_order();
				if (l_order == null) {
					l_ordering.put(player, false);
				} else {
					//execute the checked order of based on its title such as "Attack"
					if (l_order.getClass().toString().contains("Attack")) {
						l_order.execute();

						//execute the checked order of based on its title such as "Bomb" and remove that order from players' order cards
					} else if (l_order.getClass().toString().contains("Bomb")) {
						if (player.getOwnedCards().contains(Orders.BOMB)) {
							l_order.execute();
							player.removeOwnedCards(Orders.BOMB);

						} else {
							System.out.println("Player doesn't have Bomb card");
						}
					} else if (l_order.getClass().toString().contains("Airlift")) {
						if (player.getOwnedCards().contains(Orders.AIRLIFT)) {
							l_order.execute();
							player.removeOwnedCards(Orders.AIRLIFT);

						} else {
							System.out.println("Player doesn't have AirLift card");
						}
					} else if (l_order.getClass().toString().contains("Negotiate")) {
						if (player.getOwnedCards().contains(Orders.NEGOTIATE)) {
							l_order.execute();
							player.removeOwnedCards(Orders.NEGOTIATE);
							// log Negotiate execution
							try {
								d_logPrintHandler.LogFileWriter("Player " + player.getPlayerName() + " ordered Negotiate execution.");
							} catch (IOException e) {
								e.printStackTrace();
							}

						} else {
							System.out.println("Player doesn't have Negotiate card");
						}
					} else if (l_order.getClass().toString().contains("Advance")) {
						l_order.execute();
						// log Advance execution
						try {
							d_logPrintHandler.LogFileWriter("Player " + player.getPlayerName() + " ordered Advance execution.");
						} catch (IOException e) {
							e.printStackTrace();
						}

					} else if (l_order.getClass().toString().contains("Blockade")) {
						if (player.getOwnedCards().contains(Orders.BLOCKADE)) {
							l_order.execute();
							player.removeOwnedCards(Orders.BLOCKADE);

						} else {
							System.out.println("Player doesn't have Blockade card");
						}
					} else if (l_order.getClass().toString().contains("Deploy")) {
						l_order.execute();
					}

//					Orders l_type = player.next_type();
//					if (player.hasCard(l_type)) {
//						l_order.printOrder();
//						l_order.execute();
//					} else {
//						System.out.println("Player doesn't have the " + l_type + " card.");
//					}
				}
			}

		}

		ArrayList<HumanPlayer> l_toRemove = new ArrayList<>();
		System.out.println("Updated Map");
		d_map.showmap();
		//checking if the player is loser or not
		for(HumanPlayer p: d_players){
			boolean looser = false;
			if(p.getOwnedCountries().size() == 0)
				l_toRemove.add(p);
		}
		for(HumanPlayer q: l_toRemove)
			d_players.remove(q);

		for (HumanPlayer p : d_players) {
			System.out.println("Player " + p.getPlayerName() + " cards:");
			for (Orders order : p.getOwnedCards()) {
				System.out.println(order);
			}
			System.out.println("Player " + p.getPlayerName() + " countries:");
			for (String country : p.getCountries().keySet()) {
				System.out.println(country);
			}
		}
		//check if the player wants to replay
		System.out.println("Do you want to save the game?[Y/n]");
		Scanner save = new Scanner(System.in);
		String answer = save.nextLine();
		if (answer.equals("") || answer.equals("Y") || answer.equals("y")) {
			GameStatus g = new GameStatus(d_map, d_player_army, d_players,
					d_orders);
			ShowGameStatus s = new ShowGameStatus(g);
			s.saveStatus();
		}
	}
	/** Get d_players list
	 * @return the d_players
	 */
	public static ArrayList<HumanPlayer> getD_players() {
		return d_players;
	}

	/** Set d_players list
	 * @param d_players the d_players to set
	 */
	public static void setD_players(ArrayList<HumanPlayer> d_players) {
		GameEngine.d_players = d_players;
	}

	/** Get d_player_army
	 * @return the d_player_army
	 */
	public HashMap<HumanPlayer, Integer> getD_player_army() {
		return d_player_army;
	}

	/** Set d_player_army
	 * @param d_player_army the d_player_army to set
	 */
	public void setD_player_army(HashMap<HumanPlayer, Integer> d_player_army) {
		this.d_player_army = d_player_army;
	}

	/**
	 * returns the winner of the game
	 * @return the object of Player winner
	 */
	public HumanPlayer get_Winner(){
		return d_winner;
	}
}


	

