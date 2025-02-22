package epam.service;


import epam.dao.TrainingDAO;
import epam.domain.Training;
import epam.enums.TrainingType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrainingServiceTest {

    @Mock
    private TrainingDAO trainingDAO;

    @InjectMocks
    private TrainingServiceImpl trainingService;

    @Test
    public void testTrainingService_TrainingIsCreated() {
        //given
        Training someTraining = new Training.TrainingBuilder()
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
        Training mockTraining = new Training.TrainingBuilder()
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
}
