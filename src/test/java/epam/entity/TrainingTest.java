package epam.entity;

import epam.shared.training_type.entity.TrainingType;
import epam.trainee.entity.Trainee;
import epam.trainer.entity.Trainer;
import epam.training.entity.Training;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)  // Enables Mockito support
class TrainingTest {

    @Mock
    private Trainee mockTrainee;

    @Mock
    private Trainer mockTrainer;

    @Mock
    private TrainingType mockTrainingType;

    private Training training;
    private UUID trainingId;
    private LocalDateTime trainingDate;

    @BeforeEach
    void setUp() {
        trainingId = UUID.randomUUID();
        trainingDate = LocalDateTime.now();

        training = Training.builder()
                .trainingId(trainingId)
                .trainee(mockTrainee)
                .trainer(mockTrainer)
                .trainingName("Strength Training")
                .trainingDate(trainingDate)
                .trainingType(mockTrainingType)
                .trainingDuration(60)
                .build();
    }

    @Test
    void testTrainingCreation() {
        assertNotNull(training);
        assertEquals(trainingId, training.getTrainingId());
        assertEquals(mockTrainee, training.getTrainee());
        assertEquals(mockTrainer, training.getTrainer());
        assertEquals("Strength Training", training.getTrainingName());
        assertEquals(trainingDate, training.getTrainingDate());
        assertEquals(mockTrainingType, training.getTrainingType());
        assertEquals(60, training.getTrainingDuration());
    }

    @Test
    void testTrainingEquality() {
        Training anotherTraining = Training.builder()
                .trainingId(trainingId)  // Same ID should make them equal
                .trainee(mockTrainee)
                .trainer(mockTrainer)
                .trainingName("Strength Training")
                .trainingDate(trainingDate)
                .trainingType(mockTrainingType)
                .trainingDuration(60)
                .build();

        assertEquals(training, anotherTraining);
    }

    @Test
    void testTrainingInequality() {
        Training differentTraining = Training.builder()
                .trainingId(UUID.randomUUID())  // Different ID should make them unequal
                .trainee(mockTrainee)
                .trainer(mockTrainer)
                .trainingName("Strength Training")
                .trainingDate(trainingDate)
                .trainingType(mockTrainingType)
                .trainingDuration(60)
                .build();

        assertNotEquals(training, differentTraining);
    }

    @Test
    void testTrainingHandlesNullTrainee() {
        training.setTrainee(null);
        assertNull(training.getTrainee());
    }

    @Test
    void testTrainingHandlesNullTrainer() {
        training.setTrainer(null);
        assertNull(training.getTrainer());
    }

    @Test
    void testTrainingHandlesNullTrainingType() {
        training.setTrainingType(null);
        assertNull(training.getTrainingType());
    }

    @Test
    void testTrainingHandlesNullName() {
        training.setTrainingName(null);
        assertNull(training.getTrainingName());
    }

    @Test
    void testTrainingHandlesZeroDuration() {
        training.setTrainingDuration(0);
        assertEquals(0, training.getTrainingDuration());
    }

    @Test
    void testTrainingHandlesNegativeDuration() {
        training.setTrainingDuration(-10);
        assertTrue(training.getTrainingDuration() < 0);
    }

    @Test
    void testTrainingHandlesFutureDate() {
        LocalDateTime futureDate = LocalDateTime.now().plusDays(10);
        training.setTrainingDate(futureDate);
        assertTrue(training.getTrainingDate().isAfter(LocalDateTime.now()));
    }
}
