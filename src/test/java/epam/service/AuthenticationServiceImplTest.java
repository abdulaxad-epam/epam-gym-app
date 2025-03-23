package epam.service;

import epam.shared.security.dto.AuthenticateRequestDTO;
import epam.shared.security.dto.RegisterTraineeRequestDTO;
import epam.shared.security.dto.RegisterTrainerRequestDTO;
import epam.shared.security.service.impl.AuthenticationServiceImpl;
import epam.trainee.service.TraineeService;
import epam.trainer.service.TrainerService;
import epam.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthenticationServiceImplTest {

    private UserService userService;
    private TrainerService trainerService;
    private TraineeService traineeService;
    private AuthenticationServiceImpl authenticationService;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        trainerService = mock(TrainerService.class);
        traineeService = mock(TraineeService.class);
        authenticationService = new AuthenticationServiceImpl(userService, trainerService, traineeService);
    }

    @Test
    void testRegisterTrainee_Success() {
        RegisterTraineeRequestDTO requestDTO = RegisterTraineeRequestDTO.builder()
                .dateOfBirth(null)
                .address("123 Street")
                .user(UserRequestDTO.builder().build())
                .build();

        when(traineeService.createTrainee(any())).thenReturn(TraineeResponseDTO.builder().build());

        assertTrue(authenticationService.register(requestDTO));
    }

    @Test
    void testRegisterTrainer_Success() {
        RegisterTrainerRequestDTO requestDTO = RegisterTrainerRequestDTO.builder()
                .specialization("Fitness")
                .user(UserRequestDTO.builder().build())
                .build();

        when(trainerService.createTrainer(any())).thenReturn(TrainerResponseDTO.builder().build());

        assertTrue(authenticationService.register(requestDTO));
    }

    @Test
    void testRegisterTrainee_Failure() {
        RegisterTraineeRequestDTO requestDTO = RegisterTraineeRequestDTO.builder()
                .dateOfBirth(null)
                .address("123 Street")
                .user(UserRequestDTO.builder().build())
                .build();

        when(traineeService.createTrainee(any())).thenReturn(null);

        assertFalse(authenticationService.register(requestDTO));
    }

    @Test
    void testAuthenticate_Success() {
        AuthenticateRequestDTO authRequest = AuthenticateRequestDTO.builder()
                .username("testUser")
                .password("password123")
                .build();

        when(userService.existsByUsernameAndPassword("testUser", "password123")).thenReturn(true);

        assertTrue(authenticationService.authenticate(authRequest));
    }

    @Test
    void testAuthenticate_Failure() {
        AuthenticateRequestDTO authRequest = AuthenticateRequestDTO.builder()
                .username("wrongUser")
                .password("wrongPass")
                .build();

        when(userService.existsByUsernameAndPassword("wrongUser", "wrongPass")).thenReturn(false);

        assertFalse(authenticationService.authenticate(authRequest));
    }

    @Test
    void testAuthenticate_NullValues() {
        AuthenticateRequestDTO authRequest = AuthenticateRequestDTO.builder()
                .username(null)
                .password(null)
                .build();

        assertFalse(authenticationService.authenticate(authRequest));
    }
}
