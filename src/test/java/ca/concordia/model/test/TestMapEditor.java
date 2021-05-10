package ca.concordia.model.test;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.junit.Test;

import ca.concordia.model.Continent;
import ca.concordia.model.Country;
import ca.concordia.model.MapEditor;
import ca.concordia.model.*;

public class TestMapEditor {

    Graph<Country, DefaultEdge> l_graph;
    Continent l_continent;
    Country l_country1;
    Country l_country2;
    Country l_country3;

    /**
     * Test if the Graph is connected
     */
    @Test
    public void testGraphIsConnected() {
        l_graph = new DefaultDirectedGraph<>(DefaultEdge.class);

        l_continent = new Continent("111", 2);

        l_country1 = new Country("1", l_continent);
        l_country2 = new Country("2", l_continent);
        l_country3 = new Country("3", l_continent);

        // add the vertices
        l_graph.addVertex(l_country1);
        l_graph.addVertex(l_country2);
        l_graph.addVertex(l_country3);

        // add edges to create linking structure
        l_graph.addEdge(l_country1, l_country2);
        l_graph.addEdge(l_country2, l_country3);
        l_graph.addEdge(l_country3, l_country1);

        MapEditor l_mapEditor = new MapEditor();

        assertEquals(true, l_mapEditor.checkGraphConnected(l_graph));

    }

    /**
     * check if the graph is not connected. it should false.
     */
    @Test
    public void testGraphIsNotConnected() {
        l_graph = new DefaultDirectedGraph<>(DefaultEdge.class);

        l_continent = new Continent("111", 2);

        l_country1 = new Country("1", l_continent);
        l_country2 = new Country("2", l_continent);
        l_country3 = new Country("3", l_continent);

        // add the vertices
        l_graph.addVertex(l_country1);
        l_graph.addVertex(l_country2);
        l_graph.addVertex(l_country3);

        // add edges to create linking structure
        l_graph.addEdge(l_country1, l_country2);

        MapEditor l_mapEditor = new MapEditor();

        assertEquals(false, l_mapEditor.checkGraphConnected(l_graph));
    }

    /**
     * test if the graph is directed
     */
    @Test
    public void testGraphIsDirected() {
        l_graph = new DefaultDirectedGraph<>(DefaultEdge.class);

        l_continent = new Continent("111", 2);

        l_country1 = new Country("1", l_continent);
        l_country2 = new Country("2", l_continent);
        l_country3 = new Country("3", l_continent);

        // add the vertices
        l_graph.addVertex(l_country1);
        l_graph.addVertex(l_country2);
        l_graph.addVertex(l_country3);

        // add edges to create linking structure
        l_graph.addEdge(l_country1, l_country2);
        l_graph.addEdge(l_country2, l_country3);
        l_graph.addEdge(l_country3, l_country1);

        MapEditor l_mapEditor = new MapEditor();

        assertEquals(true, l_mapEditor.checkGraphIsDirected(l_graph));
    }

    /**
     * check if the graph is undirected. it should be false
     */
    @Test
    public void testGraphIsUnDirected() {
        l_graph = new DefaultUndirectedGraph<>(DefaultEdge.class);

        l_continent = new Continent("111", 2);

        l_country1 = new Country("1", l_continent);
        l_country2 = new Country("2", l_continent);
        l_country3 = new Country("3", l_continent);

        // add the vertices
        l_graph.addVertex(l_country1);
        l_graph.addVertex(l_country2);
        l_graph.addVertex(l_country3);

        MapEditor l_mapEditor = new MapEditor();

        assertEquals(false, l_mapEditor.checkGraphIsDirected(l_graph));
    }

    /**
     * test if map is invalid. it should be false
     */
    @Test
    public void testInValidationMap() {
        l_graph = new DefaultDirectedGraph<>(DefaultEdge.class);

        l_continent = new Continent("111", 2);

        l_country1 = new Country("1", l_continent);
        l_country3 = new Country("3", l_continent);
        l_country2 = new Country("2", l_continent);

        // add the vertices
        l_graph.addVertex(l_country1);
        l_graph.addVertex(l_country2);
        l_graph.addVertex(l_country3);

        // add edges to create linking structure
        l_graph.addEdge(l_country1, l_country2);

        MapEditor l_mapEditor = new MapEditor();

        HashMap<String, Continent> l_countryHashMap = new HashMap<String, Continent>();
        l_countryHashMap.put("1", l_continent);
        assertEquals(false, l_mapEditor.validateMap(l_graph, l_countryHashMap));
    }

    /**
     * test if the map is valid
     */
    @Test
    public void testValidationMap() {
        l_graph = new DefaultDirectedGraph<>(DefaultEdge.class);

        l_continent = new Continent("111", 2);

        l_country1 = new Country("1", l_continent);
        l_country3 = new Country("3", l_continent);
        l_country2 = new Country("2", l_continent);

        // add the vertices
        l_graph.addVertex(l_country1);
        l_graph.addVertex(l_country2);
        l_graph.addVertex(l_country3);

        // add edges to create linking structure
        l_graph.addEdge(l_country1, l_country2);
        l_graph.addEdge(l_country2, l_country3);
        l_graph.addEdge(l_country3, l_country1);

        MapEditor l_mapEditor = new MapEditor();

        HashMap<String, Continent> l_countryHashMap = new HashMap<String, Continent>();
        l_countryHashMap.put("1", l_continent);
        assertEquals(true, l_mapEditor.validateMap(l_graph, l_countryHashMap));
    }

    /**
     * check if the continent is connected
     */
    @Test
    public void testContinentConnectivity() {
        l_graph = new DefaultDirectedGraph<>(DefaultEdge.class);

        l_continent = new Continent("111", 2);

        l_country1 = new Country("1", l_continent);
        l_country2 = new Country("2", l_continent);
        l_country3 = new Country("3", l_continent);

        l_continent.addCountry("1", l_country1);
        l_continent.addCountry("2", l_country2);

        l_country1.AddNeighbors(l_country2);
        l_country2.AddNeighbors(l_country1);

        MapEditor l_mapEditor = new MapEditor();

        HashMap<String, Continent> l_continentHashMap = new HashMap<String, Continent>();
        l_continentHashMap.put("111", l_continent);

        assertEquals(true, l_mapEditor.checkContinentConnectivity(l_continentHashMap));
    }

    /**
     * check continent is disconnected. should be false
     */
    @Test
    public void testContinentNotConnected() {
        l_continent = new Continent("111", 2);

        l_country1 = new Country("1", l_continent);
        l_country2 = new Country("2", l_continent);
        l_country3 = new Country("3", l_continent);

        l_continent.addCountry("1", l_country1);
        l_continent.addCountry("2", l_country2);

        MapEditor l_mapEditor = new MapEditor();

        HashMap<String, Continent> l_continentHashMap = new HashMap<String, Continent>();
        l_continentHashMap.put("111", l_continent);

        assertEquals(false, l_mapEditor.checkContinentConnectivity(l_continentHashMap));
    }

    /**
     * test if continents has countries
     */
    @Test
    public void testContinentHasCountries() {
        l_continent = new Continent("111", 2);

        l_country1 = new Country("1", l_continent);
        l_country2 = new Country("2", l_continent);
        l_country3 = new Country("3", l_continent);

        l_continent.addCountry("1", l_country1);
        l_continent.addCountry("2", l_country2);

        MapEditor l_mapEditor = new MapEditor();

        HashMap<String, Continent> l_continentHashMap = new HashMap<String, Continent>();
        l_continentHashMap.put("111", l_continent);

        assertEquals(true, l_mapEditor.checkIfContinentHasCountries(l_continentHashMap));
    }

    /**
     * check if the continent has no countries.
     */
    @Test
    public void testContinentHasNoCountries() {
        l_continent = new Continent("111", 2);
        MapEditor l_mapEditor = new MapEditor();

        HashMap<String, Continent> l_continentHashMap = new HashMap<String, Continent>();
        l_continentHashMap.put("111", l_continent);

        assertEquals(true, l_mapEditor.checkIfContinentHasCountries(l_continentHashMap));
    }
}
