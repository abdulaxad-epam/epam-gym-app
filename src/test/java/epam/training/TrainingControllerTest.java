package epam.training;

import epam.training.controller.TrainingController;
import epam.training.dto.TrainingRequestDTO;
import epam.training.dto.TrainingResponseDTO;
import epam.training.service.TrainingService;
import org.instancio.Instancio;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrainingControllerTest {

    @Mock
    private TrainingService trainingService;

    @InjectMocks
    private TrainingController trainingController;

    private TrainingResponseDTO trainingResponseDTO;
    private TrainingRequestDTO trainingRequestDTO;

    @WithSettings
    public static final Settings settings = Settings.create()
            .set(Keys.STRING_MIN_LENGTH, 4).lock();



    @BeforeEach
    void setUp() {
        trainingResponseDTO = Instancio.of(TrainingResponseDTO.class)
                .withSettings(settings)
                .create();

        trainingRequestDTO = new TrainingRequestDTO();
    }


    @Test
    void createTraining_Success() {
        // Arrange
        when(trainingService.createTraining(trainingRequestDTO)).thenReturn(trainingResponseDTO);

        //Act
        ResponseEntity<TrainingResponseDTO> response = trainingController.createTraining(trainingRequestDTO);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(trainingResponseDTO, response.getBody());
    }

}
