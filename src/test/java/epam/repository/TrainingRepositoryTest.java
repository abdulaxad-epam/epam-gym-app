package epam.repository;

import epam.exception.exception.TrainingNotFoundException;
import epam.entity.Training;
import epam.repository.impl.TrainingRepositoryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.apache.commons.logging.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrainingRepositoryTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private EntityTransaction transaction;

    @Mock
    private Log log;

    @InjectMocks
    private TrainingRepositoryImpl trainingRepository;

    private Training training;
    private UUID trainingId;

    @BeforeEach
    void setUp() {
        trainingId = UUID.randomUUID();
        training = new Training();
        training.setTrainingId(trainingId);

        when(entityManager.getTransaction()).thenReturn(transaction);
    }

    @Test
    void testInsertTraining_Success() {
        when(entityManager.merge(training.getTrainee())).thenReturn(training.getTrainee());
        when(entityManager.merge(training.getTrainer())).thenReturn(training.getTrainer());
        when(entityManager.merge(training.getTrainingType())).thenReturn(training.getTrainingType());

        Training result = trainingRepository.insert(training);

        assertNotNull(result);
        verify(entityManager).persist(training);
        verify(transaction).commit();
    }

    @Test
    void testInsertTraining_Failure() {
        doThrow(new RuntimeException("DB error")).when(entityManager).persist(training);

        assertThrows(RuntimeException.class, () -> trainingRepository.insert(training));

        verify(transaction).rollback();
    }

    @Test
    void testDeleteTraining_Success() {
        when(entityManager.find(Training.class, trainingId)).thenReturn(training);

        trainingRepository.delete(trainingId);

        verify(entityManager).remove(training);
        verify(transaction).commit();
    }

    @Test
    void testDeleteTraining_NotFound() {
        when(entityManager.find(Training.class, trainingId)).thenReturn(null);

        assertThrows(TrainingNotFoundException.class, () -> trainingRepository.delete(trainingId));

        verify(transaction).rollback();
    }
}
