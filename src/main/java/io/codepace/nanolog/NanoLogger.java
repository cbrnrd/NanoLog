package io.codepace.nanolog;

import java.io.*;
import java.nio.channels.FileChannel;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static io.codepace.nanolog.Helpers.*;

/**
 * The main class to instantiate for logging
 */
public class NanoLogger {


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
    boolean isClosed = false;
    boolean firstProgramWrite = true;



    /**
     * Get the current date and time
     * @return The current date/time
     */
    public String getCurrentDate(){
        return CURRENT_DATE;
    }

    /**
     * Instantiates a new <code>NanoLogger</code> object and creates the logfile at <code>filepath</code>
     * @param filepath the path to save the log at
     */
    public NanoLogger(String filepath){
        logfile = new File(filepath);

        try {
            if (!logfile.exists()){
                logfile.createNewFile();
            }

        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    /**
     * Instantiates a new <code>NanoLogger</code> object and creates the logfile at <code>filepath</code>
     * @param filepath the path to save the log at
     * @param truncate whether or not to truncate the file if it already exists
     */
    public NanoLogger(String filepath, boolean truncate){
        logfile = new File(filepath);

        try {
            if (!logfile.exists()){
                logfile.createNewFile();
            } else {
                // Truncate file to 0
                FileChannel out = new FileOutputStream(logfile, true).getChannel();
                out.truncate(0);
                out.close();
            }
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
     * Gets the most recent message that was logged
     * @return the most recent logged message
     */
    public String getLastLog(){
        return lastLog;
    }

    /**
     * A general function to log to the log file
     * @param message the message to log
     * @param logType the type of message the message is
     * @throws ClosedLoggerError if the logger is already closed
     */
    public void log(String message, LogType logType) {
        if (logfile == null) throw new UninitializedLoggerError("Unable to log without initializing the `NanoLogger`.");
        if (isClosed) throw new ClosedLoggerError();
        try(
                FileWriter fw = new FileWriter(logfile, true);
                BufferedWriter out = new BufferedWriter(fw)){
            if (firstProgramWrite) out.write("");
            out.write(timestamp.toString() + " " + determineLogType(logType) + message + LINE_SEPERATOR);
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    /*
    /**
     * Logs the message with the '{}' replaced by each <code>subs</code> block respectively
     * <p>
     *     <b>Note: This is experimental, do not expect perfect results with this</b>
     *     <b>Also note: this <i>will not</i> replace '{}' that are not between spaces</b>
     * </p>
     * @param message The message to log
     * @param type The type of log the message is
     * @param subs The replacement strings
    public void log(String message, LogType type, String... subs){

        StringBuilder builder = new StringBuilder();
        String[] split = message.split(" ");

        for (int i = 0; i < split.length; i++){

            int subsCounter = 0;
            String s = split[i];
            if (!s.equals("{}")){
                builder.append(s);
            } else {
                builder.append(subs[subsCounter]);
                ++subsCounter;
            }
        }

        log(builder.toString(), type);

    }
    */


    private String determineLogType(LogType type){
        if (type == LogType.DEBUG) return DEBUG_LOG;
        else if (type == LogType.ERROR) return ERROR_LOG;
        else if (type == LogType.INFO) return INFO_LOG;
        else if (type == LogType.SUCCESS) return SUCCESS_LOG;
        else if (type == LogType.FATAL) return FATAL_LOG;
        else if (type == LogType.TRACE) return TRACE_LOG;
        else if (type == LogType.NONE) return NONE_LOG;
        else return "";
    }

    /**
     * Log an 'info' message
     * @param message the message to log
     */
    public void info(String message){
        lastLog = message;
        log(message, LogType.INFO);
    }

    /**
     * Log a 'success' message
     * @param message the message to log
     */
    public void success(String message){
        lastLog = message;
        log(message, LogType.SUCCESS);
    }

    /**
     * Log an 'error' message
     * @param message the message the log
     */
    public void error(String message){
        lastLog = message;
        log(message, LogType.ERROR);

    }


    /**
     * Logs the stack trace of a {@link Throwable}. The message of the exception will be included in the log
     * @param error the throwable to log
     */
    public void stacktrace(Throwable error){
        log("Exception occurred: " + error.getMessage() + "\n", LogType.TRACE);
        log(stackTraceToString(error), null);
    }

    /**
     * Log a 'debug' message
     * @param message the message to write
     */
    public void debug(String message){
        lastLog = message;
        log(message, LogType.DEBUG);
    }

    /**
     * Log a 'fatal' message
     * @param message the message to log
     */
    public void fatal(String message){
        lastLog = message;
        log(message, LogType.DEBUG);
    }

    /**
     * Log a 'debug' message
     *
     * @param message the message to write
     */
    public void none(String message){
        lastLog = message;
        log(message, LogType.NONE);
    }

}
