package epam.mapper;

import epam.entity.Trainee;
import epam.entity.Trainer;
import epam.entity.Training;
import epam.entity.TrainingType;
import epam.request_dto.TrainingRequestDTO;
import epam.response_dto.TrainingResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

class TrainingMapperTest {

    private TrainingMapper trainingMapper;
    private TrainerMapper trainerMapper;
    private TraineeMapper traineeMapper;

    @BeforeEach
    void setUp() {
        trainerMapper = mock(TrainerMapper.class);
        traineeMapper = mock(TraineeMapper.class);
        trainingMapper = new TrainingMapper(trainerMapper, traineeMapper);
    }

    @Test
    void testToTrainingResponseDTO_Success() {
        Training training = new Training();
        training.setTrainingName("HYPERTROPHY_TRAINING");
        training.setTrainingDate(LocalDateTime.now());



        TrainingType trainingType = new TrainingType();
        trainingType.setDescription("FUNCTIONAL_TRAINING");
        training.setTrainingType(trainingType);

        when(traineeMapper.toTraineeResponseDTO(any())).thenReturn(null);
        when(trainerMapper.toTrainerResponseDTO(any())).thenReturn(null);

        TrainingResponseDTO responseDTO = trainingMapper.toTrainingResponseDTO(training);

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
        assertNull(result);
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
