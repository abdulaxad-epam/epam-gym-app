package epam.dao;

import epam.domain.Trainee;
import epam.exception.TraineeNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TraineeDAOTest {

    private TraineeDAO traineeDAO;
    private Map<UUID, Trainee> inMemoryTrainee;

    private Trainee trainee;

    private Trainee trainee2;

    @BeforeEach
    void setUp() {
        trainee = Trainee.builder()
                .firstname("John")
                .lastname("Doe")
                .username("johndoe")
                .isActive(true)
                .dateOfBirth("1995-06-15")
                .address("123 Main St, Springfield")
                .build();

        trainee2 = Trainee.builder()
                .firstname("Jane")
                .lastname("Smith")
                .isActive(false)
                .dateOfBirth("1998-09-20")
                .address("456 Elm St, Metropolis")
                .build();
        inMemoryTrainee = new HashMap<>();
        traineeDAO = new TraineeDAO(inMemoryTrainee);
    }

    @Test
    void testFindById_TraineeExists() {
        UUID id = UUID.randomUUID();
        inMemoryTrainee.put(id, trainee);

        Optional<Trainee> found = traineeDAO.findById(id);

        assertTrue(found.isPresent(), "Trainee should be found");
        assertEquals(trainee, found.get(), "Returned trainee should match");
    }

    @Test
    void testFindById_TraineeDoesNotExist() {
        UUID id = UUID.randomUUID();
        Optional<Trainee> found = traineeDAO.findById(id);
        assertFalse(found.isPresent(), "Trainee should not be found");
    }

    @Test
    void testUpdate_TraineeExists() {
        UUID id = UUID.randomUUID();
        inMemoryTrainee.put(id, trainee);

        Trainee updatedTrainee = trainee2;
        Trainee result = traineeDAO.update(id, updatedTrainee);

        assertEquals("Smith", result.getLastname(), "Last name should be updated");
        assertEquals("456 Elm St, Metropolis", result.getAddress(), "Address should be updated");
    }

    @Test
    void testUpdate_TraineeDoesNotExist() {
        UUID id = UUID.randomUUID();
        Trainee newTrainee = trainee2;

        Exception exception = assertThrows(TraineeNotFoundException.class, () -> {
            traineeDAO.update(id, newTrainee);
        });

        assertEquals("Trainee with id " + id + " not found", exception.getMessage());
    }

    @Test
    void testDelete_TraineeExists() {
        UUID id = UUID.randomUUID();
        inMemoryTrainee.put(id, trainee);

        traineeDAO.delete(id);

        assertFalse(inMemoryTrainee.containsKey(id), "Trainee should be deleted");
    }

    @Test
    void testDelete_TraineeDoesNotExist() {
        UUID id = UUID.randomUUID();
        assertDoesNotThrow(() -> traineeDAO.delete(id), "Deleting a non-existing trainee should not throw");
    }

    @Test
    void testFindAll_WhenDataExists() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        inMemoryTrainee.put(id1, trainee);
        inMemoryTrainee.put(id2, trainee2);

        Map<UUID, Trainee> result = traineeDAO.findAll();

        assertEquals(2, result.size(), "Should return all trainees");
        assertTrue(result.containsKey(id1) && result.containsKey(id2), "Returned map should contain both trainees");
    }

    @Test
    void testFindAll_WhenNoData() {
        Map<UUID, Trainee> result = traineeDAO.findAll();
        assertTrue(result.isEmpty(), "Should return an empty map");
    }
}
