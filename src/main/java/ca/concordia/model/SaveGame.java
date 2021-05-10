package ca.concordia.model;

import ca.concordia.controller.GameController;
import ca.concordia.controller.GameEngine;
import ca.concordia.controller.GameStatusController;
import ca.concordia.controller.StartUpController;
import ca.concordia.view.ShowGameStatus;

/**
 * SaveGame class inherits Phase and initiates objects for saveGame
 */
public class SaveGame extends Phase {

	GameStatusController d_gameStatusController;
	StartUpController d_startUpController;
	GameEngine d_gameEngine;
	ShowGameStatus d_showGameStatus;
	GameController d_gc;

	/**
	 * Constructor
	 * @param p_ge GameController obj
	 */
	public SaveGame(GameController p_ge) {
		super(p_ge);
	}

	/**
	 * Constructor
	 * @param p_ge GameController obj
	 * @param d_startup startUpController obj
	 * @param d_gameEngine GameEngine obj
	 */
	public SaveGame(GameController p_ge, StartUpController d_startup, GameEngine d_gameEngine) {
		super(p_ge);
		d_gameStatusController = new GameStatusController();
		d_gc= new GameController();
	}

	/**
	 * Load Map
	 */
	@Override
	public void loadMap() {
		printInvalidCommandMessage();
	}

	/**
	 * Save Map
	 */
	public void showMap() {
		printInvalidCommandMessage();

	}

	/**
	 * Edit Game
	 */
	public void edit() {
		printInvalidCommandMessage();

	}

	/**
	 * Edit Country
	 */
	public void editCountry() {
		printInvalidCommandMessage();

	}

	/**
	 * Save Map
	 */
	public void saveMap() {
		printInvalidCommandMessage();

	}

	/**
	 * Edit Continent
	 */
	public void editContinent() {
		printInvalidCommandMessage();
	}

	/**
	 * Edit Neighbour
	 */
	public void editNeighbor() {
		printInvalidCommandMessage();
	}

	/**
	 * Validate Map
	 */
	public void validateMap() {
		printInvalidCommandMessage();
	}

	/**
	 * Save Game
	 */
	public void saveGame() {
		GameStatus d_gateStatus = d_gameStatusController.getD_status();
		d_showGameStatus = new ShowGameStatus(d_gateStatus);
		d_showGameStatus.saveStatus();
		ge.setPhase(new LoadGame(d_gc));

	}

	/**
	 * Set Players
	 */
	public void setPlayers() {
		printInvalidCommandMessage();

	}

	/**
	 * Assign Countries
	 */
	public void assignCountries() {
		printInvalidCommandMessage();

	}

	/**
	 * Assign Reinforce
	 */
	public void reinforce() {
		printInvalidCommandMessage();

	}

	/**
	 * Issue Order
	 */
	public void issueOrder() {
		printInvalidCommandMessage();

	}

	/**
	 * Execute Order
	 */
	public void executeOrder() {
		printInvalidCommandMessage();

	}

	/**
	 * End Game
	 */
	public void endGame() {
		printInvalidCommandMessage();

	}

	/**
	 * Tournament mode
	 */
	@Override
	public void tournamentMode() {

	}

	/**
	 * Next
	 */
	public void next() {
		printInvalidCommandMessage();

	}

}
