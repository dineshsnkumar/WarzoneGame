package ca.concordia.model;

import ca.concordia.controller.GameController;

/**
 *	State of the State pattern.
 *
 *		Phase 
 *        Edit phase (abstract)
 *          Preload
 *          Postload
 *        Play (abstract)
 *          GameSetup
 *          GamePlay 
 *          Reinforcements	
 *          Issue Order  <-----	| 
 *          OrderExecution ----	|
 *        End
 *
 */
public abstract class Phase {

	GameController ge;

	/**
	 * constructor
	 * @param p_ge gameController objto be initiated
	 */
	Phase(GameController p_ge) {
		ge = p_ge;
	}

	// common commands

	/**
	 * abstract loadMap
	 */
	abstract public void loadMap();

	/**
	 * abstract showMap
	 */
	abstract public void showMap();

	// Edit map commands

	/**
	 * abstract edit
	 */
	abstract public void edit();

	/**
	 * abstract editCountry
	 */
	abstract public void editCountry();

	/**
	 * abstract saveMap
	 */
	abstract public void saveMap();

	/**
	 * abstract editContinent
	 */
	abstract public void editContinent();

	/**
	 * abstract editNeighbor
	 */
	abstract public void editNeighbor();

	/**
	 * abstract validateMap
	 */
	abstract public void validateMap();


	// Play commands
	// game setup commands

	/**
	 * abstract setPlayers
	 */
	abstract public void setPlayers();

	/**
	 * abstract assignCountries
	 */
	abstract public void assignCountries();

	// reinforcement commands

	/**
	 * abstract reinforce
	 */
	abstract public void reinforce();

	// attack commands

	/**
	 * abstract issueOrder
	 */
	abstract public void issueOrder();

	// fortify commands

	/**
	 * abstract executeOrder
	 */
	abstract public void executeOrder();

	// end command

	/**
	 * abstract endGame
	 */
	abstract public void endGame();

	// game play mode command

	/**
	 * abstract tournament
	 */
	abstract public void tournamentMode();

	// go to next phase

	/**
	 * abstract next
	 */
	abstract public void next();

	/**
	 * abstract saveGame
	 */
	abstract public void saveGame();
	/**
	 *  Common method to all States. 
	 */
	public void printInvalidCommandMessage() {
		System.out.println("Invalid command in state " + this.getClass().getSimpleName() );
	}
}
