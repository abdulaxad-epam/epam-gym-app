package epam.service;


import epam.dao.TrainingDAO;
import epam.domain.Training;
import epam.enums.TrainingType;
import epam.exception.TrainingNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrainingServiceTest {

    @Mock
    private TrainingDAO trainingDAO;

    @InjectMocks
    private TrainingServiceImpl trainingService;

    private Training training;

    private Training training2;

    @BeforeEach
    void setUp() {
        training = Training.builder()
                .trainingId(UUID.fromString("770e8403-e29b-41d4-a716-446655440003"))
                .traineeId(UUID.randomUUID().toString())
                .trainingName("Some training")
                .trainingDate(LocalDate.now().toString())
                .trainingType(TrainingType.FUNCTIONAL_TRAINING)
                .trainingDuration("7 days")
                .build();

        training2 = Training.builder()
                .trainingId(UUID.randomUUID())
                .traineeId(UUID.randomUUID().toString())
                .trainingName("Some training")
                .trainingDate(LocalDate.now().toString())
                .trainingType(TrainingType.FUNCTIONAL_TRAINING)
                .trainingDuration("7 days")
                .build();
    }

    @Test
    public void testTrainingService_TrainingIsCreated() {
        //given
        Training someTraining = Training.builder()
                .trainingId(UUID.randomUUID())
                .traineeId(UUID.randomUUID().toString())
                .trainingName("Some training")
                .trainingDate(LocalDate.now().toString())
                .trainingType(TrainingType.FUNCTIONAL_TRAINING)
                .trainingDuration("7 years")
                .build();

        //mocking
        when(trainingDAO.insert(someTraining.getTrainingId(), someTraining)).thenReturn(someTraining);

        //when
        trainingService.createTraining(someTraining.getTrainingId(), someTraining);

        //then
        verify(trainingDAO, times(1)).insert(someTraining.getTrainingId(), someTraining);
    }

    @Test
    public void testTrainingService_TrainingGetById() {
        //given
        UUID trainingId = UUID.randomUUID();
        Training mockTraining = Training.builder()
                .trainingId(trainingId)
                .traineeId(UUID.randomUUID().toString())
                .trainingName("Some training")
                .trainingDate(LocalDate.now().toString())
                .trainingType(TrainingType.FUNCTIONAL_TRAINING)
                .trainingDuration("7 years")
                .build();

        //mocking
        when(trainingDAO.findById(trainingId)).thenReturn(Optional.ofNullable(mockTraining));

        //when
        Training result = trainingService.getTrainingById(trainingId);

        //then
        verify(trainingDAO, times(1)).findById(trainingId);

        assertNotNull(result);
        assertEquals(result.getTrainingId(), trainingId);
    }
    @Test
    public void testTrainingService_TrainingGetByIdNotFound() {
        //given
        UUID trainingId = UUID.randomUUID();

        //mocking
        when(trainingDAO.findById(trainingId)).thenThrow(TrainingNotFoundException.class);
        assertThrows(TrainingNotFoundException.class, () -> {
            trainingDAO.findById(trainingId);
        });
    }

    @Test
    void testGetAllTrainings_ReturnsEmptyList_WhenNoTrainingsExist() {
        when(trainingDAO.findAll()).thenReturn(Collections.emptyMap());

        List<Training> result = trainingService.getAllTrainings();

        assertTrue(result.isEmpty(), "Result should be an empty list");
        verify(trainingDAO, times(1)).findAll();
    }

    @Test
    void testGetAllTrainings_ReturnsListOfTrainees_WhenTrainingsExist() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        Map<UUID, Training> trainerMap = new HashMap<>();
        trainerMap.put(id1, training);
        trainerMap.put(id2, training2);

        when(trainingDAO.findAll()).thenReturn(trainerMap);

        List<Training> result = trainingService.getAllTrainings();

        assertEquals(2, result.size());
        assertTrue(result.contains(training), "Result should contain trainer1");
        assertTrue(result.contains(training2), "Result should contain trainer2");
        verify(trainingDAO, times(1)).findAll();
    }
}
