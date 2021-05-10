package ca.concordia.model;

import java.util.List;

/**
 * Strategy of the Strategy pattern
 * @author Pouria
 *
 */
public abstract class PlayerStrategy {

    /**
     * The strategy uses some data to make decisions.
     * Embeded them in the Strategy object.
     */
    List<Country> d_country;
    Player d_player;

    /**
     * Pass the required data to the Startegy upon creation
     * @param p_player player obj
     * @param p_country list of Country objects
     */
    PlayerStrategy(Player p_player, List<Country> p_country) {
        d_player = p_player;
        d_country = p_country;

    }

    /**
     *  method that will be called bt the Player to do some
     *  actions that depand on the adopted ConcreteStrategy.
     */

    public abstract Order createOrder();

    /**
     * local methods that the Strategy will use internally
     * to do some actions specifically to the ConcreteStrategy.
     *
     * @return abstracted country
     */
    public abstract Country toAttack();

    /**
     * abstract method
     * @return abstracted country
     */
    public abstract Country toAttackFrom();

    /**
     * abstract method
     * @return abstracted country
     */
    public abstract Country toMoveFrom();

    /**
     * abstract method
     * @return abstracted country
     */
    public abstract Country toDefend();

}
