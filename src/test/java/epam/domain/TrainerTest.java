package epam.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TrainerTest {

    @Test
    void testNoArgsConstructor() {
        Trainer trainer = new Trainer();
        assertNotNull(trainer, "No-args constructor should create a non-null instance");
    }

    @Test
    void testAllArgsConstructor() {
        Trainer trainer = new Trainer("HYPERTROPHY_TRAINING");
        assertNotNull(trainer, "All-args constructor should create a non-null instance");
        assertEquals("HYPERTROPHY_TRAINING", trainer.getSpecialization(), "Specialization should be set correctly");
    }

    @Test
    void testGettersAndSetters() {
        Trainer trainer = new Trainer();
        trainer.setSpecialization("FUNCTIONAL_TRAINING");

        assertEquals("FUNCTIONAL_TRAINING", trainer.getSpecialization(), "Getter should return the correct value");
    }

    @Test
    void testBuilder() {
        Trainer trainer = Trainer.builder()
                .specialization("FUNCTIONAL_TRAINING")
                .build();

        assertNotNull(trainer, "Builder should create a non-null instance");
        assertEquals("FUNCTIONAL_TRAINING", trainer.getSpecialization(), "Builder should set the correct value");
    }
}

