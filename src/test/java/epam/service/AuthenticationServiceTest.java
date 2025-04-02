package epam.service;

import epam.dto.request_dto.AuthenticateRequestDTO;
import epam.dto.request_dto.ChangePasswordRequestDTO;
import epam.dto.request_dto.RegisterTraineeRequestDTO;
import epam.dto.request_dto.RegisterTrainerRequestDTO;
import epam.dto.response_dto.RegisterTraineeResponseDTO;
import epam.dto.response_dto.RegisterTrainerResponseDTO;
import epam.exception.exception.UserNotFoundException;
import epam.service.impl.AuthenticationServiceImpl;
import epam.dto.request_dto.UserRequestDTO;
import epam.dto.response_dto.UserResponseDTO;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private TrainerService trainerService;

    @Mock
    private TraineeService traineeService;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    private MockHttpServletResponse mockResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockResponse = new MockHttpServletResponse();
    }

    @Test
    void testRegisterTrainee_Success() {
        RegisterTraineeRequestDTO requestDTO = RegisterTraineeRequestDTO.builder()
                .dateOfBirth(LocalDate.of(2000, 1, 1))
                .address("123 Street")
                .user(new UserRequestDTO("John", "Doe", true))
                .build();

        RegisterTraineeResponseDTO responseDTO = new RegisterTraineeResponseDTO(
                new UserResponseDTO("John", "Doe", "johndoe", true),
                "password123",
                "2000-01-01",
                "123 Street"
        );

        when(traineeService.createTrainee(any())).thenReturn(responseDTO);

        RegisterTraineeResponseDTO result = authenticationService.register(requestDTO, mockResponse);

        assertNotNull(result);
        assertEquals("johndoe", result.getUser().getUsername());

        Cookie authCookie = mockResponse.getCookie("__auth");
        assertNotNull(authCookie);
        assertTrue(authCookie.getValue().contains("johndoe"));
    }

    @Test
    void testRegisterTrainer_Success() {
        RegisterTrainerRequestDTO requestDTO = RegisterTrainerRequestDTO.builder()
                .specialization("Fitness")
                .user(new UserRequestDTO("Jane", "Doe", true))
                .build();

        RegisterTrainerResponseDTO responseDTO = new RegisterTrainerResponseDTO(
                new UserResponseDTO("Jane", "Doe", "janedoe", true),
                "password123",
                "Fitness"
        );

        when(trainerService.createTrainer(any())).thenReturn(responseDTO);

        RegisterTrainerResponseDTO result = authenticationService.register(requestDTO, mockResponse);

        assertNotNull(result);
        assertEquals("janedoe", result.getUser().getUsername());

        Cookie authCookie = mockResponse.getCookie("__auth");
        assertNotNull(authCookie);
        assertTrue(authCookie.getValue().contains("janedoe"));
    }

    @Test
    void testAuthenticate_Success() {
        AuthenticateRequestDTO requestDTO = new AuthenticateRequestDTO("johndoe", "password123");

        when(userService.existsByUsernameAndPassword("johndoe", "password123")).thenReturn(true);

        boolean isAuthenticated = authenticationService.authenticate(requestDTO, mockResponse);

        assertTrue(isAuthenticated);

        Cookie authCookie = mockResponse.getCookie("__auth");
        assertNotNull(authCookie);
        assertTrue(authCookie.getValue().contains("johndoe"));
    }

    @Test
    void testAuthenticate_Failure() {
        AuthenticateRequestDTO requestDTO = new AuthenticateRequestDTO("johndoe", "wrongpassword");

        when(userService.existsByUsernameAndPassword("johndoe", "wrongpassword")).thenReturn(false);

        assertThrows(UserNotFoundException.class, () ->
                authenticationService.authenticate(requestDTO, mockResponse));
    }

    @Test
    void testChangePassword_Success() {
        ChangePasswordRequestDTO requestDTO = new ChangePasswordRequestDTO("johndoe", "oldpass123", "newpass456");

        when(userService.changePassword(requestDTO)).thenReturn(true);

        boolean isPasswordChanged = authenticationService.changePassword(requestDTO, mockResponse);

        assertTrue(isPasswordChanged);

        Cookie authCookie = mockResponse.getCookie("__auth");
        assertNotNull(authCookie);
        assertTrue(authCookie.getValue().contains("newpass456"));
    }

    @Test
    void testChangePassword_Failure() {
        ChangePasswordRequestDTO requestDTO = new ChangePasswordRequestDTO("johndoe", "oldpass123", "newpass456");

        when(userService.changePassword(requestDTO)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () ->
                authenticationService.changePassword(requestDTO, mockResponse));
    }

    @Test
    void testValidateToken_Success() {
        when(userService.existsByUsernameAndPassword("johndoe", "password123")).thenReturn(true);

        boolean isValid = authenticationService.validateToken("johndoe:" + UUID.randomUUID() + ":password123");

        assertTrue(isValid);
    }

    @Test
    void testValidateToken_InvalidFormat() {
        boolean isValid = authenticationService.validateToken("invalidToken");

        assertFalse(isValid);
    }
}
