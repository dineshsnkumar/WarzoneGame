package ca.concordia.model;

import ca.concordia.view.ShowMapView;
import ca.concordia.model.LogEntryBuffer;
import ca.concordia.view.LogPrintHandler;

import java.io.IOException;

/**
 * place armies in the destination teritory
 * @version 1.1
 * @author Parsa
 */
public class Deploy implements Order {
	Country d_target;
	int d_numArmies;
	Player d_player;
	int d_allowedArmies;
	LogEntryBuffer d_log = new LogEntryBuffer();
	LogPrintHandler d_logPrintHandler = new LogPrintHandler(d_log);

	/**
	 * constructor of the class
	 * @param p_player the player called deploy
	 * @param p_country the country to deploy armies
	 * @param p_reinforcement the number of armies to deploy in the country
	 * @param p_allowed the allowed number of armies given at the reinforcement phase
	 */
	public Deploy(Player p_player, Country p_country, int p_reinforcement, int p_allowed) {
		d_player = p_player;
		d_target = p_country;
		d_numArmies = p_reinforcement;
		d_allowedArmies = p_allowed;
	}

	/**
	 * constructor of the class
	 * @param p_player the player called deploy
	 * @param p_country the country to deploy armies
	 * @param p_reinforcement the number of armies to deploy in the country
	 */
	public Deploy(Player p_player, Country p_country, int p_reinforcement) {
		d_player = p_player;
		d_target = p_country;
		d_numArmies = p_reinforcement;
	}

	/**
	 * place armies to destination country upon validation of order
	 */
	@Override
	public void execute() {
		if(valid()) {
			printOrder();
			d_target.setNumberOfArmies(d_target.getNumberOfArmies() + d_numArmies);
		}
		try {
			d_logPrintHandler.LogFileWriter("Player "+ d_player.getPlayerName()+ " executed order Deploy on Country "+d_target+
					" with "+d_numArmies+ " number of armies.");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * checks if the order is valid.
	 * checks if the country is owned by the player
	 * @return true if the above condition is passed.
	 */
	@Override
	public boolean valid() {
		if(!d_player.getCountries().containsKey(d_target.getID())) {
			System.out.println("Country doesn't belong to Player");
			return false;}
		if(d_numArmies>d_allowedArmies){
			System.out.println("Can't place more armies than you have.");
			return false;
		}
		return true;
	}

	/**
	 * prints the detilas of the order
	 */
	@Override
	public void printOrder() {
		System.out.println("Deploy order issued by player "+ d_player);
		System.out.println("Deploy " + this.d_numArmies + " to " + this.d_target.getID());
	}
}