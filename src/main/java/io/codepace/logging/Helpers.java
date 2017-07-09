package io.codepace.logging;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Helpers {

    static String stackTraceToString(Throwable e){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString(); // stack trace as a string
    }

}
