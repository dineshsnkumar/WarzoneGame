package ca.concordia.model;

import ca.concordia.view.LogPrintHandler;

import java.io.IOException;

/**
 * Airlift order, move armies between the territories of a single player which are not(or can be) neighbors.
 * @version 1.1
 * @author Parsa
 *
 */
public class Airlift implements Order{
    HumanPlayer d_player;
    Country d_src;
    Country d_dest;
    int d_numArmies;
    LogEntryBuffer d_log = new LogEntryBuffer();
    LogPrintHandler d_logPrintHandler = new LogPrintHandler(d_log);


    /**
     * constructor for then Airlift order
     * @param p_player the player
     * @param p_src the source country
     * @param p_dest the destination country
     * @param p_numArmies number of armies desired to move
     */
    public Airlift(HumanPlayer p_player, Country p_src, Country p_dest, int p_numArmies){
        d_player = p_player;
        d_src = p_src;
        d_dest = p_dest;
        d_numArmies = p_numArmies;
    }

    /**
     * executes the AirLift order upon validation
     */
    @Override
    public void execute() {
        if (valid()) {
            printOrder();
            d_src.setNumberOfArmies(d_src.getNumberOfArmies() - d_numArmies);
            d_dest.setNumberOfArmies(d_dest.getNumberOfArmies() + d_numArmies);

            // log Airlift execution
            try{
                d_logPrintHandler.LogFileWriter("Player "+ d_player.getPlayerName()+ " ordered Airlift execution, from "+d_src.getName()+" with "+ d_src.getNumberOfArmies() +" number of armies to "+ d_dest.getName()+" with "+d_dest.getNumberOfArmies());
            }
            catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * valides the order.
     * checks if the source country and the destination country belongs player
     * @return true if the condition is statisfied
     */
    @Override
    public boolean valid() {
        if(!d_player.getCountries().containsKey(d_src.getID()) ||
                !d_player.getCountries().containsKey(d_dest.getID())){
            System.out.println("Source or Destination country do not " +
                    "belong to Player" +d_player.getPlayerName());
            return false;
        }
        // log Airlift execution
        try{
            d_logPrintHandler.LogFileWriter("Player "+ d_player.getPlayerName()+ " ordered invalid airlift ");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * prints the details of the order
     */
    @Override
    public void printOrder() {
        System.out.println("Airlift card called, moving" +
                " reinforcement from "+ d_src.getID()+" to "+d_dest.getID() +".");
    }
}
