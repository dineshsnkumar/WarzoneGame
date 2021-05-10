package ca.concordia.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import ca.concordia.model.ConquestMap;
import ca.concordia.model.MapEditor;

/**
 * MapReader controller for conquest map format.
 */
public class ConquestMapReader {
	/**
	 * load map from path to a file of format conquest
	 * 
	 * @param p_path path to file
	 * @return l_map object of conquest map
	 * @throws FileNotFoundException 
	 */
	public MapEditor loadMap(String p_path) throws FileNotFoundException {

		// making objects of MapEditor, File and BufferedReader
		MapEditor l_map = new MapEditor();
        File d_file = new File("resources/" + p_path + ".map");
		BufferedReader l_p_reader = new BufferedReader(new FileReader(d_file));
		
		
		try {
			String l_line = l_p_reader.readLine();
			while (l_line != null) {

				// Add Continents
				if (l_line.equals("[Continents]")) {
					l_line = l_p_reader.readLine();
					while (!l_line.equals("")) {
						String[] l_continentString = l_line.split("=");
						l_map.addContinent(l_continentString[0], l_continentString[1]);
						l_line = l_p_reader.readLine();
					}
				}
				
				// Add Countries
	
				if (l_line.equals("[Territories]")) {
					l_line = l_p_reader.readLine();
					while (l_line != null) {
						if (l_line.equals("")) {
							l_line = l_p_reader.readLine();
							continue;
						}

						String[] l_countryString = l_line.split(",");
						l_map.addCountry(l_countryString[0], l_countryString[3]);
						
						// Add Neighbours
						for (int i = 4; i < l_countryString.length; i++) {
							l_map.addNeighbor(l_countryString[0], l_countryString[i]);

						}
						l_line = l_p_reader.readLine();
					}
				}
				
				l_line = l_p_reader.readLine();

			}
			
			l_p_reader.close();

		} catch (FileNotFoundException e) {
			System.out.println("Conquest File Not Found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error Reading Conquest File");
			e.printStackTrace();
		}
		return l_map;
	}

}
