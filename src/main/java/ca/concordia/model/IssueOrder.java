package ca.concordia.model;

import ca.concordia.controller.GameController;
import ca.concordia.controller.GameEngine;
import ca.concordia.controller.StartUpController;


/**
 * Deals with the Issue Order phase
 */

public class IssueOrder extends MainPlay {
	GameEngine d_gameEngine;
	StartUpController d_startup;

	/**
	 * Initiates some parameter from other classes for issuing and order constructor
	 * @param p_ge Game Controller obj
	 * @param p_gameEngine gameEngine obj
	 * @param p_startup StartUpController
	 */
	IssueOrder(GameController p_ge, GameEngine p_gameEngine, StartUpController p_startup) {
		super(p_ge);
		d_gameEngine = p_gameEngine;
		d_startup = p_startup;
	}

	/**
	 *  Call this method to go the the next state in the sequence. 
	 */
	public void next() {
		ge.setPhase(new OrderExecution(ge, d_gameEngine, d_startup));
	}

	/**
	 * Constructor
	 */
	@Override
	public void saveGame() {

	}


	/**
	 * reinforce
	 */
	public void reinforce() {
		printInvalidCommandMessage(); 
	}

	/**
	 * issueOrder
	 */
	public void issueOrder() {
		d_gameEngine.takeOrders();
		ge.setPhase(new OrderExecution(ge, d_gameEngine, d_startup));
	}

	/**
	 * executeOrder
	 */
	public void executeOrder() {
		printInvalidCommandMessage(); 
	}

	/**
	 * Tournament
	 */
	@Override
	public void tournamentMode() {

	}
}
