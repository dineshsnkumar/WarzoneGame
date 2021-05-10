package ca.concordia.model;

/**
 * prevent the first attack on the player if the defender has the Negotiation/Diplomacy card
 * @version 1.1
 * @author Parsa
 */
public class Negotiate implements Order{
    HumanPlayer d_player1;

    /**
     * constructor
     * @param p_player1 owner of the Negotiation card
     */
    public Negotiate(HumanPlayer p_player1){
        d_player1 = p_player1;
    }

    /**
     * there is nothing to execute
     */
    @Override
    public void execute() {

    }

    /**
     * there is no conditions for having the Negotiation order
     * @return true for if valid
     */
    @Override
    public boolean valid() {
        return true;
    }

    /**
     * print the details of the order
     */
    @Override
    public void printOrder() {

    }
}
