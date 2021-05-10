package ca.concordia.model;

import ca.concordia.view.LogPrintHandler;

import java.io.IOException;
import java.util.Scanner;

/**
 * Creates a player with the argument player name and sets default value for rest of the player fields.
 *
 *
 * @author Pouria
 *
 */
public class HumanPlayer extends Player{
	LogEntryBuffer d_log = new LogEntryBuffer();
	LogPrintHandler d_logPrintHandler = new LogPrintHandler(d_log);
	final int MAXIMUMPOOL = 5;
	Scanner scan = new Scanner(System.in);
	/**
	 * Constructor
	 * @param p_name inherits this parameter from Player Class
	 */
	public HumanPlayer(String p_name) {
		super(p_name);
	}
	/***
	 * This is an Overridden function from Player Class.
	 * For reinforcement phase, this method checks conditions for applicability of reinforcement for a player by different requirements such as
	 * checking the number of armies, and the maximum number allowed based on the maximum pool reinforcement number which is considered 5.
	 *
	 * @param p_country the chosen country for reinforcement
	 * @param p_num the number of armies
	 * @return true if the reinforcement is allowed based on the conditions and false if reinforcement cannot be done.
	 */
	@Override
	public boolean reinforce(String p_country, int p_num) {

		if(p_num<0){

			System.out.println("You cannot reinforce with negative number of armies.");
		}
		//checking if the chosen country is existed
		if(getOwnedCountries().containsKey(p_country))
		{	//check if the the number of armies is in the correct range
			if(MAXIMUMPOOL >= p_num)
			{	//assign the number of armies related to the chosen country to existing armies variable to the use it for setting the number of remained armies
				Country d_c = getOwnedCountries().get(p_country);
				int l_existingArmies = d_c.getNumberOfArmies();
				l_existingArmies += p_num;
				d_c.setNumberOfArmies(l_existingArmies);
				this.setOwnedArmies(getOwnedArmies()-p_num);
				System.out.println(p_num + " armies reinforced at " + p_country + ". Remaining reinforcement armies: " + getOwnedArmies() + "\n");

				try {
					d_logPrintHandler.LogFileWriter("Country"+p_country+" reinforced "+ p_num+" armies.");
				} catch (IOException e) {
					e.printStackTrace();
				}

				// this condition results in adding more reinforcement based on the user choice (yes or no)
				if(getOwnedArmies()==0) {
					System.out.println("Dou you want to add more armies to your countries?\n"
							+ "0. Yes\n"
							+ "1. No\n");

					try {
						int l_option = scan.nextInt();
						switch (l_option) {
							case 0: reinforce(p_country, getOwnedArmies());
								break;

							case 1 :
								break;
							default:
								System.out.println("Invalid Option! Try again");
								break;
						} }catch (Exception e) {

					}

				}
				return true;
			}
			else
			{
				System.out.println(getPlayerName() + " doesn't have " + p_num + " armies to reinforce. Invalid command.");

				try {
					d_logPrintHandler.LogFileWriter("Country"+p_country+ " could not reinforce.");
				} catch (IOException e) {
					e.printStackTrace();
				}

				return false;
			}
		}
		else
		{
			System.out.println(p_country + " not owned by " + getPlayerName() +". Invalid command.\n");
			return false;
		}
	}

	/**
	 * reinforcement method
	 * @param p_country object of country
	 * @param p_armyCount number of count Army
	 */
	public void reinforment(Country p_country, int p_armyCount){
		d_army_country.put(p_country, p_armyCount);
	}
}
