package exception;

public class TypeMissMatchException extends RuntimeException {
    public TypeMissMatchException(String message) {
        super(message);
    }

    public TypeMissMatchException(String message, Throwable cause) {
        super(message, cause);
    }
}
