package ca.concordia.model;

import ca.concordia.controller.*;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * LoadGame class
 */
public class LoadGame extends Edit{
    MapEditor d_mapEditor;
    GameEngine d_gameEngine;

    /**
     * LoadGame constructor
     *
     * @param p_ge GameController obj
     */
    public LoadGame(GameController p_ge) {
        super(p_ge);
    }
    /**
     * LoadGame constructor
     * @param p_mapEditor object of the current map editor
     * @param p_ge GameController obj
     */
    public LoadGame(MapEditor p_mapEditor, GameController p_ge){
        super(p_ge);


    }
    /**
     * Load Map
     */
    public void loadMap(){
        System.out.println("Please enter map file name: (e.g haiti)");
        Scanner l_scan = new Scanner(System.in);
        String l_entry = l_scan.nextLine();
        LoadMapController d_load;
        MapReader d_reader = new MapReader();
//        Adapter d_reader = new Adapter(l_entry);
        //new imp using MapReader
        try {
            d_mapEditor = d_reader.loadMap(l_entry);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

//		try {
//			d_load = new LoadMapController(l_entry);
//			d_mapEditor = d_load.getMap();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
        ge.setPhase(new PostLoad(ge, d_mapEditor, d_gameEngine));
    }

    /**
     * Edit phase handles countries/continent/Neighbor
     */
    public void edit() {
        printInvalidCommandMessage();
    }

    /**
     * Edit country
     */
    public void editCountry() {
        printInvalidCommandMessage();
    }
    /**
     * Edit continent
     */
    public void editContinent() {
        printInvalidCommandMessage();
    }

    /**
     * Saves the map
     */
    public void saveMap() {
        printInvalidCommandMessage();
    }

    /**
     * Edits neighbors
     */
    public void editNeighbor() {
        printInvalidCommandMessage();
    }

    /**
     * validates map
     */
    public void validateMap() {
        printInvalidCommandMessage();
    }

    /**
     * Transition to next phase
     */
    public void next() {
        System.out.println("must load map");
    }

    /**
     * print invalid msgs command for saveGame()
     */
    @Override
	public void saveGame() {
		printInvalidCommandMessage();
	}

}
