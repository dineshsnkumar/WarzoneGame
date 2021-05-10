package ca.concordia.model;

import ca.concordia.view.LogPrintHandler;

import java.io.IOException;

/**
 * reinforces the number of armies inside the coutnry by 3 times.
 * @version 1.1
 */
public class Blockade implements Order{

    HumanPlayer d_player;
    Country d_country;
    LogEntryBuffer d_log = new LogEntryBuffer();
    LogPrintHandler d_logPrintHandler = new LogPrintHandler(d_log);
    /**

     /**
     * constructor of Blockade
     * @param p_player the player calling the card
     * @param p_country the country to triple the number of armies
     */
    public Blockade(HumanPlayer p_player, Country p_country){
        d_player = p_player;
        d_country = p_country;
    }

    /**
     * executes the order upon validation
     */
    @Override
    public void execute() {
        if (valid()) {
            printOrder();
            d_country.setNumberOfArmies(d_country.getNumberOfArmies() * 3);
            // log Negotiate execution
            try{
                d_logPrintHandler.LogFileWriter("Player "+ d_player.getPlayerName()+ " ordered Blockade execution with "+d_country.getNumberOfArmies());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * check the validity
     * if the country belongs to player
     * @return true if passes the above condition
     * @version 1.1
     * @author Parsa
     */
    @Override
    public boolean valid() {
        if(!d_player.getCountries().containsKey(d_country.getID())) {
            System.out.println("Country doesn't belong to Player");
            // log Blockade validation execution
            try{
                d_logPrintHandler.LogFileWriter("Player "+ d_player.getPlayerName()+ " does not have "+d_country.getName() +" and Blockade is invalid");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
        return true;
    }

    /**
     * prints the details for the order
     */
    @Override
    public void printOrder() {
        System.out.println("Blockade card called, thripling number of armies in "
                + d_country.getID());
    }
}
