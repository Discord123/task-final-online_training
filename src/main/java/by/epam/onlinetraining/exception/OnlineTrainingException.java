package by.epam.onlinetraining.exception;

public class OnlineTrainingException extends Exception {

    public OnlineTrainingException() {
    }

    public OnlineTrainingException(String message) {
        super(message);
    }

    public OnlineTrainingException(String message, Throwable cause) {
        super(message, cause);
    }

    public OnlineTrainingException(Throwable cause) {
        super(cause);
    }

    public OnlineTrainingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
