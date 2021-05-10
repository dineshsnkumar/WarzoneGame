package ca.concordia.model;

import ca.concordia.controller.GameEngine;
import ca.concordia.controller.StartUpController;
import ca.concordia.model.*;
import ca.concordia.observer.Observable;

import java.time.LocalTime;

/**
 * this class handles Observable concept of Observer pattern that defines the information about player's action
 * To explicate more, observable prepares a buffer for logs which add logs through addlog()
 * As a result observers can be notified
 *  @author Solmaz
 */
public class LogEntryBuffer extends Observable {
    /**
     * GameEngine Object
     */
    private GameEngine d_gameEngine;
    private String d_player;

    /**
     * Default constructor for Class LogEntryBuffer
     */
    public LogEntryBuffer() {
    }

    /**
     * Constructor for Class LogEntryBuffer
     * @param p_gameEngine is the game general inforemation
     */
    public LogEntryBuffer(GameEngine p_gameEngine){
        d_gameEngine=p_gameEngine;
    }

    /**
     * return the name of the player
     * @return string name of player
     */
    public String getPlayer() {
        return d_player;
    }
}
