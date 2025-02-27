package epam.dao;

import epam.domain.Trainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TrainerDAOTest {

    private TrainerDAO trainerDAO;
    private Map<UUID, Trainer> inMemoryTrainer;


    private Trainer trainer;

    private Trainer trainer2;

    @BeforeEach
    void setUp() {
        inMemoryTrainer = new HashMap<>();
        trainerDAO = new TrainerDAO(inMemoryTrainer);

        trainer = Trainer.builder()
                .firstname("John")
                .lastname("Doe")
                .username("johndoe")
                .isActive(true)
                .specialization("Specialization")
                .build();

        trainer2 = Trainer.builder()
                .firstname("Jane")
                .lastname("Smith")
                .isActive(false)
                .specialization("Pilates")
                .build();
    }

    @Test
    void testFindById_TrainerExists() {
        UUID id = UUID.randomUUID();
        inMemoryTrainer.put(id, trainer);

        Optional<Trainer> found = trainerDAO.findById(id);

        assertTrue(found.isPresent(), "Trainer should be found");
        assertEquals(trainer, found.get(), "Returned trainer should match");
    }

    @Test
    void testFindById_TrainerDoesNotExist() {
        UUID id = UUID.randomUUID();
        Optional<Trainer> found = trainerDAO.findById(id);
        assertFalse(found.isPresent(), "Trainer should not be found");
    }

    @Test
    void testUpdate_TrainerExists() {
        UUID id = UUID.randomUUID();
        Trainer oldTrainer =trainer;
        inMemoryTrainer.put(id, oldTrainer);

        Trainer updatedTrainer = trainer2;
        Trainer result = trainerDAO.update(id, updatedTrainer);

        assertEquals("Smith", result.getLastname(), "Last name should be updated");
        assertEquals("Pilates", result.getSpecialization(), "Specialization should be updated");
    }

    @Test
    void testUpdate_TrainerDoesNotExist() {
        UUID id = UUID.randomUUID();

        Trainer result = trainerDAO.update(id, trainer);

        assertNull(inMemoryTrainer.get(id), "Trainer should not be added if not already present");
    }

    @Test
    void testDelete_TrainerExists() {
        UUID id = UUID.randomUUID();
        inMemoryTrainer.put(id, trainer);

        trainerDAO.delete(id);

        assertFalse(inMemoryTrainer.containsKey(id), "Trainer should be deleted");
    }

    @Test
    void testDelete_TrainerDoesNotExist() {
        UUID id = UUID.randomUUID();
        assertDoesNotThrow(() -> trainerDAO.delete(id), "Deleting a non-existing trainer should not throw");
    }

    @Test
    void testFindAll_WhenDataExists() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        inMemoryTrainer.put(id1, trainer);
        inMemoryTrainer.put(id2, trainer2);

        Map<UUID, Trainer> result = trainerDAO.findAll();

        assertEquals(2, result.size(), "Should return all trainers");
        assertTrue(result.containsKey(id1) && result.containsKey(id2), "Returned map should contain both trainers");
    }

    @Test
    void testFindAll_WhenNoData() {
        Map<UUID, Trainer> result = trainerDAO.findAll();
        assertTrue(result.isEmpty(), "Should return an empty map");
    }
}
