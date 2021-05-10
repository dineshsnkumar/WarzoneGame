package ca.concordia.model;

import ca.concordia.view.LogPrintHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Represents the Benevolent Player
 *
 * @author Pouria
 * @version 1.0.2
 */

public class BenevolentPlayer extends PlayerStrategy {

    /**
     *
     * Represents object of Player, Countries, Map objects
     */
    Player d_player;
    List<Country> d_country;
    MapEditor d_map;

    LogEntryBuffer d_log = new LogEntryBuffer();
    LogPrintHandler d_logPrintHandler = new LogPrintHandler(d_log);


    /**
     * Constructor of the BenevolentPlayer
     *
     * @param p_player Player obj
     * @param p_country List of Countries
     * @param p_map MapEditor obj
     */
    public BenevolentPlayer(Player p_player, List<Country> p_country, MapEditor p_map) {
        super(p_player, p_country);
        // TODO Auto-generated constructor stub

        this.d_player = p_player;
        this.d_country = p_country;
        this.d_map = p_map;

    }

    /**
     * BenevolentPlayer does not attack, so it return null
     */
    @Override
    public Country toAttack() {
        return null;
    }

    /**
     * BenevolentPlayer does not attack, so it return null
     */

    @Override
    public Country toAttackFrom() {
        return null;
    }

    /**
     * BenevolentPlayer does not move, so it return null
     */

    @Override
    public Country toMoveFrom() {
        Country l_source = toDefend();
        if(l_source.getNumberOfArmies()==1 || l_source==null){
            return null;
        }
        for(Country neighbor: l_source.getNeighbors()){
            if(d_player.getOwnedCountries().containsKey(neighbor.getID().toLowerCase())){

                return neighbor;
            }
        }
        return null;
    }

    /***
     * method to implement the way the benevolent player should defend
     * @return l_maxArmies the number of maximum armies the player owns after defend
     */

    @Override
    public Country toDefend() {
        Country l_maxArmies = d_player.getOwnedCountries().get(0);

        for (Country p_country : d_country) {
            if (l_maxArmies.getNumberOfArmies() < p_country.getNumberOfArmies()
                    && d_player.getOwnedCountries().containsValue(p_country)) {
                l_maxArmies = p_country;
            }
        }
        return l_maxArmies;
    }

    /**
     * BenevolentPlayer can only use Deploy order.
     * @return null
     */
    @Override
    public Order createOrder() {

        // TODO: Check Deploy Condition and allowed.

        Random l_random = new Random();
        int l_randOrder = l_random.nextInt(3);
        if (l_random.nextInt(5) != 0) {
            switch (l_randOrder) {
                case (0):
                    return new Deploy(d_player, toDefend(), l_random.nextInt(20));
                case (1):
                    return new Advance(d_player, toMoveFrom(), toDefend(),
                            l_random.nextInt(toMoveFrom().getNumberOfArmies() + 5), d_map);


            }

            try {
                d_logPrintHandler.LogFileWriter("Player "+ d_player.getPlayerName()+ "created order of "+l_randOrder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
