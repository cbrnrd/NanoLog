package io.codepace.nanolog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static io.codepace.nanolog.Helpers.*;

/**
 * This is a static logging class. Even though the class shouldn't be instantiated, you will have to use the {@link Logger#init(String)} method to initialize the log file.
 * <p></p>
 * If {@link Logger#init(String)} isn't called before any logging occurs, a {@link UninitializedLoggerError} will be thrown
 */
public final class Logger {

    // Private constructor
    private Logger() {
    }

    static boolean isInitiated = false;

    private static final Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    // Get the current date
    static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    static Date date = new Date();
    private static final String CURRENT_DATE = dateFormat.format(date);

    // The file to save the log to
    private static File logfile = null;

    // In and out streams
    static FileWriter fw = null;
    static BufferedWriter out = null;

    // For getLastLog()
    static String lastLog = "";
    static boolean isClosed = false;


    /**
     * Initializes a new static {@link Logger} for writing to
     *
     * @param filepath the filepath to save the log file at
     * @return if the logger was successfully initiated or not
     * @throws MultipleLoggersException
     * @throws ClosedLoggerError
     */
    public static boolean init(String filepath) {

        if (isInitiated)
            throw new MultipleLoggersException("Unable to have multiple loggers initiated at the same time");
        if (isClosed) throw new ClosedLoggerError();
        logfile = new File(filepath);

        try {
            if (!logfile.exists()) {
                logfile.createNewFile();
            }

            isInitiated = true;  // Make sure that another logger can't be initiated
            return true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }

    }

    /**
     * Initializes a new static {@link Logger} for writing to
     *
     * @param file the {@link File} to save the log file at
     * @return if the logger was successfully initiated or not
     * @throws MultipleLoggersException
     * @throws ClosedLoggerError
     */
    public static boolean init(File file) {
        if (isInitiated)
            throw new MultipleLoggersException("Unable to have multiple loggers initiated at the same time");
        if (isClosed) throw new ClosedLoggerError();

        logfile = file;

        try {
            if (!logfile.exists()) {
                logfile.createNewFile();
            }

            isInitiated = true;  // Make sure that another logger can't be initiated
            return true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }
    }


    /**
     * Get the current date and time
     *
     * @return The current date/time
     */
    public static String getCurrentDate() {
        return CURRENT_DATE;
    }


    /**
     * gets the absolute path of the log file
     *
     * @return the path of the file
     */
    public static String getLogPath() {
        return logfile.getAbsolutePath();
    }

    /**
     * Gets the most recent message that was logged
     *
     * @return the most recent logged message
     */
    public static String getLastLog() {
        return lastLog;
    }

    /**
     * A general function to log to the log file
     *
     * @param message the message to log
     * @param logType the type of message the message is
     * @throws IOException              if there was an error writing to the log file
     * @throws UninitializedLoggerError
     */
    public static void log(String message, LogType logType) throws IOException {
        if (logfile == null) throw new UninitializedLoggerError("Unable to log without initializing the `Logger`.");

        // Doing it this way will always close the streams after writing
        try (
                FileWriter fw = new FileWriter(logfile, true);
                BufferedWriter out = new BufferedWriter(fw)) {
            out.write(timestamp.toString() + " " + determineLogType(logType) + message + LINE_SEPERATOR);
        }
    }

    /**
     * Uses the {@link Object#toString()} method to log instead of a {@link String}
     *
     * @param object  The object to log
     * @param logType The type of log the message should be
     * @throws IOException
     * @throws UninitializedLoggerError
     */
    public static void log(Object object, LogType logType) throws IOException {
        if (logfile == null) throw new UninitializedLoggerError("Unable to log without initializing the `Logger`.");
        try (
                FileWriter fw = new FileWriter(logfile, true);
                BufferedWriter out = new BufferedWriter(fw)) {
            out.write("\n" + timestamp.toString() + " " + determineLogType(logType) + object.toString() + LINE_SEPERATOR);
        }
    }

    private static String determineLogType(LogType type) {
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
     *
     * @param message the message to log
     */
    public static void info(String message) {
        lastLog = message;
        try {
            log(message, LogType.INFO);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Log a 'success' message
     *
     * @param message the message to log
     */
    public static void success(String message) {
        lastLog = message;
        try {
            log(message, LogType.SUCCESS);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Log an 'error' message
     *
     * @param message the message the log
     */
    public static void error(String message) {
        lastLog = message;
        try {
            log(message, LogType.ERROR);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }


    /**
     * Logs the stack trace of a {@link Throwable}
     *
     * @param error the throwable to log
     */
    public static void stacktrace(Throwable error) {
        try {
            log("Exception occurred: " + error.getMessage() + "\n", LogType.TRACE);
            log(stackTraceToString(error), null);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Log a 'debug' message
     *
     * @param message the message to write
     */
    public static void debug(String message) {
        lastLog = message;
        try {
            log(message, LogType.DEBUG);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Log a 'fatal' message
     *
     * @param message the message to log
     */
    public static void fatal(String message) {
        lastLog = message;
        try {
            log(message, LogType.DEBUG);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Log a message with no category
     * @param message The message to log
     */
    public static void none(String message) {
        lastLog = message;
        try {
            log(message, LogType.NONE);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }


}
