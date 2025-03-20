package epam.mapper;

import epam.dto.request_dto.TrainingRequestDTO;
import epam.dto.response_dto.TrainingResponseDTO;
import epam.entity.Trainee;
import epam.entity.Trainer;
import epam.entity.Training;
import epam.entity.TrainingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class TrainingMapperTest {
    @Mock
    private TraineeMapper traineeMapper;

    @Mock
    private TrainerMapper trainerMapper;

    @InjectMocks
    private TrainingMapper trainingMapper = Mappers.getMapper(TrainingMapper.class);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testToTrainingResponseDTO_Success() {
        // Arrange
        Training training = new Training();
        training.setTrainingName("HYPERTROPHY_TRAINING");
        training.setTrainingDate(LocalDateTime.now());

        TrainingType trainingType = new TrainingType();
        trainingType.setDescription("FUNCTIONAL_TRAINING");
        training.setTrainingType(trainingType);

        // ✅ Ensure correct use of matchers
        when(traineeMapper.toTraineeResponseDTO(any())).thenReturn(null);
        when(trainerMapper.toTrainerResponseDTO(any())).thenReturn(null);

        // Act
        TrainingResponseDTO responseDTO = trainingMapper.toTrainingResponseDTO(training);

        // Assert
        assertNotNull(responseDTO);
        assertEquals("HYPERTROPHY_TRAINING", responseDTO.getTrainingName());
        assertEquals("FUNCTIONAL_TRAINING", responseDTO.getTrainingType());
    }

    @Test
    void testToTrainingResponseDTO_NullTraining() {
        assertNull(trainingMapper.toTrainingResponseDTO(null));
    }

    @Test
    void testToTraining_NullRequest() {
        Training result = trainingMapper.toTraining(null, new TrainingType(), new Trainer(), new Trainee());
        assertNull(result.getTrainingId());
    }

    @Test
    void testToTraining_Success() {
        TrainingRequestDTO requestDTO = TrainingRequestDTO.builder().build();
        requestDTO.setTrainingName("HYPERTROPHY_TRAINING");
        requestDTO.setTrainingDuration(90);
        requestDTO.setTrainingDate(LocalDateTime.now());

        TrainingType trainingType = new TrainingType();
        trainingType.setDescription("FUNCTIONAL_TRAINING");

        Trainer trainer = new Trainer();
        Trainee trainee = new Trainee();

        Training training = trainingMapper.toTraining(requestDTO, trainingType, trainer, trainee);

        assertNotNull(training);
        assertEquals("HYPERTROPHY_TRAINING", training.getTrainingName());
        assertEquals(90, training.getTrainingDuration());
        assertNotNull(training.getTrainingDate());
        assertEquals(trainingType, training.getTrainingType());
    }
}
