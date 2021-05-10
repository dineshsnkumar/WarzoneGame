package ca.concordia.model;

import ca.concordia.controller.GameController;

/**
 * Abstract method for play
 * 
 * @author dinesh
 *
 */
public abstract class Play extends Phase {
	
	
	/**
	 * Default Constructor
	 * 
	 * @param p_ge GameController obj
	 */
	Play(GameController p_ge) {
		super(p_ge);
	}

	/**
	 * Shows Map TODO
	 */
	public void showMap() {
		System.out.println("map is being displayed");
	}

	/**
	 * editCountry
	 */
	public void editCountry() {
		printInvalidCommandMessage();
	}

	/**
	 * editContinent
	 */
	public void editContinent() {
		printInvalidCommandMessage();

	}

	/**
	 * editNeighbor
	 */
	public void editNeighbor() {
		printInvalidCommandMessage();
	}

	/**
	 * saveMap
	 */
	public void saveMap() {
		printInvalidCommandMessage();
	}

	/**
	 * validateMap
	 */
	public void validateMap() {
		printInvalidCommandMessage();
	}

	/**
	 * Endgame
	 */
	public void endGame() {
		//ge.setPhase(new End(ge, ));
		printInvalidCommandMessage();
	}
	
	/**
	 * Edit
	 */
	public void edit() {
		printInvalidCommandMessage();
	}
	

}
