package ca.concordia.view;

import ca.concordia.model.*;

import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * view class for Game Status
 */
public class ShowGameStatus {
    GameStatus d_gameStatus;

    /**
     * ShowGameStatus constructor
     * @param p_gameStatus gameStatus obj
     */
    public ShowGameStatus(GameStatus p_gameStatus) {
        d_gameStatus = p_gameStatus;
    }

    /**
     * Show the Game status. starting with Map editor, then showing the players and their cards and armies
     */
    public void showGame() {
        ShowMapView l_showMap = new ShowMapView();
        l_showMap.showMapEditor(d_gameStatus.getMap().getContinents(), d_gameStatus.getMap().getNeighbors());

        for (Player player : d_gameStatus.getPlayers()) {
            System.out.print(player.getPlayerName() +
                    ", Number of armies left for reinforcement phase->" + player.getOwnedArmies() +
                    "\n\tcards ->\n");

            for (Orders order : player.getOwnedCards()) {
                System.out.print(String.valueOf(order) + ", ");
            }

        }
    }

    /**
     * save status of the game into a file
     */
    public void saveStatus() {
        Scanner l_scan = new Scanner(System.in);
        System.out.println("save the game as: ");
        String l_fileName = l_scan.nextLine();
        d_gameStatus.getMap().saveGameMap(l_fileName);
        File l_map = new File("resources/" + l_fileName+ ".game");
        try {
            FileWriter l_fileWriter = l_fileWriter = new FileWriter(l_map, true);
            BufferedWriter br = new BufferedWriter(l_fileWriter);
            PrintWriter pr = new PrintWriter(br);
            //pr.println(LocalTime.now().toString()+ " " + p_logContent);

            pr.println("\n");
            pr.println("\n[players]");
            for (Player player : d_gameStatus.getPlayers()) {
                pr.println(player.getPlayerName() + " " +d_gameStatus.getPlayerArmies().get(player));
            }

            pr.println("\n");
            pr.println("\n[territory]");
            for (Player player : d_gameStatus.getPlayers()) {
                pr.print(player.getPlayerName()+" ");
                for(Country country: player.getCountries().values()){
                    pr.print(country.getID()+" "+country.getNumberOfArmies()+" ");
                }
                pr.print("\n");
            }

            pr.println("\n[cards]");
            for (Player player : d_gameStatus.getPlayers()) {
                pr.print(player.getPlayerName()+" ");
                for(Orders order : player.getOwnedCards()){
                    pr.print(String.valueOf(order)+" ");
                }
                pr.println();
            }

//                System.out.print(player.getPlayerName() +
//                        ", Number of armies left for reinforcement phase->" + player.getOwnedArmies() +
//                        "\n\tcards ->\n");
//
//                for (Orders order : player.getOwnedCards()) {
//                    System.out.print(String.valueOf(order) + ", ");
//                }
//

            pr.close();
            br.close();
            l_fileWriter.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
