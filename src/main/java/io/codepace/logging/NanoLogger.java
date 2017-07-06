package io.codepace.logging;

import java.io.*;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NanoLogger {

    /**
     * Defines the current users home directory
     */
    public static final String HOME_DIR = System.getProperty("user.home");

    // Constants
    private final String INFO_LOG    = "[INFO] ";
    private final String DEBUG_LOG   = "[DEBUG] ";
    private final String ERROR_LOG   = "[ERROR] ";
    private final String SUCCESS_LOG = "[SUCCESS] ";
    private final String FATAL_LOG   = "[FATAL] ";
    private final Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    // Get the current date
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date = new Date();
    private final String CURRENT_DATE = dateFormat.format(date);

    // The file to save the log to
    private File logfile = null;

    // In and out streams
    FileWriter fw = null;
    BufferedWriter out = null;

    // For getLastLog()
    String lastLog = "";



    /**
     * Get the current date and time
     * @return The current date/time
     */
    public String getCurrentDate(){
        return CURRENT_DATE;
    }

    public NanoLogger(String filepath){
        logfile = new File(filepath);
        try {
            fw = new FileWriter(logfile);
            out = new BufferedWriter(fw);
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    /**
     * gets the absolute path of the log file
     * @return the path of the file
     */
    public String getLogPath(){
        return logfile.getAbsolutePath();
    }

    /**
     * A general function to log to the log file
     * @param message the message to log
     * @param logType the type of message the message is
     * @throws IOException if there was an error writing to the log file
     */
    public void log(String message, LogType logType) throws IOException{
        if (logfile == null){
            throw new UninitializedLoggerError("Unable to log without initializing the `NanoLogger`.");
        }
        out.write(timestamp.toString() + " " + determineLogType(logType) + message + "\n");
    }

    private String determineLogType(LogType type){
        if (type == LogType.DEBUG) return DEBUG_LOG;
        else if (type == LogType.ERROR) return ERROR_LOG;
        else if (type == LogType.INFO) return INFO_LOG;
        else if (type == LogType.SUCCESS) return SUCCESS_LOG;
        else if (type == LogType.FATAL) return FATAL_LOG;
        else return "";
    }

    /**
     * Log an 'info' message
     * @param message the message to log
     */
    public void info(String message){
        try{
            log(message, LogType.INFO);
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    /**
     * Log a 'success' message
     * @param message the message to log
     */
    public void success(String message){
        try{
            log(message, LogType.SUCCESS);
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    /**
     * Log an 'error' message
     * @param message the message the log
     */
    public void error(String message){
        try{
            log(message, LogType.ERROR);
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    /**
     * Log a 'debug' message
     * @param message the message to write
     */
    public void debug(String message){
        try{
            log(message, LogType.DEBUG);
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    /**
     * Log a 'fatal' message
     * @param message the message to log
     */
    public void fatal(String message){
        try{
            log(message, LogType.DEBUG);
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

}
