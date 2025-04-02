package epam.controller;

import epam.dto.response_dto.TraineeResponseDTO;
import epam.service.TraineeService;
import epam.dto.response_dto.TrainingResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TraineeControllerTest {

    @Mock
    private TraineeService traineeService;

    @InjectMocks
    private TraineeController traineeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTraineeByUsername() {
        // Arrange
        String username = "testUser";
        TraineeResponseDTO mockResponse = new TraineeResponseDTO();
        when(traineeService.getTraineeByUsername(username)).thenReturn(mockResponse);

        // Act
        ResponseEntity<TraineeResponseDTO> response = traineeController.getTraineeByUsername(username);

        // Assert
        assertNotNull(response);
        assertEquals(mockResponse, response.getBody());
        verify(traineeService, times(1)).getTraineeByUsername(username);
    }

    @Test
    void testGetTrainingByUsername() {
        // Arrange
        String username = "testUser";
        List<TrainingResponseDTO> mockTrainings = Arrays.asList(new TrainingResponseDTO(), new TrainingResponseDTO());
        when(traineeService.getTraineeTrainings(username, null, null, null, null)).thenReturn(mockTrainings);

        // Act
        ResponseEntity<List<TrainingResponseDTO>> response = traineeController.getTrainingByUsername(username, null, null, null, null);

        // Assert
        assertNotNull(response);
        assertEquals(mockTrainings, response.getBody());
        verify(traineeService, times(1)).getTraineeTrainings(username, null, null, null, null);
    }

    @Test
    void testUpdate() {
        // Arrange
        String username = "testUser";
        String firstname = "John";
        String lastname = "Doe";
        String dateOfBirth = "1990-01-01";
        String address = "123 Main St";
        Boolean isActive = true;

        TraineeResponseDTO mockResponse = new TraineeResponseDTO();
        when(traineeService.updateTrainee(username, firstname, lastname, dateOfBirth, address, isActive)).thenReturn(mockResponse);

        // Act
        ResponseEntity<TraineeResponseDTO> response = traineeController.update(username, firstname, lastname, dateOfBirth, address, isActive);

        // Assert
        assertNotNull(response);
        assertEquals(mockResponse, response.getBody());
        verify(traineeService, times(1)).updateTrainee(username, firstname, lastname, dateOfBirth, address, isActive);
    }

    @Test
    void testDelete() {
        // Arrange
        String username = "testUser";
        doNothing().when(traineeService).deleteTrainee(username);

        // Act
        ResponseEntity<Void> response = traineeController.delete(username);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        verify(traineeService, times(1)).deleteTrainee(username);
    }

    @Test
    void testUpdateTraineeStatus() {
        // Arrange
        String username = "testUser";
        Boolean isActive = false;
        doNothing().when(traineeService).updateTraineeStatus(username, isActive);

        // Act
        ResponseEntity<Void> response = traineeController.updateTraineeStatus(username, isActive);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        verify(traineeService, times(1)).updateTraineeStatus(username, isActive);
    }
}
