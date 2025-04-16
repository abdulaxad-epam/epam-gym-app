package epam.controller;

import epam.dto.request_dto.TrainingRequestDTO;
import epam.dto.response_dto.TrainingResponseDTO;
import epam.service.TrainingService;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static epam.dto.request_dto.RegisterTraineeRequestDTOTest.settings;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TrainingControllerTest {

    @Mock
    private TrainingService trainingService;

    @InjectMocks
    private TrainingController trainingController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTraining_ShouldReturnCreatedTraining() {
        // Given
        TrainingRequestDTO trainingRequestDTO = Instancio.of(TrainingRequestDTO.class).withSettings(settings).create();
        TrainingResponseDTO expectedResponse = Instancio.of(TrainingResponseDTO.class).withSettings(settings).create();

        // When
        when(trainingService.createTraining(trainingRequestDTO)).thenReturn(expectedResponse);
        ResponseEntity<TrainingResponseDTO> response = trainingController.createTraining(trainingRequestDTO);

        // Then
        assertEquals(200,  response.getStatusCode().value());
        assertEquals(expectedResponse, response.getBody());
    }
}
