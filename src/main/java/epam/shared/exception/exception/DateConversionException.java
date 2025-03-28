package epam.shared.exception.exception;

public class DateConversionException extends RuntimeException {
    public DateConversionException(String message, Throwable eMessage) {
        super(message,eMessage);
    }
    public DateConversionException(String message) {
        super(message);
    }
}
