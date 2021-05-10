package ca.concordia.model;

import ca.concordia.view.LogPrintHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.Integer.parseInt;

/**
 * This model has information regarding the continent
 * @author dinesh, Parsa
 * @version 1.0.1
 * @since 1.0.0
 */
public class Continent {

	private String d_continentID;
	private String d_continentName;
	private HashMap<String, Country> d_countries;
	private int d_continentValue;

	LogEntryBuffer d_log = new LogEntryBuffer();
	LogPrintHandler d_logPrintHandler = new LogPrintHandler(d_log);

	/**
	 * Constructor to class Continent
	 * @param p_continentID unique ID of continent
	 * @param p_continentValue value of each continent
	 */
	public Continent(String p_continentID,  int p_continentValue){
		d_continentID = p_continentID;
		d_continentValue = p_continentValue;
		d_countries = new HashMap<String, Country>();
	}

	/**
	 * Getter for continent
	 * @return the continentID of the continent
	 */
	public String getContinentID() {
		return d_continentID;
	}

	/**
	 * Setter for continent's ID
	 * @param p_continentID the contintneID
	 */
	public void setContinentID(String p_continentID) {
		d_continentID = p_continentID;
	}

	/**
	 * Getter for continent name
	 * @return the continent name
	 */
	public String getContinentName() {
		return d_continentName;
	}

	/**
	 * Setter for d_continentName
	 * @param p_continentName the continent name
	 */
	public void setContinentName(String p_continentName) {
		d_continentName = p_continentName;
	}

	/**
	 * The getter for continent and the countries it belongs to
	 * @return the continent name and the list
	 */
	public HashMap<String, Country> getCountries() {
		return d_countries;
	}

	/**
	 * The getter for list of countries
	 * @return the continent name and the list
	 */
	public List<Country> getCountriesList(){
		return new ArrayList<Country>(d_countries.values());
	}

	/**
	 * The setter for continent and the countries it belongs to
	 * @param p_countries HashMap to setup all the countries inside the continent.
	 */
	public void setCountries(HashMap<String, Country> p_countries) {
		d_countries = p_countries;
	}

	/**
	 * removes a specific county in the continent.
	 * @param p_country : the ID of country to be removed
	 * @return true if the program ends on successful termination.
	 */
	public boolean removeCountry(Country p_country){
		if(d_countries.containsKey(p_country.getID())){
			d_countries.remove(p_country.getID());

			try {
				d_logPrintHandler.LogFileWriter("Player "+ p_country.getName()+" was removed successfully.");
			} catch (IOException e) {
				e.printStackTrace();
			}

			return true;
		}
		System.out.println("Error occurred. Check again.");
		return false;
	}

	/**
	 * called from MapEditor to add countries in the continent
	 * @param p_countryID the string ID of the country
	 * @param p_country the object Country
	 */
	public void addCountry(String p_countryID, Country p_country) {
		d_countries.put(p_countryID, p_country);
	}

	/**
	 * set the name of the continent
	 * @param p_name String name of the continent
	 */
	public void setName(String p_name){
		d_continentName = p_name;
	}


	/**
	 * getter for Value of the Object Country
	 * @return return the value of each Continent
	 */
	public int getContinentValue() {
		return d_continentValue;
	}

}