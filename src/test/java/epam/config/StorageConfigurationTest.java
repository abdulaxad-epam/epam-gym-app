package epam.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import epam.domain.Trainee;
import epam.domain.Trainer;
import epam.domain.Training;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;



public class StorageConfigurationTest {

    private AnnotationConfigApplicationContext context;

    private Trainee trainee;

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

        context = new AnnotationConfigApplicationContext(StorageConfiguration.class);
    }

    @AfterEach
    void tearDown() {
        if (context != null) {
            context.close();
        }
    }

    @Test
    void testObjectMapperBeanIsLoaded() {
        ObjectMapper objectMapper = context.getBean(ObjectMapper.class);
        assertNotNull(objectMapper, "ObjectMapper bean should be created");
    }

    @Test
    void testInMemoryTraineeMapBeanIsLoaded() {
        Map<UUID, Trainee> traineeMap = context.getBean("inMemoryTrainee", Map.class);
        assertNotNull(traineeMap, "Trainee map bean should be initialized");
        assertTrue(traineeMap.isEmpty(), "Trainee map should be empty initially");
    }

    @Test
    void testInMemoryTrainerMapBeanIsLoaded() {
        Map<UUID, Trainer> trainerMap = context.getBean("inMemoryTrainer", Map.class);
        assertNotNull(trainerMap, "Trainer map bean should be initialized");
        assertTrue(trainerMap.isEmpty(), "Trainer map should be empty initially");
    }

    @Test
    void testInMemoryTrainingMapBeanIsLoaded() {
        Map<UUID, Training> trainingMap = context.getBean("inMemoryTraining", Map.class);
        assertNotNull(trainingMap, "Training map bean should be initialized");
        assertTrue(trainingMap.isEmpty(), "Training map should be empty initially");
    }

    @Test
    void testBeanSingletonBehavior() {
        Map<UUID, Trainee> traineeMap1 = context.getBean("inMemoryTrainee", Map.class);
        Map<UUID, Trainee> traineeMap2 = context.getBean("inMemoryTrainee", Map.class);

        assertSame(traineeMap1, traineeMap2, "Trainee map should be a singleton");

        UUID id = UUID.randomUUID();
        traineeMap1.put(id, trainee);

        assertEquals(trainee, traineeMap2.get(id), "Singleton map should contain the same data");
    }
}
