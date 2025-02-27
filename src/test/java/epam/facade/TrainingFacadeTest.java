package epam.facade;

import epam.domain.Trainee;
import epam.domain.Trainer;
import epam.domain.Training;
import epam.service.TraineeService;
import epam.service.TrainerService;
import epam.service.TrainingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TrainingFacadeTest {

    @Mock
    private TrainingService trainingService;

    @Mock
    private TrainerService trainerService;

    @Mock
    private TraineeService traineeService;

    @InjectMocks
    private TrainingFacade trainingFacade;

    private UUID sampleId;
    private Training sampleTraining;
    private Trainer sampleTrainer;
    private Trainee sampleTrainee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleId = UUID.randomUUID();
        sampleTraining = Training.builder()
                .trainingId(sampleId)
                .trainingName("Strength Training")
                .trainingDate("2025-03-10")
                .trainingDuration("60 minutes")
                .build();
        sampleTrainer = Trainer.builder()
                .userId(sampleId)
                .firstname("John")
                .lastname("Doe")
                .specialization("Fitness Coach")
                .build();
        sampleTrainee = Trainee.builder()
                .userId(sampleId)
                .firstname("Alice")
                .lastname("Smith")
                .dateOfBirth("1990-01-01")
                .address("123 Gym Street")
                .build();
    }

    @Test
    void testCreateTraining() {
        when(trainingService.createTraining(sampleId, sampleTraining)).thenReturn(sampleTraining);

        Training result = trainingFacade.createTraining(sampleId, sampleTraining);

        assertNotNull(result);
        assertEquals(sampleTraining, result);
        verify(trainingService, times(1)).createTraining(sampleId, sampleTraining);
    }

    @Test
    void testGetTrainingById() {
        when(trainingService.getTrainingById(sampleId)).thenReturn(sampleTraining);

        Training result = trainingFacade.getTrainingById(sampleId);

        assertNotNull(result);
        assertEquals(sampleTraining, result);
        verify(trainingService, times(1)).getTrainingById(sampleId);
    }

    @Test
    void testGetAllTrainings() {
        when(trainingService.getAllTrainings()).thenReturn(List.of(sampleTraining));

        List<Training> result = trainingFacade.getAllTrainings();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(trainingService, times(1)).getAllTrainings();
    }

    @Test
    void testCreateTrainer() {
        when(trainerService.createTrainer(sampleId, sampleTrainer)).thenReturn(sampleTrainer);

        Trainer result = trainingFacade.createTrainer(sampleId, sampleTrainer);

        assertNotNull(result);
        assertEquals(sampleTrainer, result);
        verify(trainerService, times(1)).createTrainer(sampleId, sampleTrainer);
    }

    @Test
    void testUpdateTrainer() {
        when(trainerService.updateTrainer(sampleId, sampleTrainer)).thenReturn(sampleTrainer);

        Trainer result = trainingFacade.updateTrainer(sampleId, sampleTrainer);

        assertNotNull(result);
        assertEquals(sampleTrainer, result);
        verify(trainerService, times(1)).updateTrainer(sampleId, sampleTrainer);
    }

    @Test
    void testDeleteTrainer() {
        doNothing().when(trainerService).deleteTrainer(sampleId);

        trainingFacade.deleteTrainer(sampleId);

        verify(trainerService, times(1)).deleteTrainer(sampleId);
    }

    @Test
    void testGetTrainerById() {
        when(trainerService.getTrainerById(sampleId)).thenReturn(sampleTrainer);

        Trainer result = trainingFacade.getTrainerById(sampleId);

        assertNotNull(result);
        assertEquals(sampleTrainer, result);
        verify(trainerService, times(1)).getTrainerById(sampleId);
    }

    @Test
    void testGetAllTrainers() {
        when(trainerService.getAllTrainers()).thenReturn(List.of(sampleTrainer));

        List<Trainer> result = trainingFacade.getAllTrainers();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(trainerService, times(1)).getAllTrainers();
    }

    @Test
    void testCreateTrainee() {
        when(traineeService.createTrainee(sampleId, sampleTrainee)).thenReturn(sampleTrainee);

        Trainee result = trainingFacade.createTrainee(sampleId, sampleTrainee);

        assertNotNull(result);
        assertEquals(sampleTrainee, result);
        verify(traineeService, times(1)).createTrainee(sampleId, sampleTrainee);
    }

    @Test
    void testUpdateTrainee() {
        when(traineeService.updateTrainee(sampleId, sampleTrainee)).thenReturn(sampleTrainee);

        Trainee result = trainingFacade.updateTrainee(sampleId, sampleTrainee);

        assertNotNull(result);
        assertEquals(sampleTrainee, result);
        verify(traineeService, times(1)).updateTrainee(sampleId, sampleTrainee);
    }

    @Test
    void testDeleteTrainee() {
        doNothing().when(traineeService).deleteTrainee(sampleId);

        trainingFacade.deleteTrainee(sampleId);

        verify(traineeService, times(1)).deleteTrainee(sampleId);
    }

    @Test
    void testGetTraineeById() {
        when(traineeService.getTraineeById(sampleId)).thenReturn(sampleTrainee);

        Trainee result = trainingFacade.getTraineeById(sampleId);

        assertNotNull(result);
        assertEquals(sampleTrainee, result);
        verify(traineeService, times(1)).getTraineeById(sampleId);
    }

    @Test
    void testGetAllTrainees() {
        when(traineeService.getAllTrainees()).thenReturn(List.of(sampleTrainee));

        List<Trainee> result = trainingFacade.getAllTrainees();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(traineeService, times(1)).getAllTrainees();
    }
}
