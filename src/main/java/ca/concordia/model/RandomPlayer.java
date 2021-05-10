package ca.concordia.model;

import java.util.List;
import java.util.Random;
/**
 * Represents the RandomPlayer
 * @author Pouria
 */

public class RandomPlayer extends PlayerStrategy {

    /**
     *
     * Represents object of Player, Countries, Random, MapEditor objects
     */
    Player d_player;
    List<Country> d_country;
    MapEditor d_map;
    Random d_rand = new Random();

    /**
     * Constructor of the RandomPlayer
     * @param p_player Player obj
     * @param p_country List of Country objs
     * @param p_map MapEditor Obj
     */
    public RandomPlayer(Player p_player, List<Country> p_country, MapEditor p_map) {
        super(p_player, p_country);
        this.d_player = p_player;
        this.d_country = p_country;
        this.d_map = p_map;
    }


    /**
     * Random Country to move to
     */
    @Override
    public Country toAttack() {
        return (d_country.get(d_rand.nextInt(d_country.size()-1)));
    }

    /**
     * Random of its owned countries to attack from
     */
    @Override
    public Country toAttackFrom() {
        return toDefend();
    }

    /**
     * Random of its owned countries to move from
     */
    @Override
    public Country toMoveFrom() {
        return toDefend();
    }

    /**
     * Random of its owned countries to defend
     */
    @Override
    public Country toDefend() {
        return (d_player.getOwnedCountries().get(d_rand.nextInt(d_player.getOwnedCountries().size()-1)));
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
                    return new Deploy(d_player, toDefend(), d_rand.nextInt(20));
                case (1):
                    return new Advance(d_player, toMoveFrom(), toAttack(), d_rand.nextInt(toMoveFrom().getNumberOfArmies() +5), d_map);
                case (2):
                    return new Attack((HumanPlayer) d_player, (HumanPlayer) toAttack().getOwnerPlayer(), toAttackFrom(),toAttack(), d_rand.nextInt(toMoveFrom().getNumberOfArmies() +5), d_map);
            }
        }
        return null;
    }
}
