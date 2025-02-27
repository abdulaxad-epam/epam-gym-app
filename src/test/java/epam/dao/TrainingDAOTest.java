package epam.dao;

import epam.domain.Training;
import epam.enums.TrainingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TrainingDAOTest {

    private TrainingDAO trainingDAO;
    private Map<UUID, Training> inMemoryTraining;

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
                .trainingId(UUID.fromString("770e8403-e29b-41d4-a716-426655446006"))
                .traineeId(UUID.randomUUID().toString())
                .trainingName("David")
                .trainingDate(LocalDate.now().toString())
                .trainingType(TrainingType.HYPERTROPHY_TRAINING)
                .trainingDuration("123 days")
                .build();

        inMemoryTraining = new HashMap<>();
        trainingDAO = new TrainingDAO(inMemoryTraining);
    }

    @Test
    void testInsert_TrainingIsAddedSuccessfully() {
        UUID id = UUID.randomUUID();

        Training result = trainingDAO.insert(id, training);

        assertEquals(training, result, "Inserted training should be returned");
        assertTrue(inMemoryTraining.containsKey(id), "Training should be in the storage");
        assertEquals(training, inMemoryTraining.get(id), "Stored training should match");
    }

    @Test
    void testFindById_TrainingExists() {
        UUID id = UUID.randomUUID();
        inMemoryTraining.put(id, training);

        Optional<Training> found = trainingDAO.findById(id);

        assertTrue(found.isPresent(), "Training should be found");
        assertEquals(training, found.get(), "Returned training should match");
    }

    @Test
    void testFindById_TrainingDoesNotExist() {
        UUID id = UUID.randomUUID();

        Optional<Training> found = trainingDAO.findById(id);

        assertFalse(found.isPresent(), "Training should not be found");
    }

    @Test
    void testFindAll_WhenDataExists() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        inMemoryTraining.put(id1, training);
        inMemoryTraining.put(id2, training2);

        Map<UUID, Training> result = trainingDAO.findAll();

        assertEquals(2, result.size(), "Should return all trainings");
        assertTrue(result.containsKey(id1) && result.containsKey(id2), "Returned map should contain both trainings");
    }

    @Test
    void testFindAll_WhenNoData() {
        Map<UUID, Training> result = trainingDAO.findAll();
        assertTrue(result.isEmpty(), "Should return an empty map");
    }
}

