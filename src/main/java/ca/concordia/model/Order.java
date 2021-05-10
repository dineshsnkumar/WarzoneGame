package ca.concordia.model;

/**
 * Maintains the current order of the player.
 */
public interface Order {
	/**
	 * execute() in order interface
	 */
	public void execute();
	/**
	 * valid() in order interface
	 */
	public boolean valid();
	/**
	 * printOrder() in order interface
	 */
	public void printOrder();
	
}
