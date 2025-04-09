package epam.service;

import epam.dto.request_dto.TrainingRequestDTO;
import epam.dto.response_dto.TrainingResponseDTO;
import epam.entity.Trainee;
import epam.entity.Trainer;
import epam.entity.Training;
import epam.exception.exception.TraineeNotFoundException;
import epam.exception.exception.TrainerNotFoundException;
import epam.mapper.TrainingMapper;
import epam.repository.TraineeRepository;
import epam.repository.TrainerRepository;
import epam.repository.TrainingRepository;
import epam.service.impl.TrainingServiceImpl;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        when(trainerRepository.findTraineeByUser_Username(anyString())).thenReturn(Optional.of(new Trainer()));
        when(traineeRepository.findTraineeByUser_Username(anyString())).thenReturn(Optional.of(new Trainee()));
        when(trainingMapper.toTraining(any(), any(), any(), any())).thenReturn(training);
        when(trainingMapper.toTrainingResponseDTO(any())).thenReturn(trainingResponseDTO);

        TrainingResponseDTO result = trainingService.createTraining(trainingRequestDTO);

        assertNotNull(result);
        verify(traineeTrainerService).assignTrainerToTrainee(anyString(), anyString());
        verify(trainingRepository).save(any());
    }

    @Test
    void testCreateTraining_TrainerNotFound() {
        when(trainerRepository.findTraineeByUser_Username(anyString())).thenReturn(Optional.empty());

        assertThrows(TrainerNotFoundException.class, () -> trainingService.createTraining(trainingRequestDTO));
    }

    @Test
    void testCreateTraining_TraineeNotFound() {
        when(trainerRepository.findTraineeByUser_Username(anyString())).thenReturn(Optional.of(new Trainer()));
        when(traineeRepository.findTraineeByUser_Username(anyString())).thenReturn(Optional.empty());

        assertThrows(TraineeNotFoundException.class, () -> trainingService.createTraining(trainingRequestDTO));
    }

    @Test
    void testDeleteTraining_Success() {
        UUID trainingId = UUID.randomUUID();
        lenient().when(trainingRepository.getIdByUsername("testUser")).thenReturn(Optional.of(trainingId));

        trainingService.deleteTraining("testUser");

        verify(trainingRepository).deleteTrainingByTrainingId(trainingId);
    }
}