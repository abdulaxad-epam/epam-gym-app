package epam.util.parser;

import epam.domain.Training;
import epam.enums.TrainingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TrainingParserTest {

    private TrainingParser trainingParser;

    @BeforeEach
    void setUp() {
        trainingParser = new TrainingParser();
    }

    @Test
    void testParseValidTrainings() {
        // Given
        List<Map<String, String>> inputData = List.of(
                Map.of(
                        "trainingId", UUID.randomUUID().toString(),
                        "traineeId", UUID.randomUUID().toString(),
                        "trainerId", UUID.randomUUID().toString(),
                        "trainingName", "Strength Training",
                        "trainingDate", "2025-03-01",
                        "trainingType", "FLEXIBILITY",
                        "trainingDuration", "60 days"
                ),
                Map.of(
                        "trainingId", UUID.randomUUID().toString(),
                        "traineeId", UUID.randomUUID().toString(),
                        "trainerId", UUID.randomUUID().toString(),
                        "trainingName", "Cardio Session",
                        "trainingDate", "2025-03-02",
                        "trainingType", "FUNCTIONAL_TRAINING",
                        "trainingDuration", "45 days"
                )
        );

        List<Training> trainings = trainingParser.parse(inputData);

        // Assertions
        assertNotNull(trainings, "Parsed list should not be null");
        assertEquals(2, trainings.size(), "There should be exactly 2 trainings");

        Training training1 = trainings.get(0);
        assertNotNull(training1.getTrainingId(), "Training ID should be generated");
        assertEquals("Strength Training", training1.getTrainingName(), "Training name should match");
        assertEquals("2025-03-01", training1.getTrainingDate(), "Training date should match");
        assertEquals(TrainingType.FLEXIBILITY, training1.getTrainingType(), "Training type should match");
        assertEquals("60 days", training1.getTrainingDuration(), "Training duration should match");

        Training training2 = trainings.get(1);
        assertNotNull(training2.getTrainingId(), "Training ID should be generated");
        assertEquals("Cardio Session", training2.getTrainingName(), "Training name should match");
        assertEquals("2025-03-02", training2.getTrainingDate(), "Training date should match");
        assertEquals(TrainingType.FUNCTIONAL_TRAINING, training2.getTrainingType(), "Training type should match");
        assertEquals("45 days", training2.getTrainingDuration(), "Training duration should match");
    }

    @Test
    void testParseWithEmptyList() {
        List<Training> trainings = trainingParser.parse(Collections.emptyList());
        assertNotNull(trainings, "Parsed list should not be null");
        assertTrue(trainings.isEmpty(), "Parsed list should be empty when input is empty");
    }

    @Test
    void testParseWithMissingFields() {
        List<Map<String, String>> inputData = List.of(
                Map.of(
                        "trainingId", UUID.randomUUID().toString(),
                        "traineeId", UUID.randomUUID().toString(),
                        "trainerId", UUID.randomUUID().toString(),
                        "trainingName", "Yoga Class"
                )
        );

        Exception exception = assertThrows(NullPointerException.class, () -> trainingParser.parse(inputData));

        assertNotNull(exception, "Parsing should throw an exception for missing fields");
    }

    @Test
    void testParseWithInvalidTrainingType() {
        List<Map<String, String>> inputData = List.of(
                Map.of(
                        "trainingId", UUID.randomUUID().toString(),
                        "traineeId", UUID.randomUUID().toString(),
                        "trainerId", UUID.randomUUID().toString(),
                        "trainingName", "Pilates",
                        "trainingDate", "2025-04-10",
                        "trainingType", "INVALID_TYPE",
                        "trainingDuration", "50 days"
                )
        );

        Exception exception = assertThrows(IllegalArgumentException.class, () -> trainingParser.parse(inputData));

        assertNotNull(exception, "Parsing should throw an exception for invalid trainingType");
    }
}
