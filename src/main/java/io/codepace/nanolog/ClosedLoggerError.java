package io.codepace.nanolog;

public class ClosedLoggerError extends RuntimeException{

    public ClosedLoggerError(){}

    public ClosedLoggerError(String message){
        super(message);
    }

    public ClosedLoggerError(String message, Throwable cause){
        super(message, cause);
    }
}
