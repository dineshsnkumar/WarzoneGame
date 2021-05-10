package ca.concordia.view;

import ca.concordia.controller.GameController;
import ca.concordia.controller.GameStatusController;

import java.io.*;
import java.time.LocalTime;

/**
 * This class handles the execution of play view part
 */
public class PlayGame {
	/**
	 * The execution of the project starts here
	 * @param args for main method
	 */
	public static void main(String args[]) {
		File l_startLog = new File("resources/log.txt");
		//create log file
		if(!l_startLog.exists()) {
			try {
				l_startLog.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			//append data to the file writer
			FileWriter l_fileWriter = new FileWriter(l_startLog,true);
			BufferedWriter br = new BufferedWriter(l_fileWriter);
			PrintWriter pr = new PrintWriter(br);
			pr.println(LocalTime.now().toString()+ " new game started");
			pr.close();
			br.close();
			l_fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		GameController gameEngine = new GameController();
		gameEngine.start();
	}
}
