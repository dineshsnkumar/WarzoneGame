package ca.concordia.model;

import ca.concordia.view.LogPrintHandler;

import java.io.IOException;
import java.util.List;
import java.util.Random;



/**
 * Represents the Aggressive Player
 * @author Pouria
 * @version 1.0.2
 */

public class AggressivePlayer extends PlayerStrategy{

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
     * Constructor of the Aggressive Player
     * @param p_player Player obj
     * @param p_country list of countries
     * @param p_map Mapeditor obj
     */
    public AggressivePlayer(Player p_player, List<Country> p_country, MapEditor p_map) {
        super(p_player, p_country);
        this.d_player = p_player;
        this.d_country = p_country;
        this.d_map = p_map;
    }

    /**
     * Aggressive Country to attack to
     */
    @Override
    public Country toAttack() {

        Country l_destination = findStrongestCountry();
        return canAttack(l_destination);
    }

    /**
     * Random of its owned strongest countries from
     */
    @Override
    public Country toAttackFrom() {
        return findStrongestCountry();
    }

    /**
     * Random of its owned countries to move from
     */
    @Override
    public Country toMoveFrom() {
        Country l_source = findStrongestCountry();
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

    /**
     * Random of its owned countries to defend
     */
    @Override
    public Country toDefend() {
        return null;
    }

    /**
     * The random player can either determine deploy or advance, determined randomly
     */
    @Override
    public Order createOrder() {

        int l_randOrder = d_rand.nextInt(3);
        int l_numofArmines;
        if(d_rand.nextInt(5) != 0) {
            switch(l_randOrder) {
                case (0):
                    return new Deploy(d_player, toAttackFrom(), d_rand.nextInt(20));
                case (1):
                    return new Advance(d_player, toMoveFrom(), toAttack(), d_rand.nextInt(toMoveFrom().getNumberOfArmies() +5), d_map);
                case (2):
                    return new Attack((HumanPlayer) d_player, (HumanPlayer) findEnemytoAttack(), toAttackFrom(),toAttack(), d_rand.nextInt(toMoveFrom().getNumberOfArmies() +5), d_map);
            }
        }


        try {
            d_logPrintHandler.LogFileWriter("Player aggressive created an order of "+ l_randOrder);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Checks whether attack with argument country is possible or not.
     * @param p_attackingCountry Country making the attack.
     * @return country to attack
     */
    public Country canAttack(Country p_attackingCountry){
        if(p_attackingCountry.getNumberOfArmies()==1 || p_attackingCountry==null){
            return null;
        }
        for(Country p_neighbor: p_attackingCountry.getNeighbors()){
            if(!d_player.getOwnedCountries().containsKey(p_neighbor.getID().toLowerCase())){

                return p_neighbor;
            }
        }
        return null;
    }

    /**
     * Finds strongest country to reinforce/attack.
     * @return Strongest country owned by the player.
     */
    public Country findStrongestCountry(){
        int l_maximumArmies = 0;
        int l_noNeighborMaximumArmies = 0;

        Country l_countryToReinforce = null;
        Country l_noNeighborCountryToReinforce = null;
        for(Country p_country : d_player.getOwnedCountries().values()){
            if(p_country.getNumberOfArmies()>l_maximumArmies){
                for(Country p_neighbor : p_country.getNeighbors()){
                    if(!d_player.getOwnedCountries().containsKey(p_neighbor.getID().toLowerCase())){
                        l_maximumArmies = p_country.getNumberOfArmies();
                        l_countryToReinforce = p_country;
                        break;
                    }
                }
            }
            if(p_country.getNumberOfArmies()>l_noNeighborMaximumArmies){
                l_noNeighborMaximumArmies = p_country.getNumberOfArmies();
                l_noNeighborCountryToReinforce = p_country;
            }
        }
        if(l_maximumArmies > 0){
            return l_countryToReinforce;
        } else {
            return l_noNeighborCountryToReinforce;
        }
    }

    /**
     * Finding Enemy to attack from the strongest country.
     * @return d_enemy
     */
    public Player findEnemytoAttack() {
        Country l_country = canAttack(findStrongestCountry());
        if(l_country != null) {
            d_enemy = l_country.getOwnerPlayer();
            return d_enemy;
        }
        return null;
    }

}
