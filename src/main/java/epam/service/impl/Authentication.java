package epam.service.impl;


import epam.exception.exception.UserNotAuthenticated;
import epam.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class Authentication {

    private final AuthenticationService authenticationService;

    public Authentication(AuthenticationService authenticationService, HttpServletRequest request) {
        this.authenticationService = authenticationService;
    }

    public void checkAuthentication(String username, String password) throws UserNotAuthenticated {

        if (!authenticationService.validateToken(username, password)) {
            throw new UserNotAuthenticated("Unauthorized Access");
        }
    }

}