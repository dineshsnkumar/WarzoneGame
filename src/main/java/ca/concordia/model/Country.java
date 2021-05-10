package ca.concordia.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



/**
 * Class Country : a class to store the attributes, methods, local variables for each Country in the game. containing
 * methods such as addNeighbor, removeNeighbors, and getters.
 *
 * @author Parsa
 * @version 1.0.1
 */
public class Country {
	String d_id;
	public Continent d_continent;
	String d_name;
	List<Country> d_neighbors;
	private Player ownerPlayer;

	/**
	 * Number of armies in this country.
	 */
	private int d_number;

	/**
	 * Constructor for class Country. optional attribute for Country is Name.
	 * @param p_id	unique identifier for each country
	 * @param p_continent Unique identifier the continent each country belongs to
	 * @param p_name optional attribute name of each country
	 */
	public Country(String p_id, Continent p_continent, String p_name) {
		d_id = p_id;
		d_continent = p_continent;
		d_neighbors = new ArrayList<Country>();
		d_name = p_name;
		d_number = 0;
		this.ownerPlayer = null;
	}

	/**
	 * overriding the Country constructor without the optional attribute, name;
	 * @param p_id unique identifier for each country
	 * @param p_continent unique identifier the continent each country belongs to
	 */
	public Country(String p_id, Continent p_continent) {
		d_id = p_id;
		d_continent = p_continent;
		d_neighbors = new ArrayList<Country>();
	}

	/**
	 * add neighbor to the country. the validation of the data is checked prior calling this method.
	 * @param p_country the identifier of the neighbor country.
	 */
	public void AddNeighbors(Country p_country) {
		if(!d_neighbors.contains(p_country)) {
			d_neighbors.add(p_country);
		}
	}

	/**
	 * remove neighbor to the country. the validation of the data is checked prior calling this method.
	 * @param p_country the identifier of the neighbor country.
	 */
	public boolean RemoveNeighbor(Country p_country) {
		if(d_neighbors.contains(p_country)){
			d_neighbors.remove(p_country);
			return true;}
		return false;
	}

	/**
	 * return the ID of the country
	 * @return ID of the country as String
	 */
	public String getID(){
		return d_id;
	}

	/**
	 * return the name of the country
	 * @return name of the country as String
	 */
	public String getName(){
		return d_name;
	}

	/**
	 * Getter method to get number of armies in the country.
	 * @return returns number of armies in the player
	 */
	public int getNumberOfArmies() {
		return this.d_number;
	}

	/**
	 * Set number of armies in the country
	 * @param p_number number of armies to be set in the country
	 */
	public void setNumberOfArmies(int p_number) {
		this.d_number = p_number;
	}

	/**
	 * Getter for list of neighbors
	 * @return List of countries
	 */
	public List<Country> getNeighbors() {
		return d_neighbors;
	}

	/**
	 * Getter for Continent
	 * @return Continent
	 */
	public Continent getContinent() {
		return d_continent;
	}

	/**
	 * Gets the player owning the country currently.
	 * @return Player owning this country.
	 */
	public Player getOwnerPlayer() {
		return ownerPlayer;
	}

	/**
	 * Sets the player owning this country currently
	 * @param ownerPlayer Player owning this country.
	 */
	public void setOwnerPlayer(Player ownerPlayer) {
		this.ownerPlayer = ownerPlayer;
	}
}