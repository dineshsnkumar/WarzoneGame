package ca.concordia.controller;

import ca.concordia.model.Continent;
import ca.concordia.model.Country;
import ca.concordia.model.MapEditor;

import java.io.*;
import java.util.HashMap;

/**
 * this class is used to create an object of class MapEditor and return it.
 * the map used for this method is the map format same as the Warzone game map format.
 */
public class MapReader {
    /**
     * Loading the map based on the address and path provided
     *
     * @param p_path path to the map file in the format of Warzone game.
     */
    public MapEditor loadMap(String p_path) throws FileNotFoundException {
        File d_file = new File("resources/" + p_path + ".map");
        BufferedReader l_reader = new BufferedReader(new FileReader(d_file));

        MapEditor l_map = new MapEditor();
        int i;
        String[] inputs;
        Country l_country;
        Continent l_continent;

        try {
            String l_line = l_reader.readLine();
            while (l_line != null) {
                if (l_line.toLowerCase().contains("[continents]")) {
                    i = 0;
                    l_line = l_reader.readLine();
                    while (l_line != null) {
                        if (l_line.toLowerCase().contains("countries") || l_line.toLowerCase().contains("borders")
                                || l_line.toLowerCase().contains("files"))
                            break;
                        if (!l_line.toLowerCase().contains(";")) {
                            //creating new continent, addin object to map
                            inputs = l_line.split(" ");
                            if (inputs.length >= 2) {
//								Continentcounter++;
                                l_map.addContinent(inputs[0], inputs[1]);
//								d_continentsID.put(i, inputs[0]);
//								d_continentsIDNumber.put(i, inputs[0]);
                                i++;
                            }
                        }
                        l_line = l_reader.readLine();
                    }
                }

                if (l_line.toLowerCase().contains("[countries]")) {
                    i = 0;
                    l_line = l_reader.readLine();
                    while (l_line != null) {
                        if (l_line.toLowerCase().contains("continents") || l_line.toLowerCase().contains("borders")
                                || l_line.toLowerCase().contains("files"))
                            break;
                        if (!l_line.toLowerCase().contains(";")) {
                            //creating new country adding to map
                            inputs = l_line.split(" ");
                            if (inputs.length >= 3) {
                                if (l_map.getContinents().get(l_map.get_continentID().get(Integer.parseInt(inputs[2]) + 1)) != null) {
                                    l_map.addCountry(inputs[1], l_map.get_continentID().get(Integer.parseInt(inputs[2]) + 1));
                                    //d_countriesID.put(Integer.parseInt(inputs[0]), inputs[1]);
                                }
                            }
                        }
                        l_line = l_reader.readLine();
                    }
                }

                if (l_line.toLowerCase().contains("[borders]")) {
                    l_line = l_reader.readLine();
                    while (l_line != null) {
                        if (l_line.toLowerCase().contains("continents") || l_line.toLowerCase().contains("countries")
                                || l_line.toLowerCase().contains("files"))
                            break;
                        if (!l_line.toLowerCase().contains(";")) {
                            inputs = l_line.split(" ");
                            //adding neighbors
                            if (inputs.length > 1) {
                                if (l_map.get_countriesID().containsKey(Integer.parseInt(inputs[0]))) {
                                    for (int n = 1; n < inputs.length; n++) {
                                        l_map.addNeighbor(l_map.get_countriesID().get(Integer.parseInt(inputs[0])),
                                                l_map.get_countriesID().get(Integer.parseInt(inputs[n])));
                                    }
                                }
                            }
                        }
                        l_line = l_reader.readLine();
                    }
                }
                l_line = l_reader.readLine();
            }
            l_reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return l_map;
    }
}
