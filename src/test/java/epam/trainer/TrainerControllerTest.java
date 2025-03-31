package epam.trainer;

import epam.trainer.controller.TrainerController;
import epam.trainer.dto.TrainerRequestDTO;
import epam.trainer.dto.TrainerResponseDTO;
import epam.trainer.service.TrainerService;
import epam.training.dto.TrainingResponseDTO;
import org.instancio.Instancio;
import org.instancio.junit.InstancioExtension;
import org.instancio.junit.WithSettings;
import org.instancio.settings.Keys;
import org.instancio.settings.Settings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, InstancioExtension.class})
public class TrainerControllerTest {

    @Mock
    private TrainerService trainerService;

    @InjectMocks
    private TrainerController trainerController;

    private TrainerResponseDTO trainerResponseDTO;
    private TrainerRequestDTO trainerRequestDTO;

    @WithSettings
    public static final Settings settings = Settings.create()
            .set(Keys.STRING_MIN_LENGTH, 4).lock();

    @BeforeEach
    void setUp() {
        trainerResponseDTO = Instancio.of(TrainerResponseDTO.class)
                .withSettings(settings)
                .set(field(TrainerResponseDTO::getTrainerSpecialization), "trainerSpecialization")
                .create();

        trainerRequestDTO = new TrainerRequestDTO();
    }

    @Test
    void testGetTrainer_Success() {
        // Arrange
        when(trainerService.getTrainerByUsername("testTrainer")).thenReturn(trainerResponseDTO);

        // Act
        ResponseEntity<TrainerResponseDTO> response = trainerController.getTrainer("testTrainer");

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(trainerResponseDTO, response.getBody());
    }

    @Test
    void testUpdateTrainer_Success() {
        // Arrange
        when(trainerService.updateTrainer("testTrainer", trainerRequestDTO)).thenReturn(trainerResponseDTO);

        // Act
        ResponseEntity<TrainerResponseDTO> response = trainerController.updateTrainer("testTrainer", trainerRequestDTO);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(trainerResponseDTO, response.getBody());
    }

    @Test
    void testGetTrainings_Success() {
        // Arrange
        List<TrainingResponseDTO> trainingList = List.of(new TrainingResponseDTO(), new TrainingResponseDTO());
        when(trainerService.getTrainerTrainings("testTrainer", "2024-01-01", "2024-12-31", "testTrainee"))
                .thenReturn(trainingList);

        // Act
        ResponseEntity<List<TrainingResponseDTO>> response = trainerController.getTrainings(
                "testTrainer", "2024-01-01", "2024-12-31", "testTrainee");

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(trainingList, response.getBody());
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
    }

    @Test
    void testTrainerStatus_Success() {
        // Arrange
        doNothing().when(trainerService).updateTrainerStatus("testTrainer", true);

        // Act
        ResponseEntity<Void> response = trainerController.trainerStatus("testTrainer", true);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        verify(trainerService, times(1)).updateTrainerStatus("testTrainer", true);
    }
}
