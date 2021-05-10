package ca.concordia.model;

import ca.concordia.controller.GameController;
import ca.concordia.controller.GameEngine;
import ca.concordia.controller.StartUpController;

/**
 *Reinforcement class to initiate some objects and print invalid msg command for some other methods
 */
public class Reinforcement extends MainPlay {
	
	StartUpController d_startUpController;
	GameEngine d_gameEngine;

	/**
	 * Constructor
	 * 
	 * @param p_ge Game controller obj
	 */
	Reinforcement(GameController p_ge) {
		super(p_ge);
	}

	/**
	 * Reinforcements
	 * @param p_ge Gameengine obj
	 * @param p_sc StartUpController obj
	 * @param p_gc GameController obj
	 */
	public Reinforcement(GameController p_gc, StartUpController p_sc, GameEngine p_ge) {
		super(p_gc);
		d_startUpController= p_sc;
		d_gameEngine = p_ge;
	}
	
	/**
	 * Reinforcements
	 */
	public void reinforce() {
			d_gameEngine.armyAssign();
		next();
 	}
	
	/**
	 * Issue Order
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
	 * Tournament() overridden
	 */
	@Override
	public void tournamentMode() {

	}
	/**
	 * Transition
	 */
	public void next() {
		ge.setPhase(new IssueOrder(ge, d_gameEngine, d_startUpController));
	}

	/**
	 * saveGame Overridden
	 */
	@Override
	public void saveGame() {

	}
}
