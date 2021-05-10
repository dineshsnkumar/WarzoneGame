package ca.concordia.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.IOException;
import java.util.ArrayList;

import ca.concordia.model.*;
import org.junit.Test;

import ca.concordia.controller.GameEngine;
import ca.concordia.controller.LoadMapController;
import ca.concordia.model.HumanPlayer;

/**
 * OrderTests Class for testing Order Functionality
 * @author Pouria
 */

public class OrderTests {

    MapEditor d_mapEditor;
    ArrayList<HumanPlayer> d_players = new ArrayList<HumanPlayer>();


    /**
     * Testing Deploy Functionality
     * @throws IOException
     */
    @Test
    public void DeployTest() throws IOException {

        LoadMapController l_load = new LoadMapController("haiti");
        d_mapEditor = l_load.getMap();
        GameEngine l_gameEngine = new GameEngine(d_mapEditor, d_players);


        for (int i = 0; i < 4; i++) {
            HumanPlayer l_player = new HumanPlayer("Player" + i);
            d_players.add(l_player);
        }
        l_gameEngine.setD_players(d_players);
        l_gameEngine.armyAssign();
//		l_gameEngine.setL_command("");
        Deploy l_order = new Deploy(l_gameEngine.getD_players().get(0), l_gameEngine.getD_players().get(0).getCountries().get(0), 4);
        l_gameEngine.getD_players().get(0).issue_order(l_order, Orders.DEPLOY);
        assertEquals(Orders.DEPLOY, l_gameEngine.getD_players().get(0).next_type());
//


    }

    /**
     * Testing Advance Functionality
     * @throws IOException
     */
    @Test
    public void AdvanceTest() throws IOException {

        LoadMapController l_load = new LoadMapController("haiti");
        d_mapEditor = l_load.getMap();
        GameEngine l_gameEngine = new GameEngine(d_mapEditor, d_players);


        for (int i = 0; i < 4; i++) {
            HumanPlayer l_player = new HumanPlayer("Player" + i);
            d_players.add(l_player);
        }
        l_gameEngine.setD_players(d_players);
        l_gameEngine.armyAssign();
//		l_gameEngine.setL_command("");
        Advance l_order = new Advance(l_gameEngine.getD_players().get(0), l_gameEngine.getD_players().get(0).getCountries().get(0), l_gameEngine.getD_players().get(0).getCountries().get(1), 1, d_mapEditor);
        l_gameEngine.getD_players().get(0).issue_order(l_order, Orders.ADVANCE);
        assertEquals(Orders.ADVANCE, l_gameEngine.getD_players().get(0).next_type());
//


    }
    /**
     * Testing Attack Functionality
     * @throws IOException
     */
    @Test
    public void AttackTest() throws IOException {

        LoadMapController l_load = new LoadMapController("haiti");
        d_mapEditor = l_load.getMap();
        GameEngine l_gameEngine = new GameEngine(d_mapEditor, d_players);


        for (int i = 0; i < 4; i++) {
            HumanPlayer l_player = new HumanPlayer("Player" + i);
            d_players.add(l_player);
        }
        l_gameEngine.setD_players(d_players);
        l_gameEngine.armyAssign();
//		l_gameEngine.setL_command("");
        Advance l_order = new Advance(l_gameEngine.getD_players().get(0), l_gameEngine.getD_players().get(0).getCountries().get(0), l_gameEngine.getD_players().get(1).getCountries().get(1), 1, d_mapEditor);
        l_gameEngine.getD_players().get(0).issue_order(l_order, Orders.ATTACK);
        assertEquals(Orders.ATTACK, l_gameEngine.getD_players().get(0).next_type());
//


    }

    /**
     * Testing AirLift Functionality
     * @throws IOException
     */
    @Test
    public void AirLiftTest() throws IOException {

        LoadMapController l_load = new LoadMapController("haiti");
        d_mapEditor = l_load.getMap();
        GameEngine l_gameEngine = new GameEngine(d_mapEditor, d_players);


        for (int i = 0; i < 4; i++) {
            HumanPlayer l_player = new HumanPlayer("Player" + i);
            d_players.add(l_player);
        }
        l_gameEngine.setD_players(d_players);
        l_gameEngine.armyAssign();
//		l_gameEngine.setL_command("");
        Airlift l_order = new Airlift(l_gameEngine.getD_players().get(0), l_gameEngine.getD_players().get(0).getCountries().get(0), l_gameEngine.getD_players().get(0).getCountries().get(2), 1);
        l_gameEngine.getD_players().get(0).issue_order(l_order, Orders.AIRLIFT);
        assertEquals(Orders.AIRLIFT, l_gameEngine.getD_players().get(0).next_type());
//


    }

    /**
     * Testing Bomb Functionality
     * @throws IOException
     */
    @Test
    public void BombTest() throws IOException {

        LoadMapController l_load = new LoadMapController("haiti");
        d_mapEditor = l_load.getMap();
        GameEngine l_gameEngine = new GameEngine(d_mapEditor, d_players);


        for (int i = 0; i < 4; i++) {
            HumanPlayer l_player = new HumanPlayer("Player" + i);
            d_players.add(l_player);
        }
        l_gameEngine.setD_players(d_players);
        l_gameEngine.armyAssign();
//		l_gameEngine.setL_command("");
        Bomb l_order = new Bomb(l_gameEngine.getD_players().get(0), l_gameEngine.getD_players().get(0).getCountries().get(0), d_mapEditor);
        l_gameEngine.getD_players().get(0).issue_order(l_order, Orders.BOMB);
        assertEquals(Orders.BOMB, l_gameEngine.getD_players().get(0).next_type());
//


    }

    /**
     * Testing Negotiate Functionality
     * @throws IOException
     */
    @Test
    public void NegotiateTest() throws IOException {

        LoadMapController l_load = new LoadMapController("haiti");
        d_mapEditor = l_load.getMap();
        GameEngine l_gameEngine = new GameEngine(d_mapEditor, d_players);


        for (int i = 0; i < 4; i++) {
            HumanPlayer l_player = new HumanPlayer("Player" + i);
            d_players.add(l_player);
        }
        l_gameEngine.setD_players(d_players);
        l_gameEngine.armyAssign();
//		l_gameEngine.setL_command("");
        Negotiate l_order = new Negotiate(l_gameEngine.getD_players().get(0));
        l_gameEngine.getD_players().get(0).issue_order(l_order, Orders.NEGOTIATE);
        assertEquals(Orders.NEGOTIATE, l_gameEngine.getD_players().get(0).next_type());
//


    }


    /**
     * Testing Blockade Functionality
     * @throws IOException
     */
    @Test
    public void BlockadeTest() throws IOException {

        LoadMapController l_load = new LoadMapController("haiti");
        d_mapEditor = l_load.getMap();
        GameEngine l_gameEngine = new GameEngine(d_mapEditor, d_players);


        for (int i = 0; i < 4; i++) {
            HumanPlayer l_player = new HumanPlayer("Player" + i);
            d_players.add(l_player);
        }
        l_gameEngine.setD_players(d_players);
        l_gameEngine.armyAssign();
//		l_gameEngine.setL_command("");
        Blockade l_order = new Blockade(l_gameEngine.getD_players().get(0), l_gameEngine.getD_players().get(0).getCountries().get(0));
        l_gameEngine.getD_players().get(0).issue_order(l_order, Orders.BLOCKADE);
        assertEquals(Orders.BLOCKADE, l_gameEngine.getD_players().get(0).next_type());
//


    }

    /**
     * Test Reinforcement
     * @throws IOException
     */

    @Test
    public void reinforcement() throws IOException {

        LoadMapController l_load = new LoadMapController("haiti");
        d_mapEditor = l_load.getMap();
        GameEngine l_gameEngine = new GameEngine(d_mapEditor, d_players);


        for (int i = 0; i < 4; i++) {
            HumanPlayer l_player = new HumanPlayer("Player" + i);
            d_players.add(l_player);
        }
        l_gameEngine.setD_players(d_players);
        l_gameEngine.reinforcement();


        assertEquals(4, l_gameEngine.getD_player_army().size());
        assertNotEquals(13, l_gameEngine.getD_players().get(0).getCountries().size());
    }

    /**
     * Test AddPlayer Functionality
     * @throws IOException
     */

    @Test
    public void addPlayer() throws IOException {

        LoadMapController l_load = new LoadMapController("haiti");
        d_mapEditor = l_load.getMap();
        GameEngine l_gameEngine = new GameEngine(d_mapEditor, d_players);



        HumanPlayer l_player = new HumanPlayer("PlayerD");
        d_players.add(l_player);

        l_gameEngine.setD_players(d_players);
        l_gameEngine.reinforcement();


        assertEquals("PlayerD", l_gameEngine.getD_players().get(0).getPlayerName());

    }

    /**
     * Test RemovePlayer Functionality
     * @throws IOException
     */
    @Test
    public void removelayer() throws IOException {

        LoadMapController l_load = new LoadMapController("haiti");
        d_mapEditor = l_load.getMap();
        GameEngine l_gameEngine = new GameEngine(d_mapEditor, d_players);



        HumanPlayer l_player = new HumanPlayer("PlayerD");
        d_players.add(l_player);

        l_gameEngine.setD_players(d_players);
        l_gameEngine.reinforcement();

        d_players.remove(0);
        l_gameEngine.setD_players(d_players);


        assertEquals(0, l_gameEngine.getD_players().size());

    }



}