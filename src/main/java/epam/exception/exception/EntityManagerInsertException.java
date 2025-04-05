package epam.exception.exception;

public class EntityManagerInsertException extends GymBaseException {
    public EntityManagerInsertException(String message) {
        super(message, "INSERT_FAILED");
    }
}
