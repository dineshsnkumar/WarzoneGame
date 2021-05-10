package ca.concordia.model;
import ca.concordia.view.LogPrintHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

/**
 * attack from source country to destination country
 * @version 1.1
 * @author Parsa
 */
public class Attack implements Order{
    HumanPlayer d_attacker;
    HumanPlayer d_defender;
    Country d_src;
    Country d_target;
    int d_numArmies;
    MapEditor d_map;
    LogEntryBuffer d_log = new LogEntryBuffer();
    LogPrintHandler d_logPrintHandler = new LogPrintHandler(d_log);


    /**
     * the constructor for the Attack class
     * @param p_attacker the player who attacks
     * @param p_defender the player who defends
     * @param p_src the source country which attacks initiates from
     * @param p_target the target country
     * @param p_numArmies the number of armies to send from source country to target for battle
     * @param p_map the loaded map of the game
     */
    public Attack(HumanPlayer p_attacker, HumanPlayer p_defender, Country p_src, Country p_target, int p_numArmies, MapEditor p_map){
        d_attacker = p_attacker;
        d_defender = (HumanPlayer) p_target.getOwnerPlayer();
        d_src = p_src;
        d_target = p_target;
        d_numArmies = p_numArmies;
        d_map = p_map;
    }

    /**
     * executes the attack method upon validation
     * it's a one on one battle between soldiers in an army.
     * if the attacker soldier gets higher than 60% change and the defender gets lower than 70%, attacker wins.
     * if the attacker soldier gets less than 60% change and the defender gets lower than 70%, its a tie.
     * if the attacker soldier gets higher than 60% change and the defender gets higher than 70%, defender wins
     * if the attacker soldier gets higher than 60% change and the defender gets higher than 70%, defender wins
     * the winner of the battle gets random Orders card for special orders
     */
    @Override
    public void execute() {
        if(!valid())
            return;
        printOrder();
        float l_attackProb;
        float l_defendProb;

        Random rand = new Random();

        int l_attackerLeft = d_numArmies;
        int l_defenderLeft = d_target.getNumberOfArmies();

        while(l_attackerLeft!=0 || l_defenderLeft!=0){
            l_attackProb = rand.nextFloat();
            l_defendProb = rand.nextFloat();
            if(l_attackerLeft ==0 || l_defenderLeft == 0)
                break;
            if(l_attackProb>=0.6 && l_defendProb<0.7){
                l_defenderLeft--;
                System.out.println("attacker killed one defender soldier");
                //log attack order execution
                try {
                    d_logPrintHandler.LogFileWriter("Player "+ d_attacker.getPlayerName()+ " attacks "+ d_defender.getPlayerName()+" with "+l_attackProb+" and a defender is killed");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if(l_attackProb>=0.6 && l_defendProb>=0.7){
                l_attackerLeft--;
                System.out.println("defender killed one soldier");
                try {
                    d_logPrintHandler.LogFileWriter("Player "+ d_attacker.getPlayerName()+ " attacks "+ d_defender.getPlayerName()+" with "+l_attackProb+ " and an attacker soldier is killed");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if(l_attackProb<0.6 && l_defendProb >=0.7){
                l_attackerLeft--;
                System.out.println("defender killed one soldier");
                try {
                    d_logPrintHandler.LogFileWriter("Player "+ d_attacker.getPlayerName()+ " attacks "+ d_defender.getPlayerName()+" with "+l_attackProb+ " and an attacker soldier is killed");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                System.out.println("it's a tie");
                try {
                    d_logPrintHandler.LogFileWriter("Player "+ d_attacker.getPlayerName()+ " attacks "+ d_defender.getPlayerName()+" with "+l_attackProb+ " and the result is tie");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }

        //TODO edit
        if(l_defenderLeft == 0){

            System.out.println("Attacker conquered "+ d_target.getID()+".");
            d_target.setOwnerPlayer(d_attacker);
            d_target.setNumberOfArmies(l_attackerLeft);
            d_src.setNumberOfArmies(d_src.getNumberOfArmies()-l_attackerLeft);
            d_attacker.putCountry(d_target.getID(), d_target);
            HashMap<String, Country> l_tmpCountries = d_defender.getCountries();
            l_tmpCountries.remove(d_target.getID());
            d_defender.setOwnedCountries(l_tmpCountries);
            int pick = new Random().nextInt(Orders.values().length);
            d_attacker.setOwnedCards(Orders.values()[pick]);

            for(Continent cntn: d_map.getContinents().values()){
                boolean l_check = true;
                for(Country cntry: cntn.getCountriesList()){
                    if(cntry.getOwnerPlayer()!=d_attacker)
                        l_check = false;
                }
                if(l_check && !d_attacker.getOwnedContinents().values().contains(cntn)){
                    d_attacker.setOwnedArmies(d_attacker.getOwnedArmies()+cntn.getContinentValue());
                    HashMap<String, Continent> tmp = d_attacker.getOwnedContinents();
                    tmp.put(cntn.getContinentID(), cntn);
                    d_attacker.setOwnedContinents(tmp);
                }
            }
            try {
                d_logPrintHandler.LogFileWriter("Attacker "+ d_attacker.getPlayerName()+ "won and conquered "+d_target.getName()+".");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("Defender won");
            d_target.setNumberOfArmies(l_defenderLeft);
            d_src.setNumberOfArmies(d_src.getNumberOfArmies()-d_numArmies);
            int pick = new Random().nextInt(Orders.values().length);
            d_defender.setOwnedCards(Orders.values()[pick]);
            try {
                d_logPrintHandler.LogFileWriter("Defender "+ d_attacker.getPlayerName()+ "won and keeps "+d_target.getName()+".");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * check the validy of the map.
     * if the uponents has the diplomacy card you shall not attack them.
     * source country shouldn't be owned by the country
     * number of armies in the battle are limited to the already existing armies in the source country
     * @return true if the above conditions are satisfied
     */
    @Override
    public boolean valid() {
        if(d_defender.getOwnedCards().contains(Orders.NEGOTIATE))
        {
            System.out.println("Target country plays Diplomacy card");
            d_defender.removeOwnedCards(Orders.NEGOTIATE);
            return false;
        }
        if(!d_attacker.getCountries().containsKey(d_src.getID())){
            System.out.println("Source Country not owned by player.");
            return false;
        }else if (d_attacker.getCountries().get(d_src.getID()).getNumberOfArmies()<d_numArmies){
            System.out.println("Player can't move more armies than existing number of armies.");
            return false;
        }else if(!d_map.getCountries().get(d_src.getID()).getNeighbors().contains(d_target)){
            System.out.println("Countries are not neighbors.");
            //log invalid attack
            try {
                d_logPrintHandler.LogFileWriter("Player "+ d_attacker.getPlayerName()+ " could not attack "+ d_defender.getPlayerName()+" as they were not neighbors");
            } catch (IOException e) {
                e.printStackTrace();
            }

            return false;
        }

        return true;
    }

    /**
     * prints the details in the orders
     */
    @Override
    public void printOrder() {
        System.out.println("Order Attack, Player "+d_attacker.getPlayerName()+" attacks " +
                d_defender.getPlayerName()+ " from " + d_src.getID()+ " to " + d_target.getID()+
                "with "+d_numArmies+ " number of armies.");
    }
}
