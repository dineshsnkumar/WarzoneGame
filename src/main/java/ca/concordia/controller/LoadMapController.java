package ca.concordia.controller;

import ca.concordia.model.LogEntryBuffer;
import ca.concordia.model.MapEditor;
import ca.concordia.view.LogPrintHandler;
import ca.concordia.view.ShowMapView;

import java.io.*;

/**
 * This class controls the input for loading the map
 */
public class  LoadMapController{
    static String d_path;
    MapEditor d_map;
    ShowMapView d_show;
    

    /**
     * public constructor of LoadMapController when opening a new map. creates a new
     * object of MapEditor
     * @param p_path to file to open
     */
    public LoadMapController(String p_path) throws IOException {
        File d_file = new File("resources/" + p_path + ".map");
        if(!d_file.exists()){
            System.out.println("new Map Created. Please add Continents, Countries, Neighbors. Just makes sure you follow the right order.");
        }
        d_map = new MapEditor(p_path);
        d_path = p_path;
        d_show = new ShowMapView();
    }

    /**
     * loads map with the path given in constructor
     * @throws FileNotFoundException
     */
    public void loadMap() throws FileNotFoundException {
        d_map.loadMap(d_path);
    }

    /**
     * Controller for calling edit continents on MapEditor
     * @param p_args continent to edit
     */
    public void editContinents(String p_args) {
        d_map.editContinent(p_args);
    }

    /**
     * controller for calling edit countries on MapEditor
     * @param p_args countries to edit
     */
    public void editCountries(String p_args) {
        d_map.editCountry(p_args);
    }

    /**
     * controller for calling on Map editor
     * @param p_args neighbours to edit
     */
    public void editNeighbors(String p_args) {
        d_map.editNeighbor(p_args);
    }

    /**
     * getter for the MapEditor Objects
     *
     * @return MapEditor object
     */
    public MapEditor getMap() {
        return d_map;
    }

    /**
     * call saveMap in MapEditor with the given name
     * @param p_path the name to the map to be saved with
     * @return true if the map is validated to true
     */
    public boolean saveMap(String p_path) {
        return d_map.savemap(p_path);
    }

    /**
     * call validateMap on the MapEditor objects
     * @return boolean upon validating the map.
     */
    public boolean validateMap() {
        return d_map.validateMap();
    }

    /**
     * Displays the continent and the countries.
     * call the view Shoemap to show the map
     */
    public void showmap() {
        d_show.showMapEditor(d_map.getContinents(), d_map.getNeighbors());
    }
}
