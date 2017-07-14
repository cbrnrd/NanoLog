package io.codepace.nanolog;

/**
 * This exception is thrown when there are more than one static loggers initialized at once.
 */
public class MultipleLoggersException extends RuntimeException{

    public MultipleLoggersException(){ }

    public MultipleLoggersException(String message){
        super(message);
    }

    public MultipleLoggersException(String message, Throwable cause){
        super(message, cause);
    }
}
