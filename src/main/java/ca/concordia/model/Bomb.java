package ca.concordia.model;

import ca.concordia.view.LogPrintHandler;

import java.io.IOException;
import java.util.List;

/**
 * order to halve the number of armies of the opponents' neighbor territories
 * @version 1.1
 * @author Parsa
 */
public class Bomb implements Order{
    HumanPlayer d_player;
    Country d_country;
    MapEditor d_map;
    LogEntryBuffer d_log = new LogEntryBuffer();
    LogPrintHandler d_logPrintHandler = new LogPrintHandler(d_log);
    /**

     /**
     * constructor to the Bomb order class
     * @param p_player the player issuing the Bomb order
     * @param p_country the country where the bomb is originated in
     * @param p_map the loaded map of the game
     */
    public Bomb(HumanPlayer p_player, Country p_country, MapEditor p_map){
        d_player = p_player;
        d_country = p_country;
        d_map = p_map;
    }

    /**
     * executes the Bomb order it's valid.
     * halves the number of armies in all opponents' territories
     */
    @Override
    public void execute() {
        if(valid()){
            printOrder();
            List<Country> l_neighbors = d_map.getNeighbors().get(d_country);
            for(Country country: l_neighbors){
                if(country.getOwnerPlayer()!=d_country.getOwnerPlayer())
                    country.setNumberOfArmies(country.getNumberOfArmies()/2);

                //log bomb execution
                try {
                    d_logPrintHandler.LogFileWriter("Player "+ d_player.getPlayerName()+ " ordered Bomb execution for " +country.getName() );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * checks if the order is valid.
     * checks if the source country is owned by the player
     * @return true if the above checks are passed
     */
    @Override
    public boolean valid() {
        if(!d_player.getCountries().containsKey(d_country.getID())) {
            System.out.println("Country doesn't belong to Player");


            //log bomb execution
            try {
                d_logPrintHandler.LogFileWriter("Player "+ d_player.getPlayerName()+ " could not order Bomb execution because country does not belong to the player ");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
        return true;
    }

    /**
     * prints the order details
     */
    @Override
    public void printOrder() {
        System.out.println("bombing neighbors of " + d_country.getID()+".");
    }
}
