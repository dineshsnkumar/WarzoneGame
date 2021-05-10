package ca.concordia.model;

import ca.concordia.controller.GameController;


/**
 * Abstract Class for Edit phase 
 *
 * @author dinesh
 *
 */
public abstract class Edit extends Phase {
	/**
	 * Edit
	 * @param p_ge GameController obj
	 */
	Edit(GameController p_ge) {
		super(p_ge);
	}

	/**
	 * Sets Players
	 */
	public void setPlayers() {
		printInvalidCommandMessage();
	}

	/**
	 * Assigns countries
	 */
	public void assignCountries() {
		printInvalidCommandMessage();
	}

	/**
	 * Assign reinforcements
	 */
	public void reinforce() {
		printInvalidCommandMessage();
	}

	/**
	 * Issue Orders
	 */
	public void issueOrder() {
		printInvalidCommandMessage();
	}

	/**
	 * Execute Orders
	 */
	public void executeOrder() {
		printInvalidCommandMessage();
	}

	/**
	 * End game
	 */
	public void endGame() {
		printInvalidCommandMessage();
	}

	/**
	 * tournamentMode
	 */
	public void tournamentMode() {
		printInvalidCommandMessage();
	}

	/**
	 * Show's map
	 */
	public void showMap() {
		printInvalidCommandMessage();
	}
}
