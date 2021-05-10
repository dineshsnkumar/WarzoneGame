package ca.concordia.view;

import ca.concordia.model.LogEntryBuffer;
import ca.concordia.observer.Observable;
import ca.concordia.observer.Observer;
import java.io.*;
import java.time.LocalTime;


/**
 * This class handles print of log messages by implementation of observer which has the information regarding LogEntryBuffer
 * This class gets the notifications from LogEntryBuffer and put them in log file
 * @author Solmaz
 */
public class LogPrintHandler implements Observer {
    File d_logfile;
    LogEntryBuffer d_logEntryBuffer;

    /**
     * This method update and print log messages in the onsole
     * @param p_o this object is passed by the Observable
     */
    @Override
    public void update(Observable p_o) {
        String l_log = p_o.toString();
        try {
            LogFileWriter(l_log);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructor to attach an instance to a LogEntryBuffer object
     *
     * @param p_logEntryBuffer the parameter defines the LogEntryBuffer object which should be observed
     */
    public LogPrintHandler(LogEntryBuffer p_logEntryBuffer) {
        this.d_logEntryBuffer=p_logEntryBuffer;
        this.d_logfile= new File("resources/log.txt");
        if(!d_logfile.exists()) {
            try {
                d_logfile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        p_logEntryBuffer.attach(this);
    }

    /***
     * This method writes the messages into the log file in to the resources path
     * @param p_logContent the content of logFile
     * @throws void
     */
    public void LogFileWriter(String p_logContent) throws IOException {
        try {
            FileWriter l_fileWriter=new FileWriter(d_logfile,true);
            BufferedWriter br = new BufferedWriter(l_fileWriter);
            PrintWriter pr = new PrintWriter(br);
            pr.println(LocalTime.now().toString()+ " " + p_logContent);
            pr.close();
            br.close();
            l_fileWriter.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}


