package epam.training;

import epam.shared.exception.exception.TraineeNotFoundException;
import epam.shared.exception.exception.TrainerNotFoundException;
import epam.shared.trainee_trainer.service.TraineeTrainerService;
import epam.trainee.entity.Trainee;
import epam.trainee.repository.TraineeRepository;
import epam.trainer.entity.Trainer;
import epam.trainer.repository.TrainerRepository;
import epam.training.dto.TrainingRequestDTO;
import epam.training.dto.TrainingResponseDTO;
import epam.training.entity.Training;
import epam.training.mapper.TrainingMapper;
import epam.training.repository.TrainingRepository;
import epam.training.service.impl.TrainingServiceImpl;
import epam.training_type.service.TrainingTypeService;
import org.instancio.Instancio;
import org.instancio.junit.InstancioExtension;
import org.instancio.settings.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static epam.trainer.TrainerControllerTest.settings;
import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class, InstancioExtension.class})
public class TrainingServiceTest {

    @Mock
    private TrainingRepository trainingRepository;

    @Mock
    private TrainingMapper trainingMapper;

    @Mock
    private TrainingTypeService trainingTypeService;

    @Mock
    private TraineeRepository traineeRepository;

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private TraineeTrainerService traineeTrainerService;

    @InjectMocks
    private TrainingServiceImpl trainingService;

    private TrainingRequestDTO trainingRequestDTO;
    private Training training;
    private TrainingResponseDTO trainingResponseDTO;

    @BeforeEach
    void setUp() {
        trainingRequestDTO = new TrainingRequestDTO("testTrainer", "testTrainee", "Yoga", LocalDate.now(), "FLEXIBILITY", 180);
        training = new Training();
        trainingResponseDTO = new TrainingResponseDTO();
    }

    @Test
    void testCreateTraining_Success() {
        when(trainingTypeService.getTrainingByTrainingName(anyString())).thenReturn(null);
        when(trainerRepository.findByUsername(anyString())).thenReturn(Optional.of(new Trainer()));
        when(traineeRepository.findByUsername(anyString())).thenReturn(Optional.of(new Trainee()));
        when(trainingMapper.toTraining(any(), any(), any(), any())).thenReturn(training);
        when(trainingMapper.toTrainingResponseDTO(any())).thenReturn(trainingResponseDTO);

        TrainingResponseDTO result = trainingService.createTraining(trainingRequestDTO);

        assertNotNull(result);
        verify(traineeTrainerService).assignTrainerToTrainee(anyString(), anyString());
        verify(trainingRepository).insert(any());
    }

    @Test
    void testCreateTraining_TrainerNotFound() {
        when(trainerRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        assertThrows(TrainerNotFoundException.class, () -> trainingService.createTraining(trainingRequestDTO));
    }

    @Test
    void testCreateTraining_TraineeNotFound() {
        when(trainerRepository.findByUsername(anyString())).thenReturn(Optional.of(new Trainer()));
        when(traineeRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        assertThrows(TraineeNotFoundException.class, () -> trainingService.createTraining(trainingRequestDTO));
    }

    @Test
    void testDeleteTraining_Success() {
        when(trainingRepository.getIdByUsername(anyString())).thenReturn(Optional.of(mock(UUID.class)));

        trainingService.deleteTraining("testUser");

        verify(trainingRepository).delete(any());
    }
}