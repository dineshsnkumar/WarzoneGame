package ca.concordia.model;

import java.util.Scanner;

import ca.concordia.controller.GameController;
import ca.concordia.controller.GameEngine;
import ca.concordia.view.ShowMapView;

/**
 * PostLoad phase class which edits and does some other actions during the postLoad phase
 */
public class PostLoad extends Edit {

	MapEditor d_mapEditor;
	ShowMapView d_showMapView;

	/**
	 * Constructor
	 * @param p_ge GameController obj
	 * @param p_mapEditor MapEditor obj
	 * @param p_gameEngine GameEngine obj
	 */
	PostLoad(GameController p_ge, MapEditor p_mapEditor, GameEngine p_gameEngine) {
		super(p_ge);
		d_showMapView = new ShowMapView();
		d_mapEditor = p_mapEditor;

	}
	
	/**
	 * Construtor
	 * @param p_ge GameController obj
	 */
	PostLoad(GameController p_ge) {
		super(p_ge);
	}

	/**
	 * Shows the map
	 */
	public void showMap() {
		d_mapEditor.showmap();
	}


	/**
	 * Loads the map
	 */
	public void loadMap() {
		System.out.println("map has been loaded");
	}

	/**
	 * Edits country/ continent/neighbor
	 */
	public void edit() {

		System.out.println(
				"follow the structure below\n" + "editcontinent -add continentID continentvalue -remove continentID\n"
						+ "editcountry -add countryID continentID -remove countryID\n"
						+ "editneighbor -add countryID neighborcountryID -remove countryID neighborcountryID");
		Scanner d_scan = new Scanner(System.in);

		while (true) {
			String d_editCommand = d_scan.nextLine();
			if (d_editCommand.startsWith("editcontinent")) {
				d_mapEditor.editContinent(d_editCommand);
				System.out.println("editing continent");
			} else if (d_editCommand.startsWith("editcountry")) {

				d_mapEditor.editCountry(d_editCommand);
				System.out.println("editing country");
			} else if (d_editCommand.startsWith("editneighbor")) {
				d_mapEditor.editNeighbor(d_editCommand);
				System.out.println("editing neighbors");
			} else if (d_editCommand.equals("save")) {
				if (d_mapEditor.validateMap()) {
					d_mapEditor.savemap(d_mapEditor.getFileName());
					System.out.println("saving map");
					break;
				} else {
					System.out.println("map not valid, check connections and come back later!");
					d_mapEditor.showmap();
					break;
				}
			} else {
				System.out.println("wrong command. Please follow the command structure\n"
						+ "editcontinent -add continentID continentvalue -remove continentID\n"
						+ "editcountry -add countryID continentID -remove countryID\n"
						+ "editneighbor -add countryID neighborcountryID -remove countryID neighborcountryID");
			}
		}
		System.out.println("country has been edited");
	}

	/**
	 * Edit Continent
	 */
	public void editContinent() {
		System.out.println("Continent has been edited");

	}

	/**
	 * Edit Country
	 */
	public void editCountry() {

	}

	/**
	 * Edit neighbour
	 */
	public void editNeighbor() {
		System.out.println("Edit neighbour has been edited");
	}

	/**
	 * Validate Map
	 */
	public void validateMap() {
	boolean flag = d_mapEditor.validateMap();
	if(!flag){
		ge.setPhase(new Preload(d_mapEditor, ge));
	}
	}

	/**
	 * Show Map
	 */
	public void saveMap() {
		boolean flag = d_mapEditor.validateMap();
		if(flag)
		{	d_mapEditor.savemap(d_mapEditor.getFileName());
			ge.setPhase(new PlaySetup(ge, d_mapEditor));}
		else{
			ge.setPhase(new Preload(d_mapEditor, ge));
		}
	}

	/**
	 * Next
	 */
	public void next() {
		System.out.println("must save map");
	}

	/**
	 * saveGame
	 */
	@Override
	public void saveGame() {
		printInvalidCommandMessage();
	}



}
