package ca.concordia.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class stores the status of the game. the status contains the players, their teritories with the armies and
 * the cards they have.
 */
public class GameStatus {
    MapEditor d_map;
    static ArrayList<HumanPlayer> d_players = new ArrayList<HumanPlayer>();
    HashMap<HumanPlayer, Integer> d_player_army = new HashMap<>();
    ArrayList<HashMap<HumanPlayer, String>> d_orders = new ArrayList<>();

    /**
     * constructor for class GameStatus
     * @param p_map mapEditor obj
     * @param p_player_army hashmap of players armies with object of human player and an integer id to be assigned to it
     * @param p_players players arraysList form HumanPlayer
     * @param p_orders asrrayList of orders with a string id assigned to it
     */
    public GameStatus(MapEditor p_map, HashMap<HumanPlayer, Integer> p_player_army, ArrayList<HumanPlayer> p_players,
                      ArrayList<HashMap<HumanPlayer, String>> p_orders){
        d_map = p_map;
        d_player_army = p_player_army;
        d_players = p_players;
        d_orders = p_orders;
    }

    /**
     * update player status, update the status of the player by overwriting the current index of the player in the array
     * @param p_players the player we want to update
     */
    public void updatePlayer(ArrayList<HumanPlayer> p_players){
        d_players = p_players;
    }

    /**
     * update the number of armies for each player in the game
     * @param p_player_army list of updated players' armies
     */
    public void updateArmies(HashMap<HumanPlayer, Integer> p_player_army){
        d_player_army = p_player_army;
    }

    /**
     * update the orders of players
     * @param p_orders the updated orders list
     */
    public void updateOrders(ArrayList<HashMap<HumanPlayer, String>> p_orders){
        d_orders = p_orders;
    }

    /**
     * get the players array
     * @return the list of players
     */
    public ArrayList<HumanPlayer> getPlayers(){
        return d_players;
    }

    /**
     * get the orders for players
     * @return the list of orders for each player
     */
    public ArrayList<HashMap<HumanPlayer, String>> getOrders(){
        return d_orders;
    }

    /**
     * get the number of armies for each player
     * @return list of armies for players
     */
    public HashMap<HumanPlayer, Integer> getPlayerArmies(){
        return d_player_army;
    }

    /**
     * get the map of the game
     * @return Object of MapEditor
     */
    public MapEditor getMap(){
        return d_map;
    }
}
