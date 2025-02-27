package epam.domain;

import epam.enums.TrainingType;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TrainingTest {

    @Test
    void testNoArgsConstructor() {
        Training training = new Training();
        assertNotNull(training, "No-args constructor should create a non-null instance");
    }

    @Test
    void testBuilder() {
        UUID trainingId = UUID.randomUUID();
        Training training = Training.builder()
                .trainingId(trainingId)
                .traineeId("trainee123")
                .trainerId("trainer456")
                .trainingName("Strength Training")
                .trainingDate("2025-03-01")
                .trainingType(TrainingType.FUNCTIONAL_TRAINING)
                .trainingDuration("60 minutes")
                .build();

        assertNotNull(training, "Builder should create a non-null instance");
        assertEquals("Strength Training", training.getTrainingName(), "Builder should set training name correctly");
        assertEquals("trainee123", training.getTraineeId(), "Builder should set trainee ID correctly");
        assertEquals("trainer456", training.getTrainerId(), "Builder should set trainer ID correctly");
        assertEquals("2025-03-01", training.getTrainingDate(), "Builder should set training date correctly");
        assertEquals(TrainingType.FUNCTIONAL_TRAINING, training.getTrainingType(), "Builder should set training type correctly");
        assertEquals("60 minutes", training.getTrainingDuration(), "Builder should set training duration correctly");
    }
}
