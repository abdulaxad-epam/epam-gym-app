package epam.shared.exception.exception;

public class UserNotAuthenticated extends Throwable {
    public UserNotAuthenticated(String unauthorizedAccess) {
        super(unauthorizedAccess);
    }
}
