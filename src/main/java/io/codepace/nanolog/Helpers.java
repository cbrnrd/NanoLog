package io.codepace.nanolog;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * This class contains helpful constants and methods that are used throughout the project
 */
public class Helpers {

    static final String TRACE_LOG   = "[TRACE]: ";
    static final String INFO_LOG    = "[INFO]: ";
    static final String DEBUG_LOG   = "[DEBUG]: ";
    static final String ERROR_LOG   = "[ERROR]: ";
    static final String SUCCESS_LOG = "[SUCCESS]: ";
    static final String FATAL_LOG   = "[FATAL]: ";
    static final String NONE_LOG    = "";

    /**
     * The system-specific line separator
     * @see System#lineSeparator()
     */
    public static final String LINE_SEPERATOR = System.lineSeparator();

    /**
     * Defines the current users home directory
     */
    public static final String HOME_DIR = System.getProperty("user.home");
    
    static String stackTraceToString(Throwable e){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString(); // stack trace as a string
    }



}
