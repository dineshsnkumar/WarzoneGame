package ca.concordia.view;

import java.util.HashMap;
import java.util.List;

import ca.concordia.model.Continent;
import ca.concordia.model.Country;
import ca.concordia.model.MapEditor;

/**
 * This class is responsible for displaying the continents, countries and their
 * respective neighbors
 *
 * @author dinesh, Parsa
 * @version 1.0.1
 * @since 1.0.0
 * @see ca.concordia.model.MapEditor
 */
public class ShowMapView {

    /**
     * This method takes the continents and checks the countries it has and prints
     * its neighbors
     *
     * @param p_continents list of continents
     * @param p_neighbors list of country neighbors
     * @return void
     */
    public void showMapEditor(HashMap<String, Continent> p_continents, HashMap<Country, List<Country>> p_neighbors) {

        if (p_continents.values().size() > 0) {
            for (Continent l_continent : p_continents.values()) {
                System.out.println(l_continent.getContinentID());
                System.out.print("|");
                p_neighbors.forEach((k, v) -> {
                    if (l_continent.getCountries().containsKey(k.getID())) {
                        System.out.print("|---------------->" + k.getID() + " (# armies: " + k.getNumberOfArmies());
                        if(k.getOwnerPlayer()!=null)
                            System.out.println(" Owner: "+k.getOwnerPlayer().getPlayerName()+")");
                        else
                            System.out.println(")");
                        v.forEach(n -> System.out.println("|\t\t\t\t\t\t\t|------------------->" + n.getID()));
                    }
                });
            }

        } else {

            System.out.println("The map doesn't have any continents");
        }
    }
}
