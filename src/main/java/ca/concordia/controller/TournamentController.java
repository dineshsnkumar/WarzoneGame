package ca.concordia.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ca.concordia.model.MapEditor;
import ca.concordia.model.RandomPlayer;
import ca.concordia.model.AggressivePlayer;
import ca.concordia.model.BenevolentPlayer;
import ca.concordia.model.CheaterPlayer;
import ca.concordia.model.Country;
import ca.concordia.model.HumanPlayer;
import ca.concordia.model.LogEntryBuffer;
import ca.concordia.view.LogPrintHandler;

/**
 * Manages tournament related commands.
 */
public class TournamentController {
    MapEditor d_mapEditor;
    StartUpController d_startUpController;
    LogEntryBuffer d_log = new LogEntryBuffer();
    LogPrintHandler d_logPrintHandler = new LogPrintHandler(d_log);
    GameEngine d_gameEngine;

    static ArrayList<RandomPlayer> d_players = new ArrayList<RandomPlayer>();

    /**
     * Function responsible for handling command for tournament
     * @param p_newCommand Command to be interpreted
     * @return success if command is valid else displays an appropriate
     * error message which indicates reason of failure
     */
    public String parseCommand(String p_newCommand) {
        String[] l_inputs = p_newCommand.split(" ");
        String l_commandName = l_inputs[0];
        int l_numOfGames;
        int l_numOfTurns;
        ArrayList<String> l_maps = new ArrayList<String>();
        ArrayList<String> l_strategies = new ArrayList<String>();

        if(l_commandName.equals("tournament")) {
            try {
                if(l_inputs[1].equals("-M")) {
                    int i = 2;

                    // working with maps (-M)
                    while(!l_inputs[i].equals("-P")) {
                        if (i < l_inputs.length) {
                            l_maps.add(l_inputs[i]);
                        }
                        else {
                            return printInvalidCommandMessage();
                        }
                        i++;
                    }
                    System.out.println("1. Displaying maps: " + l_maps);

                    // Working with player strategies (-P)
                    if(l_maps.size() >= 1 && l_maps.size() <= 5 && allMapExists(l_maps)) {
                        if(l_inputs[i].equals("-P")) {
                            int l_newIndex = i + 1;
                            while(!l_inputs[l_newIndex].equals("-G")) {
                                if (l_newIndex < l_inputs.length) {
                                    if(isPlayerStrategyValid(l_inputs[l_newIndex])) {
                                        l_strategies.add(l_inputs[l_newIndex]);
                                    } else {
                                        return printInvalidCommandMessage();
                                    }
                                } else {
                                    return printInvalidCommandMessage();
                                }
                                l_newIndex++;
                            }

                            System.out.println("2. Displaying strategies: " + l_strategies);

                            // Working with number of games (-G)
                            if(l_strategies.size() >= 2 && l_strategies.size() <= 4 && isPlayerStrategyDistinct(l_strategies)) {
                                if(l_inputs[l_newIndex].equals("-G")) {
                                    if(l_newIndex + 1 < l_inputs.length) {
                                        if(l_inputs[l_newIndex + 1].matches("[1-5]")) {
                                            l_numOfGames = Integer.parseInt(l_inputs[l_newIndex + 1]);

                                            System.out.println("3. Displaying number of Games: " + l_numOfGames);
                                        } else {
                                            return "Please add number of games between (1-5)";
                                        }
                                    }
                                    else {
                                        return printInvalidCommandMessage();
                                    }

                                    int l_index = l_newIndex + 2;

                                    // Working with number of Turns (-D)
                                    if(l_inputs[l_index].equals("-D")) {
                                        if (l_index + 1 < l_inputs.length) {
                                            l_numOfTurns = Integer.parseInt(l_inputs[l_index + 1]);
                                            if(l_numOfTurns >= 10 && l_numOfTurns <= 50) {
                                                System.out.println("4. Displaying number of Turns: " + l_numOfTurns);
                                                playTournament(l_maps, l_strategies, l_numOfGames, l_numOfTurns);
                                                return "success";
                                            } else {
                                                return "Please add number of turns between (10-50)";
                                            }
                                        } else {
                                            return printInvalidCommandMessage();
                                        }
                                    } else {
                                        return printInvalidCommandMessage();
                                    }

                                } else {
                                    return printInvalidCommandMessage();
                                }
                            } else {
                                return "Error in Player Strategies: Please verify while adding player strategies";
                            }

                        } else {
                            return printInvalidCommandMessage();
                        }
                    } else {
                        return "Error in Maps: Please verify while adding maps";
                    }
                } else {
                    return printInvalidCommandMessage();
                }
            } catch(ArrayIndexOutOfBoundsException | IOException e) {
                return printInvalidCommandMessage();
            }
        } else {
            return printInvalidCommandMessage();
        }
    }

    /**
     * Conducts the tournament as per the mentioned arguments.
     * @param p_mapFiles List of map files to play on
     * @param p_strategies List of player strategies
     * @param p_numOfGames Number of games to play
     * @param p_numOfTurns Number of turns to play in each game 
     */
    public void playTournament(ArrayList<String> p_mapFiles, ArrayList<String> p_strategies, int p_numOfGames, int p_numOfTurns) throws IOException{
        int l_totalPlayers = p_strategies.size();
        int l_traversalCounter = 0;
        int l_gameNumber = 0;

        HashMap<Integer, String> l_winner = new HashMap<Integer, String>();
        Country d_country = null;
    	List<Country> d_countries;    	    	
    	MapEditor d_map;
        
        for(String l_mapName : p_mapFiles) {
        	for(int  i = 1; i <= p_numOfGames; i++) {
	    		d_map = new MapEditor(l_mapName);
	    		for (String l_strategy : p_strategies) {
	        		HumanPlayer player = new HumanPlayer(l_strategy);        		
	        		d_countries = d_map.getNeighbors().get(d_country);
	        		
	        		if(l_strategy.equals("Aggressive")) {        		
	        			AggressivePlayer l_aggressive = new AggressivePlayer(player, d_countries, d_map);
	        		} else if(l_strategy.equals("Benevolent")) {
	        			BenevolentPlayer l_benevolant = new BenevolentPlayer(player, d_countries, d_map);
	        		} else if(l_strategy.equals("Random")) {
	        			RandomPlayer l_random = new RandomPlayer(player, d_countries, d_map);
	        		} else {
	        			CheaterPlayer l_cheater = new CheaterPlayer(player, d_countries, d_map);
	        		}
	        	}
	    		
	    		try {
	                d_logPrintHandler.LogFileWriter("--------------------------------------------");
	                d_logPrintHandler.LogFileWriter("Game " + i + " on " + l_mapName + ": Countries allocated amongst players");
	            } catch(IOException e) {
	                e.printStackTrace();
	            }
        	}    	
        }
        // Displaying winner
        displayTournamentResult(l_winner, p_mapFiles);
    }

    /**
     * Displays the result of the tournament
     * @param p_winner HashMap representing the winner of the game indexed based on the number of the game.
     * @param p_maps List of maps used to play the tournament.
     */
    public void displayTournamentResult(HashMap<Integer, String> p_winner, ArrayList<String> p_maps) {
        int l_numOfGames = 4;
        int l_index = 0;
        System.out.format("%15s", " ");
        for(int i = 1; i <= l_numOfGames; i++) {
            System.out.format("%15s", "Game " + i);
        }
        System.out.println();

        for(int i = 0; i < p_maps.size(); i++){
            System.out.format("%15s", p_maps.get(i));

            for(int k = 1; k <= l_numOfGames; k++) {
                l_index++;
                System.out.format("%15s", p_winner.get(l_index));
            }
            System.out.println();
        }
    }

    /**
     * Ensures that all maps exist
     * @param p_mapLists list of name of maps
     * @return true if valid else false
     */
    public boolean allMapExists(ArrayList<String> p_mapLists) {
        int l_counter = 0;
        for(String s:p_mapLists) {
            File d_file = new File("resources/" + s + ".map");
            if(d_file.exists())
                l_counter++;
        }
        if(l_counter == p_mapLists.size())
            return true;
        else
            return false;
    }

    /**
     * Ensures player strategy is valid
     * @param p_str strategy of the player
     * @return true if valid else false
     */
    public boolean isPlayerStrategyValid(String p_str) {
        String[] l_playerStr = new String[] { "Aggressive", "Benevolent", "Random", "Cheater" };

        for(int i = 0; i < l_playerStr.length; i++) {
            if(p_str.equals(l_playerStr[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * Ensure that all strategies of player are distinct
     * @param p_lists list of player's strategy
     * @return true if valid else false
     */
    public boolean isPlayerStrategyDistinct(ArrayList<String> p_lists) {
        for(int i = 0; i < p_lists.size(); i++) {
            for (int j = i+1; j < p_lists.size(); j++) {
                if(p_lists.get(i).equals(p_lists.get(j)))
                    return false;
            }
        }
        return true;
    }

    /**
     *Method prints failure message for tournament command
     *@return void
     */
    public String printInvalidCommandMessage() {
        return "Command has to be in the form of 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'";
    }
}
