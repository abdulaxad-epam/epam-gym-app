package epam.controller;

import epam.dto.request_dto.UpdateTraineeRequestDTO;
import epam.service.impl.Authentication;
import epam.dto.response_dto.TraineeResponseDTO;
import epam.dto.response_dto.TrainingResponseDTO;
import epam.service.TraineeService;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TraineeControllerTest {

    @Mock
    private TraineeService traineeService;

    @InjectMocks
    private TraineeController traineeController;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTraineeByUsername() {
        // Arrange
        String username = "testUser";
        String password = "password";
        TraineeResponseDTO mockResponse = new TraineeResponseDTO();
        when(traineeService.getTraineeByUsername(username)).thenReturn(mockResponse);

        // Act
        ResponseEntity<TraineeResponseDTO> response = traineeController.getTraineeByUsername(username, password);

        // Assert
        assertNotNull(response);
        assertEquals(mockResponse, response.getBody());
        verify(traineeService, times(1)).getTraineeByUsername(username);
    }

    @Test
    void testGetTrainingByUsername() {
        // Arrange
        String username = "testUser";
        String password = "password";
        List<TrainingResponseDTO> mockTrainings = Arrays.asList(new TrainingResponseDTO(), new TrainingResponseDTO());
        when(traineeService.getTraineeTrainings(username, null, null, null, null)).thenReturn(mockTrainings);

        // Act
        ResponseEntity<List<TrainingResponseDTO>> response = traineeController.getTrainingByUsername(username, null, null, null, null, password);

        // Assert
        assertNotNull(response);
        assertEquals(mockTrainings, response.getBody());
        verify(traineeService, times(1)).getTraineeTrainings(username, null, null, null, null);
    }

    @Test
    void testUpdate() {
        // Arrange
        UpdateTraineeRequestDTO requestDTO = Instancio.of(UpdateTraineeRequestDTO.class).create();
        String username = "testUser";
        String password = "testPassword";


        TraineeResponseDTO mockResponse = mock(TraineeResponseDTO.class);
        when(traineeService.updateTrainee(username,requestDTO)).thenReturn(mockResponse);

        // Act
        ResponseEntity<TraineeResponseDTO> response = traineeController.update(username, requestDTO, password);

        // Assert
        assertNotNull(response);
        assertEquals(mockResponse, response.getBody());
        verify(traineeService, times(1)).updateTrainee(username, requestDTO);
    }

    @Test
    void testDelete() {
        // Arrange
        String username = "testUser";
        String password = "password";
        doNothing().when(traineeService).deleteTrainee(username);

        // Act
        ResponseEntity<Void> response = traineeController.delete(username, password);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        verify(traineeService, times(1)).deleteTrainee(username);
    }

    @Test
    void testUpdateTraineeStatus() {
        // Arrange
        String username = "testUser";
        String password = "password";
        Boolean isActive = false;
        doNothing().when(traineeService).updateTraineeStatus(username, isActive);

        // Act
        ResponseEntity<Void> response = traineeController.updateTraineeStatus(username, isActive, password);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        verify(traineeService, times(1)).updateTraineeStatus(username, isActive);
    }
}
