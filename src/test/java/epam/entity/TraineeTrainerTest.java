package epam.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class TraineeTrainerTest {

    @Mock
    private Trainee mockTrainee;

    @Mock
    private Trainer mockTrainer;

    private TraineeTrainer traineeTrainer;
    private TraineeTrainer.TraineeTrainerId traineeTrainerId;

    @BeforeEach
    void setUp() {
        UUID traineeId = UUID.randomUUID();
        UUID trainerId = UUID.randomUUID();
        traineeTrainerId = new TraineeTrainer.TraineeTrainerId(traineeId, trainerId);

        traineeTrainer = TraineeTrainer.builder()
                .id(traineeTrainerId)
                .trainee(mockTrainee)
                .trainer(mockTrainer)
                .build();
    }

    @Test
    void testTraineeTrainerCreation() {
        assertNotNull(traineeTrainer);
        assertNotNull(traineeTrainer.getId());
        assertEquals(traineeTrainerId, traineeTrainer.getId());
        assertEquals(mockTrainee, traineeTrainer.getTrainee());
        assertEquals(mockTrainer, traineeTrainer.getTrainer());
    }

    @Test
    void testTraineeTrainerEquality() {
        TraineeTrainer anotherInstance = TraineeTrainer.builder()
                .id(traineeTrainerId)
                .trainee(mockTrainee)
                .trainer(mockTrainer)
                .build();

        assertEquals(traineeTrainer, anotherInstance);
    }

    @Test
    void testTraineeTrainerInequality() {
        TraineeTrainer.TraineeTrainerId differentId = new TraineeTrainer.TraineeTrainerId(UUID.randomUUID(), UUID.randomUUID());

        TraineeTrainer differentInstance = TraineeTrainer.builder()
                .id(differentId)
                .trainee(mockTrainee)
                .trainer(mockTrainer)
                .build();

        assertNotEquals(traineeTrainer, differentInstance);
    }

    @Test
    void testNullIdShouldNotThrowException() {
        TraineeTrainer instanceWithNullId = TraineeTrainer.builder()
                .id(null)
                .trainee(mockTrainee)
                .trainer(mockTrainer)
                .build();

        assertNull(instanceWithNullId.getId());
    }

    @Test
    void testTraineeTrainerHandlesNullTrainee() {
        traineeTrainer.setTrainee(null);
        assertNull(traineeTrainer.getTrainee());
    }

    @Test
    void testTraineeTrainerHandlesNullTrainer() {
        traineeTrainer.setTrainer(null);
        assertNull(traineeTrainer.getTrainer());
    }
}
