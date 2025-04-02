package epam.aop;

import epam.exception.exception.UserNotAuthenticated;
import epam.service.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class AuthenticationAspectTest {
    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private JoinPoint joinPoint;

    private AuthenticationAspect authenticationAspect;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authenticationAspect = new AuthenticationAspect(authenticationService, request);
    }

    @Test
    void testCheckAuthentication_WithValidToken() throws UserNotAuthenticated {
        Cookie authCookie = new Cookie("__auth", "validToken");
        when(request.getCookies()).thenReturn(new Cookie[]{authCookie});
        when(authenticationService.validateToken("validToken")).thenReturn(true);

        assertDoesNotThrow(() -> authenticationAspect.checkAuthentication());
    }

    @Test
    void testCheckAuthentication_WithNoCookies_ThrowsException() {
        when(request.getCookies()).thenReturn(null);

        UserNotAuthenticated exception = assertThrows(UserNotAuthenticated.class, () -> authenticationAspect.checkAuthentication());

        assertEquals("Unauthorized Access", exception.getMessage());
    }

    @Test
    void testCheckAuthentication_WithInvalidToken_ThrowsException() {
        Cookie authCookie = new Cookie("__auth", "invalidToken");
        when(request.getCookies()).thenReturn(new Cookie[]{authCookie});
        when(authenticationService.validateToken("invalidToken")).thenReturn(false);

        UserNotAuthenticated exception = assertThrows(UserNotAuthenticated.class, () -> authenticationAspect.checkAuthentication());

        assertEquals("Unauthorized Access", exception.getMessage());
    }

    @Test
    void testCheckAuthentication_WithNoAuthCookie_ThrowsException() {
        Cookie someOtherCookie = new Cookie("random", "value");
        when(request.getCookies()).thenReturn(new Cookie[]{someOtherCookie});

        UserNotAuthenticated exception = assertThrows(UserNotAuthenticated.class, () -> authenticationAspect.checkAuthentication());

        assertEquals("Unauthorized Access", exception.getMessage());
    }

}
