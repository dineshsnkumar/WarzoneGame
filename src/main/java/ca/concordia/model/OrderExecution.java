package ca.concordia.model;

import ca.concordia.controller.GameController;
import ca.concordia.controller.GameEngine;
import ca.concordia.controller.StartUpController;

/**
 * This class inherits MAinPlay class to override methods like Tournament, initiate some objects in its Constructor and to call
 * 		printInvalidCommandMessage method for some methods such as issuing an order
 */
public class OrderExecution extends MainPlay {
	GameEngine d_gameEngine;
	StartUpController d_startup;

	/**
	 * Initiates GameController, GameEngine and StartupController objects
	 * @param p_ge GameController obj
	 * @param p_gameEngine GameEngine obj
	 * @param p_startup startupcontroller obj
	 */
	OrderExecution(GameController p_ge, GameEngine p_gameEngine, StartUpController p_startup) {
		super(p_ge);
		d_gameEngine = p_gameEngine;
		d_startup = p_startup;
	}

	/**
	 * calling invalid msgs to be printe for reinforce()
	 */
	public void reinforce() {
		printInvalidCommandMessage(); 
	}
	/**
	 * calling invalid msgs to be printe for issueOrder()
	 */
	public void issueOrder() {
		printInvalidCommandMessage(); 
	}
	/**
	 * calling invalid msgs to be printe for executeOrder()
	 */
	public void executeOrder() {
		d_gameEngine.executeAllOrders();
		//ge.setPhase(new Reinforcement(ge,d_startup, d_gameEngine));
		ge.setPhase(new End(ge, d_gameEngine, d_startup));
	}
	/**
	 * TournamentMode () overridden
	 */
	@Override
	public void tournamentMode() {

	}

	/**
	 * making SaveGame object with its attributes to be put in setPhase method called in next()
	 */
	public void next() {
		//ge.setPhase(new SaveGame(ge, d_startup,d_gameEngine));
		ge.setPhase(new End(ge, d_gameEngine, d_startup));
	}

	/**
	 * saveGame Overridden
	 */
	@Override
	public void saveGame() {

	}

}
