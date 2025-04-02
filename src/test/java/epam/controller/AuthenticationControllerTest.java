package epam.controller;

import epam.exception.exception.TraineeNotFoundException;
import epam.dto.request_dto.AuthenticateRequestDTO;
import epam.dto.request_dto.ChangePasswordRequestDTO;
import epam.dto.request_dto.RegisterTraineeRequestDTO;
import epam.dto.response_dto.RegisterTraineeResponseDTO;
import epam.dto.request_dto.RegisterTrainerRequestDTO;
import epam.dto.response_dto.RegisterTrainerResponseDTO;
import epam.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import org.instancio.Instancio;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(InstancioExtension.class)
class AuthenticationControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTrainer_Success() {
        // Arrange
        RegisterTrainerRequestDTO trainerRequest = Instancio.create(RegisterTrainerRequestDTO.class);
        RegisterTrainerResponseDTO trainerResponse = Instancio.create(RegisterTrainerResponseDTO.class);

        when(authenticationService.register(any(RegisterTrainerRequestDTO.class), any(HttpServletResponse.class)))
                .thenReturn(trainerResponse);

        // Act
        ResponseEntity<RegisterTrainerResponseDTO> responseEntity = authenticationController.createTrainer(trainerRequest, response);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(trainerResponse, responseEntity.getBody());

        verify(authenticationService, times(1)).register(any(RegisterTrainerRequestDTO.class), any(HttpServletResponse.class));
    }

    @Test
    void testCreateTrainee_Success() {
        // Arrange
        RegisterTraineeRequestDTO traineeRequest = Instancio.create(RegisterTraineeRequestDTO.class);
        RegisterTraineeResponseDTO traineeResponse = Instancio.create(RegisterTraineeResponseDTO.class);

        when(authenticationService.register(any(RegisterTraineeRequestDTO.class), any(HttpServletResponse.class)))
                .thenReturn(traineeResponse);

        // Act
        ResponseEntity<RegisterTraineeResponseDTO> responseEntity = authenticationController.createTrainee(traineeRequest, response);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(traineeResponse, responseEntity.getBody());

        verify(authenticationService, times(1)).register(any(RegisterTraineeRequestDTO.class), any(HttpServletResponse.class));
    }

    @Test
    void testAuthenticateTrainee_Success() throws TraineeNotFoundException {

        AuthenticateRequestDTO authenticateRequest = Instancio.create(AuthenticateRequestDTO.class);

        when(authenticationService.authenticate(any(AuthenticateRequestDTO.class), any(HttpServletResponse.class))).thenReturn(true);

        ResponseEntity<Void> responseEntity = authenticationController.authenticateTrainee(authenticateRequest, response);

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCode().value());

        verify(authenticationService, times(1)).authenticate(any(AuthenticateRequestDTO.class), any(HttpServletResponse.class));
    }

    @Test
    void testChangePassword_Success() throws TraineeNotFoundException {
        ChangePasswordRequestDTO changePasswordRequest = Instancio.create(ChangePasswordRequestDTO.class);

        when(authenticationService.changePassword(any(ChangePasswordRequestDTO.class), any(HttpServletResponse.class))).thenReturn(true);

        ResponseEntity<Void> responseEntity = authenticationController.changePassword(changePasswordRequest, response);

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCode().value());

        verify(authenticationService, times(1)).changePassword(any(ChangePasswordRequestDTO.class), any(HttpServletResponse.class));
    }

    @Test
    void testAuthenticateTrainee_Failure() throws TraineeNotFoundException {
        AuthenticateRequestDTO authenticateRequest = Instancio.create(AuthenticateRequestDTO.class);

        doThrow(new TraineeNotFoundException("Trainee not found"))
                .when(authenticationService).authenticate(any(AuthenticateRequestDTO.class), any(HttpServletResponse.class));

        TraineeNotFoundException exception = assertThrows(
                TraineeNotFoundException.class,
                () -> authenticationController.authenticateTrainee(authenticateRequest, response)
        );

        assertEquals("Trainee not found", exception.getMessage());

        verify(authenticationService, times(1)).authenticate(any(AuthenticateRequestDTO.class), any(HttpServletResponse.class));
    }
}
