package epam.service;

import epam.client.dto.TrainingRequestDTO;
import epam.client.dto.TrainingResponseDTO;
import epam.entity.Trainee;
import epam.entity.Trainer;
import epam.entity.Training;
import epam.entity.TrainingType;
import epam.exception.exception.TraineeNotFoundException;
import epam.exception.exception.TrainerNotFoundException;
import epam.exception.exception.TrainingNotFoundException;
import epam.mapper.TrainingMapper;
import epam.repository.TraineeRepository;
import epam.repository.TrainerRepository;
import epam.repository.TrainingRepository;
import epam.service.impl.TrainingServiceImpl;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TrainingServiceTest {

    private TrainingRepository trainingRepository;
    private TrainingMapper trainingMapper;
    private TrainingTypeService trainingTypeService;
    private TraineeRepository traineeRepository;
    private TrainerRepository trainerRepository;
    private TraineeTrainerService traineeTrainerService;
    private Authentication authentication;
    private UserDetails userDetails;

    private TrainingServiceImpl trainingService;

    @BeforeEach
    void setUp() {
        trainingRepository = mock(TrainingRepository.class);
        trainingMapper = mock(TrainingMapper.class);
        trainingTypeService = mock(TrainingTypeService.class);
        traineeRepository = mock(TraineeRepository.class);
        trainerRepository = mock(TrainerRepository.class);
        traineeTrainerService = mock(TraineeTrainerService.class);
        authentication = mock(Authentication.class);
        userDetails = mock(UserDetails.class);

        trainingService = new TrainingServiceImpl(
                trainingRepository,
                trainingMapper,
                trainingTypeService,
                traineeRepository,
                trainerRepository,
                traineeTrainerService
        );
    }

    @Test
    void createTraining_shouldSucceed() {
        TrainingRequestDTO dto = new TrainingRequestDTO();
        dto.setTraineeUsername("trainee1");
        dto.setTrainingType("Yoga");

        Trainer trainer = new Trainer();
        trainer.setTrainerId(UUID.randomUUID());

        Trainee trainee = new Trainee();
        trainee.setTraineeId(UUID.randomUUID());

        TrainingType type = Instancio.create(TrainingType.class);

        Training training = new Training();
        TrainingResponseDTO responseDTO = new TrainingResponseDTO();

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("trainer1");
        when(trainingTypeService.getTrainingByTrainingName("Yoga")).thenReturn(type);
        when(trainerRepository.findTraineeByUser_Username("trainer1")).thenReturn(Optional.of(trainer));
        when(traineeRepository.findTraineeByUser_Username("trainee1")).thenReturn(Optional.of(trainee));
        when(trainingMapper.toTraining(dto, type, trainer, trainee)).thenReturn(training);
        when(trainingMapper.toTrainingResponseDTO(training)).thenReturn(responseDTO);

        TrainingResponseDTO result = trainingService.createTraining(dto, authentication);

        assertNotNull(result);
        verify(trainingRepository).save(training);
        verify(traineeTrainerService).assignTrainerToTrainee("trainee1", "trainer1");
    }

    @Test
    void createTraining_shouldThrowTrainerNotFound() {
        TrainingRequestDTO dto = new TrainingRequestDTO();
        dto.setTraineeUsername("trainee1");
        dto.setTrainingType("Yoga");

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("trainer1");
        when(authentication.getPrincipal()).thenReturn(userDetails);

        when(trainingTypeService.getTrainingByTrainingName("Yoga")).thenReturn(new TrainingType());
        when(trainerRepository.findTraineeByUser_Username("trainer1")).thenReturn(Optional.empty());

        assertThrows(TrainerNotFoundException.class,
                () -> trainingService.createTraining(dto, authentication));
    }


    @Test
    void createTraining_shouldThrowTraineeNotFound() {
        TrainingRequestDTO dto = new TrainingRequestDTO();
        dto.setTraineeUsername("trainee1");
        dto.setTrainingType("Yoga");

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("trainer1");
        when(authentication.getPrincipal()).thenReturn(userDetails);


        Trainer trainer = new Trainer();
        when(trainingTypeService.getTrainingByTrainingName("Yoga")).thenReturn(new TrainingType());
        when(trainerRepository.findTraineeByUser_Username("trainer1")).thenReturn(Optional.of(trainer));
        when(traineeRepository.findTraineeByUser_Username("trainee1")).thenReturn(Optional.empty());

        assertThrows(TraineeNotFoundException.class,
                () -> trainingService.createTraining(dto, authentication));
    }


    @Test
    void deleteTraining_shouldDeleteSuccessfully() {
        UUID trainingId = UUID.randomUUID();

        when(trainingRepository.getIdByUsername("trainee1")).thenReturn(Optional.of(trainingId));

        trainingService.deleteTraining("trainee1");

        verify(trainingRepository).deleteTrainingByTrainingId(trainingId);
    }

    @Test
    void deleteTraining_shouldThrowWhenTrainingNotFound() {
        when(trainingRepository.getIdByUsername("trainee1")).thenReturn(Optional.empty());

        assertThrows(TrainingNotFoundException.class,
                () -> trainingService.deleteTraining("trainee1"));
    }
}
