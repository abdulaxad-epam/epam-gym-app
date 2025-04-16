package epam.security;

import epam.enums.OpenEndpoints;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class SpringSecurityTest {

    @Mock
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @InjectMocks
    private SpringSecurity springSecurity;

    private HttpSecurity httpSecurity;

    @BeforeEach
    void setUp() throws Exception {
        // Initialize HttpSecurity mock to avoid actual web context setup
        httpSecurity = mock(HttpSecurity.class);
    }

    @Test
    void shouldConfigureSecurityFilterChainCorrectly() throws Exception {
        // Act
        SecurityFilterChain securityFilterChain = springSecurity.securityFilterChain(httpSecurity, jwtAuthenticationEntryPoint);

        // Verify that cors, csrf, form login, and logout are disabled
        verify(httpSecurity).cors(AbstractHttpConfigurer::disable);
        verify(httpSecurity).csrf(AbstractHttpConfigurer::disable);
        verify(httpSecurity).formLogin(AbstractHttpConfigurer::disable);
        verify(httpSecurity).logout(AbstractHttpConfigurer::disable);

        // Verify that authentication filter is added
        verify(httpSecurity).addFilterAt(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // Verify that the security session management is stateless
        verify(httpSecurity).sessionManagement(AbstractHttpConfigurer::disable);
        verify(httpSecurity).exceptionHandling(AbstractHttpConfigurer::disable);

        // Check that authorized requests configuration is correct
        verify(httpSecurity).authorizeHttpRequests(any());

        // Ensure that the SecurityFilterChain is built correctly
        verify(httpSecurity).build();
    }

    @Test
    void shouldPermitOpenEndpoints() throws Exception {
        // Act
        springSecurity.securityFilterChain(httpSecurity, jwtAuthenticationEntryPoint);

        // Verify that the OpenEndpoints are permitted (they should not require authentication)
        for (OpenEndpoints openEndpoint : OpenEndpoints.values()) {
            verify(httpSecurity).authorizeHttpRequests(request ->
                    request.requestMatchers(openEndpoint.getUrl()).permitAll()
            );
        }
    }

    @Test
    void shouldRequireAuthenticationForOtherEndpoints() throws Exception {
        // Act
        springSecurity.securityFilterChain(httpSecurity, jwtAuthenticationEntryPoint);

        // Verify that any other request requires authentication
        verify(httpSecurity).authorizeHttpRequests(request ->
                request.anyRequest().authenticated()
        );
    }
}
