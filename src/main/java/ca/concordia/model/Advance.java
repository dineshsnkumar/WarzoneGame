package ca.concordia.model;

import ca.concordia.view.LogPrintHandler;

import java.io.IOException;
import java.util.Map;

/**
 * move armies between 2 neighbor countries
 * @version 1.0.2
 * @author Parsa
 */
public class Advance implements Order {

	Country d_source;
	Country d_target;
	Player d_initiator;
	int d_num_to_advance;
	MapEditor d_map;
	LogEntryBuffer d_log = new LogEntryBuffer();
	LogPrintHandler d_logPrintHandler = new LogPrintHandler(d_log);


	/**
	 * Constructor for Advance Order class
	 * @param p_initiator the player issuing the advance order
	 * @param p_source the sourse country from armies are being moved
	 * @param p_target the target country from armies are moved to
	 * @param p_num the number of armies desired to mvoe
	 * @param p_map the loaded map of the game
	 */
	public Advance(Player p_initiator, Country p_source, Country p_target, int p_num, MapEditor p_map) {
		this.d_initiator = p_initiator;
		this.d_source = p_source;
		this.d_target = p_target;
		this.d_num_to_advance = p_num;
		this.d_map = p_map;
	}

	/**
	 * execute the order, move armies from source to target, reduce the number of armies in the source by d_num_to_advance,
	 * adds that amount to the armies in the target country
	 */
	@Override
	public void execute() {
		if (valid()) {
			printOrder();
			d_source.setNumberOfArmies(d_source.getNumberOfArmies() - d_num_to_advance);
			d_target.setNumberOfArmies(d_target.getNumberOfArmies() + d_num_to_advance);
		}
		try {
			d_logPrintHandler.LogFileWriter("Player "+ d_initiator.getPlayerName()+ " advanced from " +d_source.getName()+" with "+d_source.getNumberOfArmies()+" to "+d_target.getName()+" with "+d_target.getNumberOfArmies());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * checks if the order is valid.
	 * checks if the source or target belong to the player
	 * checks if the countries are neighbors
	 * checks if the number of armies desired to move actually exist
	 * @return true if all the above conditions are true
	 */
	@Override
	public boolean valid() {
		if(!d_initiator.getCountries().containsKey(d_source.getID()) ||
				!d_initiator.getCountries().containsKey(d_target.getID())){
			System.out.println("Source or Destination country do not belong to Player" +d_initiator.getPlayerName());

			try {
				d_logPrintHandler.LogFileWriter("Player "+ d_source.getName()+" could not advance");
			} catch (IOException e) {
				e.printStackTrace();
			}

			return false;
		}
		if(!d_map.getCountries().get(d_source.getID()).getNeighbors().contains(d_target)){
			System.out.println("Countries are not neighbors");

			try {
				d_logPrintHandler.LogFileWriter("Player "+ d_source.getName()+" could not advance");
			} catch (IOException e) {
				e.printStackTrace();
			}

			return false;
		}
		if(d_source.getNumberOfArmies() < d_num_to_advance){
			System.out.println("Not enough reinforcements to move");

			try {
				d_logPrintHandler.LogFileWriter("Player "+ d_source.getName()+" could not advance");
			} catch (IOException e) {
				e.printStackTrace();
			}

			return false;
		}
		return true;
	}

	/**
	 * prints the order details
	 */
	@Override
	public void printOrder() {
		System.out.println("Moving "+d_num_to_advance+" armies from "+
				d_source.getID()+ " to "+d_target.getID()+".");
	}

}
