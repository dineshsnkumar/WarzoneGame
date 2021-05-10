package ca.concordia.model;

import java.io.*;
import java.util.*;

import ca.concordia.view.LogPrintHandler;
import org.jgrapht.*;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;

import ca.concordia.view.ShowMapView;

/**
 * This is the main Map editor class, everything would be handled here. The
 * methods include adding/remoivng countires, d_continents and neighbors to
 * countries.
 *
 * @author Parsa
 * @version 1.1.0
 * @since 1.0.1
 */
public class MapEditor {
	File d_map;
	String d_mapFile;
	String d_fileName;
	static int Continentcounter = 0;
	HashMap<Integer, String> d_continentsID = new HashMap<Integer, String>();
	HashMap<Integer, String> d_continentsIDNumber = new HashMap<Integer, String>();
	HashMap<String, Continent> d_continents = new HashMap<String, Continent>();
	static int Countrycounter = 0;
	HashMap<String, Country> d_countries = new HashMap<String, Country>();
	HashMap<Integer, String> d_countriesID = new HashMap<Integer, String>();
	HashMap<Integer, String> d_countriesIDNumber = new HashMap<Integer, String>();

	HashMap<Country, List<Country>> d_neighbors = new HashMap<Country, List<Country>>();
	Graph<Country, DefaultEdge> d_map_graph = new DefaultDirectedGraph<>(DefaultEdge.class);

	ShowMapView d_showMapView = new ShowMapView();

	LogEntryBuffer d_log = new LogEntryBuffer();
	LogPrintHandler d_logPrintHandler = new LogPrintHandler(d_log);

	/**
	 * MapEditor : Constructor for class MapEditor. checks if the map exists or not.
	 * if exists, opens and if not, creates.
	 *
	 * @param p_path string parameter referring to the path
	 * @throws IOException for wrong input
	 */
	public MapEditor(String p_path) throws IOException {
		File d_game = new File("resources/" + p_path + ".game");
		if (d_game.exists()) {
			d_map = new File("resources/" + p_path + ".game");
			System.out.println("opening map");
			loadMap("resources/" + p_path + ".game");
			d_fileName = p_path;
			d_mapFile = "resources/" + p_path + ".game";
		} else {
			d_map = new File("resources/" + p_path + ".map");
			if (d_map.createNewFile()) {
				System.out.println("new map created");
				d_fileName = p_path;
				// getting commands from user
			} else {
				System.out.println("opening map");
				loadMap("resources/" + p_path + ".map");
				d_fileName = p_path;
			}
			d_mapFile = "resources/" + p_path + ".map";
		}
	}
	/**
	 * return the name of the map
	 * @return name of the map file in String
	 */
	public String getFileName(){
		return d_fileName;
	}
	/**
	 * Default constructor
	 */
	public MapEditor() {

	}

	/**
	 * editContinent : takes the user arguments from the command line and change it
	 * to a readable format for tye software.
	 *
	 * @param p_args : the user generated arguments which includes 'editcontinent
	 *               -add continentID continentID -remove countryID' * and accepts
	 *               unlimited number of inputs. the argument will break into
	 *               commands and inputs for the command. * for each command,
	 *               corresponding method will be invoked.
	 */
	public void editContinent(String p_args) {
		String[] l_inputs = p_args.split(" ");
		for (int i = 1; i < l_inputs.length; i++) {
			if (l_inputs[i].equals("-add")) {
				addContinent(l_inputs[i + 1], l_inputs[i + 2]);

				try {
					d_logPrintHandler.LogFileWriter("Continent "+l_inputs[i+1]+ " was added successfully!");
				} catch (IOException e) {
					e.printStackTrace();
				}

			} else if (l_inputs[i].equals("-remove")) {
				// remove continent
				removeContinent(l_inputs[i + 1]);
				try {
					d_logPrintHandler.LogFileWriter("Continent "+l_inputs[i+1]+ " was added successfully!");
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				// wrong input
			}
		}
	}

	/**
	 * editCountry : takes the user arguments from the command line and change it to
	 * a readable format for tye software.
	 *
	 * @param p_args : the user generated arguments which includes 'editcountry -add
	 *               continentID continentID -remove countryID' and accepts
	 *               unlimited number of inputs. the argument will break into
	 *               commands and inputs for the command. for each command,
	 *               corresponding method will be invoked.
	 */
	public void editCountry(String p_args) {
		String[] l_inputs = p_args.split(" ");
		for (int i = 0; i < l_inputs.length; i++) {
			if (l_inputs[i].equals("-add")) {
				addCountry(l_inputs[i + 1], l_inputs[i + 2]);
				// add continent
				try {
					d_logPrintHandler.LogFileWriter("Country "+l_inputs[i+1]+ " was added successfully to "+l_inputs[i+2]);
				} catch (IOException e) {
					e.printStackTrace();
				}
				i += 2;
			} else if (l_inputs[i].equals("-remove")) {
				// remove continent
				removeCountry(l_inputs[i + 1]);
				try {
					d_logPrintHandler.LogFileWriter("Country "+l_inputs[i+1]+ " was removed successfully!");
				} catch (IOException e) {
					e.printStackTrace();
				}
				i++;
			} else {
				// wrong input
			}
		}
	}

	/**
	 * editNeighbor : takes the user arguments from the command line and change it
	 * to a readable format for tye software.
	 *
	 * @param p_args : the user generated arguments which includes 'editneighbor
	 *               -add continentID neighborID -remove countryID neighborID' and
	 *               accepts unlimited number of inputs. the argument will break
	 *               into commands and inputs for the command. For each command,
	 *               corresponding method will be invoked.
	 */
	public void editNeighbor(String p_args) {
		String[] l_inputs = p_args.split(" ");
		for (int i = 0; i < l_inputs.length; i++) {
			if (l_inputs[i].equals("-add")) {
				addNeighbor(l_inputs[i + 1], l_inputs[i + 2]);

				try {
					d_logPrintHandler.LogFileWriter("Country "+l_inputs[i+1]+ " became neighbour with "+l_inputs[i+2]);
				} catch (IOException e) {
					e.printStackTrace();
				}

			} else if (l_inputs[i].equals("-remove")) {
				removeNeighbor(l_inputs[i + 1], l_inputs[i + 2]);

				try {
					d_logPrintHandler.LogFileWriter("Country "+l_inputs[i+1]+ " was removed from neighbourhood of "+l_inputs[i+2]);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				// wrong input
			}
		}
	}

	/**
	 * addContinent : Checks if the continent already exists or not. the new
	 * Continent would be added to a HashMap<String, Continent> of the d_continents
	 * currently residing in the loaded map.
	 *
	 * @param p_continentID    : unique ID for the new continent. Used for verifying
	 *                         if the continent is new.
	 * @param p_continentValue : each continent has a value in is passed as String
	 *                         to the method but converted to Integer.
	 * @see HashMap
	 * @return true if the continent is new and added. false if the continent
	 *         already exists.
	 */
	public boolean addContinent(String p_continentID, String p_continentValue) {
		if (d_continents.containsKey(p_continentID)) {
			// continent already exists
			return false;
		} else {
			Continentcounter++;
			Continent l_continent = new Continent(p_continentID, Integer.parseInt(p_continentValue));
			l_continent.setName(p_continentID);
			d_continents.put(p_continentID, l_continent);
			d_continentsID.put(Continentcounter, p_continentID);
			d_continentsIDNumber.put(Continentcounter, p_continentID);
			return true;
		}
	}

	/**
	 * addCountry: in a specific continent (continentID) a Country is added. First
	 * it would be validated to check if the continent and country already exist. in
	 * case the country is new, it would be added to the continent. The verification
	 * at this level remains solely on checking the validity of single objects not
	 * the whole map.
	 *
	 * @param p_countryID   : the unique ID for each Country
	 * @param p_continentID : the unique ID for each Continent
	 * @return : returns true if the new country is created. returns false if the
	 *         country already exists.
	 */
	public boolean addCountry(String p_countryID, String p_continentID) {
		if (d_continents.containsKey(p_continentID)) {
			if (d_continents.get(p_continentID).getCountries().containsKey(p_continentID)) {
				// country exists
				return false;
			} else {
				Country l_country = new Country(p_countryID, d_continents.get(p_continentID));
				Countrycounter += 1;
				d_countriesIDNumber.put(Countrycounter, p_countryID);
				d_countriesID.put(Countrycounter, p_countryID);
				d_countries.put(p_countryID, l_country);
				d_map_graph.addVertex(l_country);
				getContinents().get(p_continentID).addCountry(p_countryID, l_country);
				return true;
			}
		} else {
			// continent doesn't exists
			return false;
		}
	}

	/**
	 * addNeighbor : first verifies if the country already exists then if neighbor
	 * is new. adds neighbor to a specific country. the neigbor and the country
	 * should be the same.
	 *
	 * @param p_countryID  : unique ID for each country
	 * @param p_neighborID : unique ID of the neighbor country
	 * @return false if country and neighbor are same. false if country or neighbor
	 *         doesn't exists. false if the neighbor is already listed as neighbor
	 *         to the country. true if the neighbor is new.
	 */
	public boolean addNeighbor(String p_countryID, String p_neighborID) {

		if (p_countryID.equals(p_neighborID)) {
			// country and neighbor not the same
			return false;
		}
		if (!d_countries.containsKey(p_countryID)) {
			// country doesn't exists
			return false;
		}
		if (!d_countries.containsKey(p_neighborID)) {
			// neighbor doesn't exist
			return false;
		}
		if (d_countries.get(p_countryID).d_neighbors.contains(d_countries.get(p_neighborID))) {
			// already a neighbor to country
			return false;
		}
		d_countries.get(p_countryID).AddNeighbors(d_countries.get(p_neighborID));
		if (!d_neighbors.containsKey(getCountry(p_countryID))) {// no neighbors yet
			d_neighbors.put(getCountry(p_countryID), new ArrayList<Country>());
			d_neighbors.get(getCountry(p_countryID)).add(getCountry(p_neighborID));
			d_map_graph.addEdge(getCountry(p_countryID), getCountry(p_neighborID));
			// add new country and neighbor to neighbor list
			return true;
		} else {
			d_neighbors.get(getCountry(p_countryID)).add(getCountry(p_neighborID));
			d_map_graph.addEdge(getCountry(p_countryID), getCountry(p_neighborID));
			return true;
		}
	}

	/**
	 * removeNeighbor : remove specific neighbor of a specific country. First
	 * validate the existance of the country and the neighbor
	 *
	 * @param p_countryID  : unique ID for the country
	 * @param p_neighborID : unique ID for the neighbor countries
	 * @return true on successful termination of removal process. false if the
	 *         country or neigbor do not exits.
	 */
	public boolean removeNeighbor(String p_countryID, String p_neighborID) {
		if (!d_countries.containsKey(p_countryID)
				|| !d_countries.get(p_countryID).d_neighbors.contains(getCountry(p_neighborID))
				|| !d_countries.containsKey(p_neighborID)) {
			// country or neighbor doesn't exist
			return false;
		}
		d_neighbors.get(getCountry(p_countryID)).remove(getCountry(p_neighborID));
		getCountry(p_countryID).RemoveNeighbor(getCountry(p_neighborID));
		d_map_graph.removeEdge(getCountry(p_countryID), getCountry(p_neighborID));
		return true;
	}

	/**
	 * removeCountry : to remove the a country, first the country must be removed
	 * from the neighborList of each neighbor. then it must be removed from the list
	 * of countries the respective continent.
	 *
	 * @param p_countryID unique ID country
	 * @return true if the removing process terminates successfully.
	 */
	public boolean removeCountry(String p_countryID) {
		if (!d_countries.containsKey(p_countryID)) {
			// country doesn't exist
			return false;
		}
		d_neighbors.forEach((k, v) -> {
			v.remove(getCountry(p_countryID));
		});
		d_neighbors.remove(getCountry(p_countryID));
		d_map_graph.removeVertex(getCountry(p_countryID));
		getContinents().get(getCountry(p_countryID).getContinent().getContinentID())
				.removeCountry(getCountry(p_countryID));

		d_countries.remove(p_countryID);
		return true;
	}

	/**
	 * removeContinent : first verifies the existence of the continent. then removes
	 * the countries in each continent, removeneighbors will be called to handle
	 * neighbor removal.
	 *
	 * @param p_continentID : unique ID for each country
	 * @return true if the removing process terminates successfully.
	 */
	public boolean removeContinent(String p_continentID) {
		if (!d_continents.containsKey(p_continentID)) {
			// continent doesn't exist
			return false;
		}
		getContinent(p_continentID).getCountriesList().forEach(n -> removeCountry(n.getID()));
		// d_continent_counter--;
		d_continents.remove(p_continentID);
		return true;
	}

	/**
	 * overriding the loadMap method. this method received a path to map file.
	 *
	 * @param p_path path to the map file - String format
	 * @throws FileNotFoundException handle error if path doesn't include the map
	 *                               file
	 */
	public void loadMap(String p_path) throws FileNotFoundException {
		BufferedReader l_p_reader = new BufferedReader(new FileReader(p_path));
		loadMap(l_p_reader);
	}

	/**
	 * loadMap, get an existing map and loads the data of the map such as
	 * d_continents, Countries and borders.
	 *
	 * @param p_reader file of map - Bufferp_reader format
	 */
	public void loadMap(BufferedReader p_reader) {
		int i;
		String[] inputs;
		Country l_country;
		Continent l_continent;

		try {
			String l_line = p_reader.readLine();
			while (l_line != null) {
				if (l_line.toLowerCase().contains("[continents]")) {
					i = 0;
					l_line = p_reader.readLine();
					while (l_line != null) {
						if (l_line.toLowerCase().contains("countries") || l_line.toLowerCase().contains("borders")
								|| l_line.toLowerCase().contains("files"))
							break;
						if (!l_line.toLowerCase().contains(";")) {
							//creating new continent, addin object to map
							inputs = l_line.split(" ");
							if (inputs.length >= 2) {
//								Continentcounter++;
								addContinent(inputs[0], inputs[1]);
//								d_continentsID.put(i, inputs[0]);
//								d_continentsIDNumber.put(i, inputs[0]);
								i++;
							}
						}
						l_line = p_reader.readLine();
					}
				}

				if (l_line.toLowerCase().contains("[countries]")) {
					i = 0;
					l_line = p_reader.readLine();
					while (l_line != null) {
						if (l_line.toLowerCase().contains("continents") || l_line.toLowerCase().contains("borders")
								|| l_line.toLowerCase().contains("files"))
							break;
						if (!l_line.toLowerCase().contains(";")) {
							//creating new country adding to map
							inputs = l_line.split(" ");
							if (inputs.length >= 3) {
								if (d_continents.get(d_continentsID.get(Integer.parseInt(inputs[2])+1)) != null) {
									addCountry(inputs[1], d_continentsID.get(Integer.parseInt(inputs[2])+1));
									//d_countriesID.put(Integer.parseInt(inputs[0]), inputs[1]);
								}
							}
						}
						l_line = p_reader.readLine();
					}
				}

				if (l_line.toLowerCase().contains("[borders]")) {
					l_line = p_reader.readLine();
					while (l_line != null) {
						if (l_line.toLowerCase().contains("continents") || l_line.toLowerCase().contains("countries")
								|| l_line.toLowerCase().contains("files")
								|| l_line.toLowerCase().contains("players")
								|| l_line.toLowerCase().contains("territory")
								|| l_line.toLowerCase().contains("cards"))
							break;
						if (!l_line.toLowerCase().contains(";")) {
							inputs = l_line.split(" ");
							//adding neighbors
							if (inputs.length > 1) {
								if (d_countriesID.containsKey(Integer.parseInt(inputs[0]))) {
									for (int n = 1; n < inputs.length; n++) {
										addNeighbor(d_countriesID.get(Integer.parseInt(inputs[0])),
												d_countriesID.get(Integer.parseInt(inputs[n])));
									}
								}
							}
						}
						l_line = p_reader.readLine();
					}
				}
				l_line = p_reader.readLine();
			}
			p_reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * getContinent : get the continent as Object from the HashMap of d_continents
	 * in the loaded Map.
	 *
	 * @param p_continentID the unique identifier Name of each continent.
	 * @return a single object of continent by Name
	 */
	public Continent getContinent(String p_continentID) {
		return d_continents.get(p_continentID);
	}

	/**
	 * getContinent : get the continent as Object from the HashMap of d_continents
	 * in the loaded Map.
	 *
	 * @param p_continentID the unique identifier Name of each continent.
	 * @return a single object of continent by Name. return 0 if not found. return
	 *         the index if found;
	 */
	public int getContinentNumber(String p_continentID) {

		for (int i = 1; i < d_continentsIDNumber.size(); i++) {
			if (d_continentsIDNumber.get(i).equals(p_continentID))
				return i;
		}
		return 0;
	}

	/**
	 * Getter for class attribute continents
	 *
	 * @return a Hashmap<String, Object> of Continent
	 */
	public HashMap<String, Continent> getContinents() {
		return d_continents;
	}

	/**
	 * getter for the country objects in the Map including their unique ID
	 *
	 * @return Hashmap<String, Country> returns a hashmap of all countries in the
	 *         map.
	 */
	public HashMap<String, Country> getCountries() {
		return d_countries;
	}

	/**
	 * getCountry : get the Country as Object from the HashMap of countries in the
	 * loaded Map.
	 *
	 * @param p_countryID the unique identifier of each country.
	 * @return a single object of Country with p_CountryID ID.
	 */
	public Country getCountry(String p_countryID) {
		return d_countries.get(p_countryID);
	}

	/**
	 * getCountryNumber : get the ID of Country as Integer from the HashMap of
	 * countries and ID numbers.
	 *
	 * @param p_countryID the unique identifier of each country.
	 * @return return the index if found. return 0 if not found
	 */
	public int getCountryNumber(String p_countryID) {
		for (int i = 1; i < d_countriesIDNumber.size(); i++) {
			if (d_countriesIDNumber.get(i).equals(p_countryID))
				return i;
		}
		return 0;
	}

	/**
	 * getter for all the neighbor data in the map
	 *
	 * @return HashMap<Country, List<Country>> returns the neighbors as a List of
	 *         Country objects for each Object of Country
	 */
	public HashMap<Country, List<Country>> getNeighbors() {
		return d_neighbors;
	}

	/**
	 * in order to have more control over the data and map, we converted the map of
	 * countries to a graph using JGraphT. vertices are the countries and edges are
	 * the border between countries.
	 *
	 * @return Graph of connected countries and edges between them.
	 * @see org.jgrapht.demo.HelloJGraphT
	 */
	public Graph<Country, DefaultEdge> getMapGraph() {
		return d_map_graph;
	}

	/**
	 * This method validates whether the map is valid/invalid
	 *
	 * @return true if the map is a valid map
	 */
	public boolean validateMap() {

		return validateMap(d_map_graph, d_continents);

	}

	/**
	 * This method validates whether the map is valid/invalid
	 *
	 * 1.Check if the graph is connected 2.Check if there are any continents that
	 * doesn't have any country 3. Check if each continent is a connected subgraph
	 * doesn't have any country 3. Check if graph is directed
	 *
	 * @param p_map_graph The current state of the map
	 * @return true if the map is a valid map
	 *
	 */
	public boolean validateMap(Graph<Country, DefaultEdge> p_map_graph, HashMap<String, Continent> p_continents) {

		if (checkGraphConnected(p_map_graph)) {
			if (checkIfContinentHasCountries(p_continents)) {
				if (checkGraphIsDirected(p_map_graph)) {

					try {
						d_logPrintHandler.LogFileWriter("Map was validated successfully");
					} catch (IOException e) {
						e.printStackTrace();
					}

					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Checks whether the map is connected or disconnected
	 * @param p_map_graph The current graph
	 * @return true of the graph is connected and false if it is disconnected
	 */
	public boolean checkGraphConnected(Graph<Country, DefaultEdge> p_map_graph) {

		if (GraphTests.isConnected(p_map_graph)) {
			try {
				d_logPrintHandler.LogFileWriter("Graph was connected successfully.");
			} catch (IOException e) {
				e.printStackTrace();
			}

			return true;
		} else {

			try {
				d_logPrintHandler.LogFileWriter("Graph was not validated successfully");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}
	}

	/**
	 * Overloaded method which checks if the graph is directed or not
	 *
	 * @param p_map_graph The current graph
	 * @return true if graph is directed
	 */
	public boolean checkGraphIsDirected(Graph<Country, DefaultEdge> p_map_graph) {

		boolean l_isDirected = true;
		try {
			GraphTests.requireDirected(p_map_graph);
		} catch (IllegalArgumentException e) {

			l_isDirected = false;
		}
		return l_isDirected;
	}

	/**
	 * Checks if graph is directed
	 *
	 * @return true if graph is directed
	 */
	public boolean checkGraphIsDirected() {

		boolean l_isDirected = true;
		try {
			GraphTests.requireDirected(d_map_graph);
		} catch (IllegalArgumentException e) {

			l_isDirected = false;
		}
		return l_isDirected;
	}

	/**
	 * Checks if the continent has countries
	 *
	 * @return true of all the continents have countries and false any one continent
	 *         doesn't have a country
	 */
	public boolean checkIfContinentHasCountries(HashMap<String, Continent> p_continents) {

		for (Continent l_continent : d_continents.values()) {
			int l_countrySize = l_continent.getCountries().values().size();
			if (l_countrySize == 0) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Saves the map if it is valid
	 *
	 * @param p_fileName the name of the file we want to save as our map/new map.
	 *                   this would be stored in /resources/[p_fileName].map (the
	 *                   format of files are .map)
	 * @return true if file is saved, false otherwise.
	 */
	public boolean savemap(String p_fileName) {
		boolean isVaildMap = true;
		if (validateMap()) {
			try {
				BufferedWriter l_writer = new BufferedWriter(new FileWriter("resources/" + p_fileName + ".map"));
				//write map name to file
				l_writer.write(";name " + p_fileName + " Map");
				l_writer.newLine();
				l_writer.newLine();
				l_writer.write("[files]");
				l_writer.newLine();
				l_writer.newLine();

				//add continents to file
				l_writer.write("[continents]");
				for (int i = 1; i <= d_continents.values().size(); i++) {
					l_writer.newLine();
					String l_continentName = d_continentsID.get(i);
					Continent l_continent = d_continents.get(l_continentName);
					int l_continentValue = l_continent.getContinentValue();
					l_writer.write(l_continentName + " " + Integer.toString(l_continentValue));
				}
				l_writer.newLine();
				l_writer.flush();
				l_writer.newLine();
				l_writer.newLine();

				//add countries to file
				l_writer.write("[countries]");
				for (int i = 1; i <= d_countries.size(); i++) {
					l_writer.newLine();
					l_writer.write(String.valueOf(i) + " " + d_countriesID.get(i) + " "
							+ getContinentNumber(getCountry(d_countriesID.get(i)).getContinent().getContinentID()));
				}
				l_writer.flush();
				l_writer.newLine();
				l_writer.newLine();

				//add borders to file
				l_writer.write("[borders]");
				l_writer.newLine();
				d_neighbors.forEach((k, v) -> {
					try {
						l_writer.newLine();
						l_writer.write(String.valueOf(getCountryID(k.getID())));
						v.forEach(q -> {
							try {
								l_writer.write(" " + String.valueOf(getCountryID(q.getID())));
							} catch (IOException e) {
								e.printStackTrace();
							}
						});
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
				l_writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			isVaildMap = false;
			System.out.println("map is not Valid");

			try {
				d_logPrintHandler.LogFileWriter("Map was not saved successfully.");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return isVaildMap;
	}

	/**
	 * Saves the map if it is valid
	 *
	 * @param p_fileName the name of the file we want to save as our map/new map.
	 *                   this would be stored in /resources/[p_fileName].map (the
	 *                   format of files are .map)
	 * @return true if file is saved, false otherwise.
	 */
	public boolean saveGameMap(String p_fileName) {
		boolean isVaildMap = true;
		if (validateMap()) {
			try {
				BufferedWriter l_writer = new BufferedWriter(new FileWriter("resources/" + p_fileName + ".game"));
				//write map name to file
				l_writer.write(";name " + p_fileName + " Map");
				l_writer.newLine();
				l_writer.newLine();
				l_writer.write("[files]");
				l_writer.newLine();
				l_writer.newLine();

				//add continents to file
				l_writer.write("[continents]");
				for (int i = 1; i <= d_continents.values().size(); i++) {
					l_writer.newLine();
					String l_continentName = d_continentsID.get(i);
					Continent l_continent = d_continents.get(l_continentName);
					int l_continentValue = l_continent.getContinentValue();
					l_writer.write(l_continentName + " " + Integer.toString(l_continentValue));
				}
				l_writer.newLine();
				l_writer.flush();
				l_writer.newLine();
				l_writer.newLine();

				//add countries to file
				l_writer.write("[countries]");
				for (int i = 1; i <= d_countries.size(); i++) {
					l_writer.newLine();
					l_writer.write(String.valueOf(i) + " " + d_countriesID.get(i) + " "
							+ getContinentNumber(getCountry(d_countriesID.get(i)).getContinent().getContinentID()));
				}
				l_writer.flush();
				l_writer.newLine();
				l_writer.newLine();

				//add borders to file
				l_writer.write("[borders]");
				l_writer.newLine();
				d_neighbors.forEach((k, v) -> {
					try {
						l_writer.newLine();
						l_writer.write(String.valueOf(getCountryID(k.getID())));
						v.forEach(q -> {
							try {
								l_writer.write(" " + String.valueOf(getCountryID(q.getID())));
							} catch (IOException e) {
								e.printStackTrace();
							}
						});
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
				l_writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			isVaildMap = false;
			System.out.println("map is not Valid");

			try {
				d_logPrintHandler.LogFileWriter("Map was not saved successfully.");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return isVaildMap;
	}

	/**
	 * return the Number ID of the country for the Country that matches the input.
	 *
	 * @param p_country Name of the Country we want its Number ID
	 * @return the index of the Country in CountryID by its Name
	 */
	public int getCountryID(String p_country) {
		for (int i : d_countriesID.keySet()) {
			if (d_countriesID.get(i).equals(p_country))
				return i;
		}

		return 0;
	}

	/**
	 * getter for the map file that was opened or created.
	 *
	 * @return a File to the map.
	 */
	public File getFile() {
		return new File("resources/" + d_mapFile + ".map");
	}

	/**
	 * getter of the path to the map that was opened or created;
	 *
	 * @return a string containing the path.
	 */

	public String getFilePath() {
		return d_mapFile;
	}

	/**
	 * Checks if countries in the continent are connected
	 *
	 * @return true if it connected and false if not connected
	 */
	public boolean checkContinentConnectivity(HashMap<String, Continent> p_continents) {

		for (Continent l_continent : p_continents.values()) {
			Graph<Country, DefaultEdge> l_graph = new DefaultUndirectedGraph<>(DefaultEdge.class);
			for (Country l_country : l_continent.getCountries().values()) {
				l_graph.addVertex(l_country);
			}

			for (Country l_country : l_continent.getCountries().values()) {
				for (Country l_neighbor : l_country.getNeighbors()) {
					l_graph.addEdge(l_country, l_neighbor);
				}
			}

			if (!GraphTests.isConnected(l_graph)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Displays the continent and the countries.
	 */
	public void showmap() {
		d_showMapView.showMapEditor(d_continents, d_neighbors);
	}


	/**
	 * Load Map
	 */
	public void loadMap() {
		try {
			loadMap(d_fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * get the continent ID hashmap. The ID is the number in the file map
	 * @return d_continentID
	 */
	public HashMap<Integer, String> get_continentID(){
		return d_continentsID;
	}

	/**
	 * add to the hashmap d_continentID
	 * @param p_contienetIDNum the ID number of each continent based on the number in the file
	 * @param p_continentID the name of each continent in String
	 */
	public void addContinentID(Integer p_contienetIDNum, String p_continentID){
		d_continentsID.put(p_contienetIDNum, p_continentID);
	}


	/**
	 * get the countryID hashmap. the HashMap for connecting the number of countries in the map file
	 * to their names
	 * @return d_countryID
	 */
	public HashMap<Integer, String> get_countriesID(){
		return d_countriesID;
	}

	/**
	 * add a country with its ID to d_countriesID HashMap
	 */
	public void addCountyID (Integer p_countryID, String p_countryName){
		d_countriesID.put(p_countryID, p_countryName);
	}
}



