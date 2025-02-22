package epam.exception;

public class IllegalArgumentPassedException extends RuntimeException {
    public IllegalArgumentPassedException(String message) {
        super(message);
    }
}
