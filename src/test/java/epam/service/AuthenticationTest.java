package epam.service;

import epam.exception.exception.UserNotAuthenticated;
import epam.service.impl.Authentication;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AuthenticationTest {

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCheckAuthentication_ValidCredentials() {
        // Arrange
        String username = "user";
        String password = "password";
        when(authenticationService.validateToken(username, password)).thenReturn(true);

        // Act and Assert
        assertDoesNotThrow(() -> authentication.checkAuthentication(username, password));

        // Verify interaction
        verify(authenticationService, times(1)).validateToken(username, password);
    }

    @Test
    void testCheckAuthentication_InvalidCredentials() {
        // Arrange
        String username = "user";
        String password = "wrongPassword";
        when(authenticationService.validateToken(username, password)).thenReturn(false);

        // Act and Assert
        UserNotAuthenticated exception = assertThrows(UserNotAuthenticated.class, () -> authentication.checkAuthentication(username, password));
        assertEquals("Unauthorized Access", exception.getMessage());

        // Verify interaction
        verify(authenticationService, times(1)).validateToken(username, password);
    }
}
