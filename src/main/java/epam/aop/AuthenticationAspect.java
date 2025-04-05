package epam.aop;


import epam.exception.exception.UserNotAuthenticated;
import epam.service.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthenticationAspect {

    private final AuthenticationService authenticationService;
    private final HttpServletRequest request;

    public AuthenticationAspect(AuthenticationService authenticationService, HttpServletRequest request) {
        this.authenticationService = authenticationService;
        this.request = request;
    }

    @Before("@annotation(epam.aop.Authenticated)")
    public void checkAuthentication() throws UserNotAuthenticated {

        System.out.println("Checking authentication");
        String authCookie = getAuthCookie();

        if (authCookie == null || !authenticationService.validateToken(authCookie)) {
            throw new UserNotAuthenticated("Unauthorized Access");
        }
    }

    private String getAuthCookie() {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("__auth".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}