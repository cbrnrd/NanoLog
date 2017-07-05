package io.codepace.logging;

/**
 * This should only be used when the user is trying to log without initializing the file
 */
public class UninitializedLoggerError extends RuntimeException {

    public UninitializedLoggerError(){ }

    public UninitializedLoggerError(String message){
        super(message);
    }

    public UninitializedLoggerError(String message, Throwable e){
        super(message, e);
    }

}
