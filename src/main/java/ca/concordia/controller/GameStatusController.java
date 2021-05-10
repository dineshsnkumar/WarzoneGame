package ca.concordia.controller;

import ca.concordia.model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * controller for gameStatus model
 * @version 1.0.1
 */
public class GameStatusController {
    GameStatus d_status;
    MapEditor d_map;
    static ArrayList<HumanPlayer> d_players = new ArrayList<HumanPlayer>();
    static ArrayList<String> d_playersID = new ArrayList<String>();
    HashMap<HumanPlayer, Integer> d_player_army = new HashMap<>();

    /**
     * getter for the loaded map of the game
     */
    public MapEditor getMap(){
        return d_map;
    }

    /**
     * Getter for GameStatus
     */
    public GameStatus getD_status() {
		return d_status;
	}

    /**
     * Setter for GameStatus
     * @param d_status Game status
     */
	public void setstatus(GameStatus d_status) {
		this.d_status = d_status;
	}

	/**
     * getter for the players of the game
     */
    public ArrayList<HumanPlayer> getPlayers(){
        return d_players;
    }

    /**
     * getter for the number of armies left in the reinforcement phase for each player
     */
    public HashMap<HumanPlayer, Integer> getPlayerArmy(){
        return d_player_army;
    }



    /**
     * default constructor for the class
     */
    public GameStatusController(){
    }

    /**
     * load the map of the game;
     * @param p_path pathe to load the game
     */
    public GameStatus loadGame(String p_path){
        int i;
        String[] inputs;
        Country l_country;
        Continent l_continent;
        HumanPlayer l_player;
        Orders l_order;
        try {
            d_map = new MapEditor(p_path);
            System.out.println("****map***");

            BufferedReader l_reader = new BufferedReader(new FileReader("resources/"+p_path+".game"));
            String l_line = l_reader.readLine();
            while(l_line!=null){
                if(l_line.toLowerCase().contains("[players]")){
                    i = 0;
                    System.out.println("****players***");
                    l_line = l_reader.readLine();
                    while(l_line!=null) {
                        if (       l_line.toLowerCase().contains("continents")
                                || l_line.toLowerCase().contains("countries")
                                || l_line.toLowerCase().contains("files")
                                || l_line.toLowerCase().contains("borders")
                                || l_line.toLowerCase().contains("territory")
                                || l_line.toLowerCase().contains("cards"))
                            break;
                        if (!l_line.toLowerCase().contains(";")) {
                            inputs = l_line.split(" ");
                            //adding players
                            if (inputs.length > 1) {
                                if (!d_playersID.contains(inputs[0])) {
                                    HumanPlayer p = new HumanPlayer(inputs[0]);
                                    d_players.add(p);
                                    d_playersID.add(inputs[0]);
                                    d_player_army.put(p, Integer.parseInt(inputs[1]));
                                }
                            }
                        }
                        l_line = l_reader.readLine();
                    }
                }

                if(l_line.toLowerCase().contains("[territory]")){
                    i = 0;
                    System.out.println("****ter***");

                    l_line = l_reader.readLine();
                    while(l_line!=null) {
                        if (       l_line.toLowerCase().contains("continents")
                                || l_line.toLowerCase().contains("countries")
                                || l_line.toLowerCase().contains("files")
                                || l_line.toLowerCase().contains("borders")
                                || l_line.toLowerCase().contains("players")
                                || l_line.toLowerCase().contains("cards"))
                            break;
                        if (!l_line.toLowerCase().contains(";")) {
                            inputs = l_line.split(" ");
                            //adding players
                            if (inputs.length > 1) {
                                if(d_playersID.contains(inputs[0])) {
                                    HumanPlayer p = null;
                                    for(HumanPlayer q: d_players){
                                        if(q.getPlayerName().equals(inputs[0]))
                                            p = q;
                                    }
                                    for (int n = 1;n<inputs.length; n++){
                                        d_map.getCountry(inputs[n]).setNumberOfArmies(Integer.parseInt(inputs[n+1]));
                                        d_map.getCountry(inputs[n]).setOwnerPlayer(p);
                                        p.putCountry(inputs[n], d_map.getCountry(inputs[n]));
                                        n++;
                                    }
                                }
                            }
                        }
                        l_line = l_reader.readLine();
                    }
                }

                if(l_line.toLowerCase().contains("[cards]")){
                    i = 0;
                    System.out.println("****card***");

                    l_line = l_reader.readLine();
                    while(l_line!=null) {
                        if (       l_line.toLowerCase().contains("continents")
                                || l_line.toLowerCase().contains("countries")
                                || l_line.toLowerCase().contains("files")
                                || l_line.toLowerCase().contains("borders")
                                || l_line.toLowerCase().contains("players")
                                || l_line.toLowerCase().contains("territory"))
                            break;
                        if (!l_line.toLowerCase().contains(";")) {
                            inputs = l_line.split(" ");
                            //adding players
                            if (inputs.length > 1) {
                                if(d_playersID.contains(inputs[0])) {
                                    HumanPlayer p = null;
                                    for(HumanPlayer q: d_players){
                                        if(q.getPlayerName().equals(inputs[0]))
                                            p = q;
                                    }
                                    //checks the input order of the player and setting that order into his/her cards
                                    for(int n = 1; n<inputs.length; n++){
                                        if(inputs[n].toLowerCase().startsWith("bomb")){
                                            p.setOwnedCards(Orders.BOMB);
                                        }else if(inputs[n].toLowerCase().startsWith("blockade")){
                                            p.setOwnedCards(Orders.BLOCKADE);
                                        }else if(inputs[n].toLowerCase().startsWith("airlift")){
                                            p.setOwnedCards(Orders.AIRLIFT);
                                        }else if(inputs[n].toLowerCase().startsWith("negotiate")){
                                            p.setOwnedCards(Orders.NEGOTIATE);
                                        }
                                    }
                                }
                            }
                        }
                        l_line = l_reader.readLine();
                    }
                }
                l_line = l_reader.readLine();
            }
            d_map.showmap();
            for(HumanPlayer r:d_players){
                System.out.println("******"+r.getPlayerName()+"***"+r.getOwnedCards().size());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new GameStatus(d_map, d_player_army,d_players,new ArrayList<>());
    }
}
