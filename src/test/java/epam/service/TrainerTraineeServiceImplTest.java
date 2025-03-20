package epam.service;

import epam.entity.Trainee;
import epam.entity.Trainer;
import epam.exception.TraineeHasNotAssignedBeforeException;
import epam.exception.TraineeNotFoundException;
import epam.exception.TrainerNotFoundException;
import epam.repository.TraineeRepository;
import epam.repository.TrainerRepository;
import epam.service.impl.TrainerTraineeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TrainerTraineeServiceImplTest {

    private TraineeRepository traineeRepository;
    private TrainerRepository trainerRepository;
    private TraineeTrainerService traineeTrainerService;

    @BeforeEach
    void setUp() {
        traineeRepository = mock(TraineeRepository.class);
        trainerRepository = mock(TrainerRepository.class);
        traineeTrainerService = new TrainerTraineeServiceImpl(traineeRepository, trainerRepository);
    }

    @Test
    void testAddTrainerToTrainee_Success() {
        String traineeUsername = "traineeUser";
        String trainerUsername = "trainerUser";

        Trainee trainee = new Trainee();
        trainee.setTraineeId(UUID.randomUUID());
        Trainer trainer = new Trainer();
        trainer.setTrainerId(UUID.randomUUID());

        when(traineeRepository.findByUsername(traineeUsername)).thenReturn(Optional.of(trainee));
        when(trainerRepository.findByUsername(trainerUsername)).thenReturn(Optional.of(trainer));
        when(trainerRepository.trainerHasTrainee(trainer.getTrainerId(), trainee.getTraineeId())).thenReturn(true);
        doNothing().when(trainerRepository).addTrainerToTrainee(trainee, trainer);

        Boolean result = traineeTrainerService.addTrainerToTrainee(traineeUsername, trainerUsername);

        assertTrue(result);
        verify(trainerRepository, times(1)).addTrainerToTrainee(trainee, trainer);
    }

    @Test
    void testAddTrainerToTrainee_TraineeNotFound() {
        String traineeUsername = "unknownTrainee";
        String trainerUsername = "trainerUser";

        when(traineeRepository.findByUsername(traineeUsername)).thenReturn(Optional.empty());

        assertThrows(TraineeNotFoundException.class, () -> traineeTrainerService.addTrainerToTrainee(traineeUsername, trainerUsername));
    }

    @Test
    void testAddTrainerToTrainee_TrainerNotFound() {
        String traineeUsername = "traineeUser";
        String trainerUsername = "unknownTrainer";

        Trainee trainee = new Trainee();
        trainee.setTraineeId(UUID.randomUUID());

        when(traineeRepository.findByUsername(traineeUsername)).thenReturn(Optional.of(trainee));
        when(trainerRepository.findByUsername(trainerUsername)).thenReturn(Optional.empty());

        assertThrows(TrainerNotFoundException.class, () -> traineeTrainerService.addTrainerToTrainee(traineeUsername, trainerUsername));
    }

    @Test
    void testRemoveTrainerFromTrainee_Success() {
        String traineeUsername = "traineeUser";
        String trainerUsername = "trainerUser";

        Trainee trainee = new Trainee();
        trainee.setTraineeId(UUID.randomUUID());
        Trainer trainer = new Trainer();
        trainer.setTrainerId(UUID.randomUUID());

        when(traineeRepository.findByUsername(traineeUsername)).thenReturn(Optional.of(trainee));
        when(trainerRepository.findByUsername(trainerUsername)).thenReturn(Optional.of(trainer));
        when(trainerRepository.trainerHasTrainee(trainer.getTrainerId(), trainee.getTraineeId())).thenReturn(true);
        doNothing().when(trainerRepository).removeTraineeOfTrainer(trainee, trainer);

        Boolean result = traineeTrainerService.removeTrainerFromTrainee(traineeUsername, trainerUsername);

        assertTrue(result);
        verify(trainerRepository, times(1)).removeTraineeOfTrainer(trainee, trainer);
    }

    @Test
    void testRemoveTrainerFromTrainee_NotAssigned() {
        String traineeUsername = "traineeUser";
        String trainerUsername = "trainerUser";

        Trainee trainee = new Trainee();
        trainee.setTraineeId(UUID.randomUUID());
        Trainer trainer = new Trainer();
        trainer.setTrainerId(UUID.randomUUID());

        when(traineeRepository.findByUsername(traineeUsername)).thenReturn(Optional.of(trainee));
        when(trainerRepository.findByUsername(trainerUsername)).thenReturn(Optional.of(trainer));
        when(trainerRepository.trainerHasTrainee(trainer.getTrainerId(), trainee.getTraineeId())).thenReturn(false);

        assertThrows(TraineeHasNotAssignedBeforeException.class, () -> traineeTrainerService.removeTrainerFromTrainee(traineeUsername, trainerUsername));
    }
}
