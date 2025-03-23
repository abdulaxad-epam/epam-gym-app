package epam.shared.exception.exception;

public class GymBaseException extends RuntimeException {
    private final String errorCode;

    public GymBaseException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public GymBaseException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
