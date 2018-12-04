package edu.epam.audio.model.exception;

public class LogicLayerException extends Exception {
    public LogicLayerException() {
    }

    public LogicLayerException(String message) {
        super(message);
    }

    public LogicLayerException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogicLayerException(Throwable cause) {
        super(cause);
    }

    public LogicLayerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
