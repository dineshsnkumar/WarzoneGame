package ca.concordia.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Represents a typical player.
 * @version 1.1
 * @author Pouria
 * @author Parsa
 */
public abstract class Player {

	/**
	 * Name of the player
	 */
	private String d_name;

	/**
	 * Number of armies owned by this player.
	 */
	private int d_armies;

	/**
	 * Stores countries owned by the player. HashMap has 1) Key: name of the country
	 * in lower case 2) Value: corresponding Country object
	 */
	private HashMap<String, Country> d_countries;

	/**
	 * Stores continents owned by the player. HashMap has 1) Key: name of the
	 * continent in lower case 2) Value: corresponding Continent object
	 */
	private HashMap<String, Continent> d_continents;

	/**
	 * Stores cards owned by the player.
	 */
	private ArrayList<Orders> d_cards;

	/**
	 * Stores order owned by the player.
	 */
	private ArrayList<Order> d_orders;
	ArrayList<Orders> d_orderType;

	HashMap<Country, Integer> d_army_country;
	/**
	 * Creates a player with the argument player name and sets default value for
	 * rest of the player fields.
	 *
	 * @param p_name name of player
	 */
	public Player(String p_name) {

		this.d_name = p_name;
		this.d_armies = 0;
		this.d_countries = new HashMap<String, Country>();
		this.d_continents = new HashMap<String, Continent>();
		this.d_cards = new ArrayList<Orders>();
		this.d_orders = new ArrayList<Order>();
		this.d_orderType = new ArrayList<Orders>();
	}

	/**
	 * This function allow player to place armies
	 * @param p_country Reinforce armies here
	 * @param p_num Reinforce this many armies
	 * @return true if successful, else false
	 */
	public abstract boolean reinforce(String p_country, int p_num);

	/**
	 * Getter method to return player name entered by user
	 *
	 * @return d_name
	 */
	public String getPlayerName() {
//		System.out.println(this.d_name);
		return this.d_name;
	}

	/**
	 * Method to set player name
	 *
	 * @param p_name Name of the player
	 */
	public void setPlayerName(String p_name) {
		this.d_name = p_name;
	}

	/**
	 * This function gets the number of initial armies
	 *
	 * @return d_armies
	 */
	public int getOwnedArmies() {
		return d_armies;
	}

	/**
	 * This function sets the number of initial armies
	 *
	 * @param p_armies number of armies owned by players
	 */
	public void setOwnedArmies(int p_armies) {

		this.d_armies = p_armies;

	}

	/**
	 * This method returns the countries owned by players
	 *
	 * @return d_countries
	 */
	public HashMap<String, Country> getOwnedCountries() {
		return this.d_countries;
	}

	/**
	 * This method sets the countries owned by players as a Hash map
	 *
	 * @param p_countries number of countries owned by players
	 */
	public void setOwnedCountries(HashMap<String, Country> p_countries) {
		this.d_countries = p_countries;
	}

	/**
	 * put countries with parameters like id and the country obj name
	 * @param p_id string name of the country or id
	 * @param p_country country obj
	 */
	public void putCountry(String p_id,Country p_country){this.d_countries.put(p_id,p_country);}



	/**
	 * This method returns the continents owned by the player
	 *
	 * @return d_countries owned by the player
	 */
	public HashMap<String, Continent> getOwnedContinents() {
		return d_continents;
	}

	/**
	 * This method sets the continents owned by the player
	 *
	 * @param p_continents Current set of continents owned by the player.
	 */
	public void setOwnedContinents(HashMap<String, Continent> p_continents) {
		this.d_continents = p_continents;
	}

	/**
	 * This method returns the cards owned by player.
	 *
	 * @return d_cards number of cards owned by player
	 */
	public ArrayList<Orders> getOwnedCards() {
		return this.d_cards;
	}

	/**
	 * This method adds the card earned by the player.
	 *
	 * @param p_card object of Card
	 */
	public void setOwnedCards(Orders p_card) {
		this.d_cards.add(p_card);

	}

	/**
	 * This method removes the card from owned cards after trade in process.
	 *
	 * @param p_card object of Card
	 */
	public void removeOwnedCards(Orders p_card) {
		this.d_cards.remove(p_card);
	}

	/**
	 * check to see if the player has the input card
	 * @param p_card the card to check
	 * @return boolean
	 */
	public boolean hasCard(Orders p_card){
		return d_cards.contains(p_card);
	}
	/**
	 * Function to add an order to the list of orders held by the player when the
	 * game engine calls it during the issue orders phase.
	 *
	 * @return none
	 */
	public void issue_order(Order p_order, Orders p_type) {
		d_orders.add(p_order);
		d_orderType.add(p_type);
	}

	/**
	 * Method that is called by the GameEngine during the execute orders phase and
	 * returns the first order in the player’s list of orders, then removes it from
	 * the list.
	 *
	 * @return l_result the first order object in the player’s list of orders.
	 */
	public Order next_order() {
		if(d_orders.size() == 0){
			return null;
		}

		Order l_result = this.d_orders.get(0);
		d_orders.remove(0);
		return l_result;

	}

	/**
	 * returns the first order in the queue of orders and deletes it
	 * @return order in front of the queue
	 */
	public Orders next_type(){
		if(d_orderType.size() == 0){
			return null;
		}

		Orders l_result = this.d_orderType.get(0);
		d_orderType.remove(0);
		return l_result;
	}

	/**
	 *
	 * @return a Hashmap of the countries owned by the player
	 */
	public HashMap<String, Country> getCountries(){
		return d_countries;
	}
}