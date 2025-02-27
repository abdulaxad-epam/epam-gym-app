package epam.util.parser;

import epam.domain.Trainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TrainerParserTest {

    private TrainerParser trainerParser;

    @BeforeEach
    void setUp() {
        trainerParser = new TrainerParser();
    }

    @Test
    void testParseValidTrainers() {
        // Given
        List<Map<String, String>> inputData = List.of(
                Map.of(
                        "userId", UUID.randomUUID().toString(),
                        "firstname", "Alex",
                        "lastname", "Ferguson",
                        "specialization", "Strength Training",
                        "isActive", "true"
                ),
                Map.of(
                        "userId", UUID.randomUUID().toString(),
                        "firstname", "Maria",
                        "lastname", "Sharapova",
                        "specialization", "Tennis Coaching",
                        "isActive", "false"
                )
        );

        List<Trainer> trainers = trainerParser.parse(inputData);

        // Assertions
        assertNotNull(trainers, "Parsed list should not be null");
        assertEquals(2, trainers.size(), "There should be exactly 2 trainers");

        Trainer trainer1 = trainers.get(0);
        assertEquals("Alex.Ferguson", trainer1.getUsername(), "Username should be 'firstname.lastname'");
        assertEquals("Alex", trainer1.getFirstname(), "Firstname should be correct");
        assertEquals("Ferguson", trainer1.getLastname(), "Lastname should be correct");
        assertEquals("Strength Training", trainer1.getSpecialization(), "Specialization should match");
        assertTrue(trainer1.getIsActive(), "isActive should be true");


        Trainer trainer2 = trainers.get(1);
        assertEquals("Maria.Sharapova", trainer2.getUsername(), "Username should be 'firstname.lastname'");
        assertEquals("Maria", trainer2.getFirstname(), "Firstname should be correct");
        assertEquals("Sharapova", trainer2.getLastname(), "Lastname should be correct");
        assertEquals("Tennis Coaching", trainer2.getSpecialization(), "Specialization should match");
        assertFalse(trainer2.getIsActive(), "isActive should be false");
    }

    @Test
    void testParseWithEmptyList() {
        List<Trainer> trainers = trainerParser.parse(Collections.emptyList());
        assertNotNull(trainers, "Parsed list should not be null");
        assertTrue(trainers.isEmpty(), "Parsed list should be empty when input is empty");
    }

    @Test
    void testParseWithMissingFields() {
        List<Map<String, String>> inputData = List.of(
                Map.of(
                        "userId", UUID.randomUUID().toString(),
                        "firstname", "Serena",
                        "lastname", "Williams"
                )
        );

        List<Trainer> trainers = trainerParser.parse(inputData);

        assertNotNull(trainers, "Parsed list should not be null");
        assertEquals(1, trainers.size(), "Should parse 1 trainer");

        Trainer trainer = trainers.get(0);
        assertEquals("Serena.Williams", trainer.getUsername(), "Username should be 'firstname.lastname'");
        assertNull(trainer.getSpecialization(), "Specialization should be null if missing");
        assertFalse(trainer.getIsActive(), "isActive should default to false");
    }
}
