package exception;

public class NotDefinedException extends RuntimeException {
    public NotDefinedException(String message) {
        super(message);
    }

    public NotDefinedException(String message, Throwable cause) {
        super(message, cause);
    }
}
