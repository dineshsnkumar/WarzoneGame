package ca.concordia.controller;

import java.io.FileNotFoundException;

import ca.concordia.model.MapEditor;

/**
 * This is the Adapter class to translate the conquest map to WarZone map
 * format.
 */
public class Adapter extends MapReader{
    ConquestMapReader d_conquestMapReader;

    /**
     * Constructor to initiate ConcouestMapReader
     * @param p_conMap ConquestMap obj
     */
    public Adapter(ConquestMapReader p_conMap){
        d_conquestMapReader = p_conMap;
    }

    /**
     * method that loads the loaded map after implementing conqust map reader
     * @param p_path path to the map file in the format of Warzone game.
     * @return loaded map effected by conquest map
     * @throws FileNotFoundException
     */
    @Override
    public MapEditor loadMap(String p_path) throws FileNotFoundException {
        return ((d_conquestMapReader.loadMap(p_path)));
    }

    
}
