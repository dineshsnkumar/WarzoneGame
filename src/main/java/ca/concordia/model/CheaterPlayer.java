package ca.concordia.model;

import ca.concordia.view.LogPrintHandler;

import java.io.IOException;
import java.util.List;
import java.util.Random;



/**
 * Represents the Cheater Player
 * @author Pouria
 * @version 1.1
 */

public class CheaterPlayer extends PlayerStrategy{

    /**
     *
     * Represents object of Player, Countries, Random, MapEditor objects
     */
    Player d_player;
    Player d_enemy;
    List<Country> d_country;
    MapEditor d_map;
    Random d_rand = new Random();

    LogEntryBuffer d_log = new LogEntryBuffer();
    LogPrintHandler d_logPrintHandler = new LogPrintHandler(d_log);


    /**
     * Constructor of the Cheater Player
     * @param p_player Player obj
     * @param p_country List of countries
     * @param p_map mapEditor obj
     */
    public CheaterPlayer(Player p_player, List<Country> p_country, MapEditor p_map) {
        super(p_player, p_country);
        this.d_player = p_player;
        this.d_country = p_country;
        this.d_map = p_map;
    }

    /**
     * Conquers all the immediate neighboring enemy countries and then doubles the number of armies on its countries that have enemy neighbors.
     */
    @Override
    public Order createOrder() {

        for(Country country : d_country){
            for(Country neighbour : country.getNeighbors()){
                if(!d_player.getOwnedCountries().containsKey(neighbour.getID())){
                    Player defendingPlayer = neighbour.getOwnerPlayer();
                    d_player.getOwnedCountries().put(neighbour.getID(),neighbour);
                    defendingPlayer.getOwnedCountries().remove(neighbour.getID());
                    neighbour.setOwnerPlayer(d_player);

                }
            }
        }

        //Multiply*2 armies
        for(Country country : d_player.getOwnedCountries().values()){
            for(Country neighbour : country.getNeighbors()){
                if(!(d_player.getOwnedCountries().containsKey(neighbour.getID()))){
                    country.setNumberOfArmies(country.getNumberOfArmies() * 2);
                }
            }
        }
        //adding log writer
        try {
            d_logPrintHandler.LogFileWriter("Cheater Player created an order");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Cheater Player does not attack, so it return null
     */
    @Override
    public Country toAttack() {
        return null;
    }

    /**
     * Cheater Player does not attack from, so it return null
     */

    @Override
    public Country toAttackFrom() {
        return null;
    }

    /**
     * Cheater Player does not move, so it return null
     */
    @Override
    public Country toMoveFrom() {
        return null;
    }

    /**
     * Cheater Player does not defend, so it return null
     */
    @Override
    public Country toDefend() {
        return null;
    }

}
