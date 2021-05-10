package ca.concordia.model;

import ca.concordia.controller.GameController;
import ca.concordia.controller.GameEngine;
import ca.concordia.controller.StartUpController;

/**
 * Class deals with the endphase
 * @author dinesh
 *
 */
public class End extends MainPlay {
	GameEngine d_gameEngine;
	StartUpController d_startUp;
	HumanPlayer d_winner;
	/**
	 * Constructor
	 *
	 * @param p_ge GameController obj
	 */
	End(GameController p_ge, GameEngine p_gameEngine, StartUpController p_startup) {
		super(p_ge);
		d_gameEngine = p_gameEngine;
		d_startUp = p_startup;
	}

	/**
	 * Loads map
	 */
	public void loadMap() {
		printInvalidCommandMessage();
	}

	/**
	 * Invalid command
	 */
	public void showMap() {
		printInvalidCommandMessage();
	}


	/**
	 * Invalid command
	 */
	public void editCountry() {
		printInvalidCommandMessage();
	}

	/**
	 * Invalid command
	 */
	public void editContinent() {
		printInvalidCommandMessage();
	}

	/**
	 * Invalid command
	 */
	public void editNeighbor() {
		printInvalidCommandMessage();
	}
	/**
	 * Invalid command
	 */
	public void validateMap() {
		printInvalidCommandMessage();
	}

	/**
	 * Invalid command
	 */
	public void saveMap() {
		printInvalidCommandMessage();
	}

	/**
	 * Invalid command
	 */
	public void setPlayers() {
		printInvalidCommandMessage();
	}

	/**
	 * Invalid command
	 */
	public void assignCountries() {
		printInvalidCommandMessage();
	}

	/**
	 * Invalid command
	 */
	public void reinforce() {
		printInvalidCommandMessage();
	}

	/**
	 * Invalid command
	 */
	public void issueOrder() {
		printInvalidCommandMessage();
	}

	/**
	 * Invalid command
	 */
	public void executeOrder() {
		printInvalidCommandMessage();
	}

	/**
	 * Invalid command
	 */
	public void endGame() {

		boolean finish = d_gameEngine.finish();
		if(finish){
			System.out.println("game finished!");
			d_winner = d_gameEngine.get_Winner();
			ge.setPhase(new SaveGame(ge));
		}else{
			ge.setPhase(new Reinforcement(ge,d_startUp, d_gameEngine));
		}
	}

	/**
	 * Invalid command
	 */
	public void tournamentMode() {
		printInvalidCommandMessage();
	}

	/**
	 * Invalid command
	 */
	public void next() {
//		ge.setPhase(new Reinforcement(ge,d_startUp, d_gameEngine));
		ge.setPhase(new SaveGame(ge));
	}

	/**
	 * save game method overridden
	 */
	@Override
	public void saveGame() {

	}

	/**
	 * edit method overridden
	 */
	@Override
	public void edit() {
		printInvalidCommandMessage();
	}

	/**
	 * return the winner of the game
	 * @return object of the winner;
	 */
	public HumanPlayer getWinner(){
		return d_winner;
	}
}
