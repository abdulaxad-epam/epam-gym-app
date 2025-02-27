package epam.util.parser;

import epam.domain.Trainee;
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

public class TraineeParserTest {

    private TraineeParser traineeParser;

    @BeforeEach
    void setUp() {
        traineeParser = new TraineeParser();
    }

    @Test
    void testParseValidTrainees() {
        // Given
        List<Map<String, String>> inputData = List.of(
                Map.of(
                        "userId", UUID.randomUUID().toString(),
                        "firstname", "John",
                        "lastname", "Doe",
                        "dateOfBirth", "1990-01-01",
                        "address", "123 Gym Street",
                        "isActive", "true"
                ),
                Map.of(
                        "userId", UUID.randomUUID().toString(),
                        "firstname", "Jane",
                        "lastname", "Smith",
                        "dateOfBirth", "1992-05-15",
                        "address", "456 Fitness Avenue",
                        "isActive", "false"
                )
        );

        List<Trainee> trainees = traineeParser.parse(inputData);

        // Assertions
        assertNotNull(trainees, "Parsed list should not be null");
        assertEquals(2, trainees.size(), "There should be exactly 2 trainees");

        Trainee trainee1 = trainees.get(0);
        assertEquals("John.Doe", trainee1.getUsername(), "Username should be 'firstname.lastname'");
        assertEquals("John", trainee1.getFirstname(), "Firstname should be correct");
        assertEquals("Doe", trainee1.getLastname(), "Lastname should be correct");
        assertEquals("1990-01-01", trainee1.getDateOfBirth(), "Date of birth should match");
        assertEquals("123 Gym Street", trainee1.getAddress(), "Address should match");
        assertTrue(trainee1.getIsActive(), "isActive should be true");

        Trainee trainee2 = trainees.get(1);
        assertEquals("Jane.Smith", trainee2.getUsername(), "Username should be 'firstname.lastname'");
        assertEquals("Jane", trainee2.getFirstname(), "Firstname should be correct");
        assertEquals("Smith", trainee2.getLastname(), "Lastname should be correct");
        assertEquals("1992-05-15", trainee2.getDateOfBirth(), "Date of birth should match");
        assertEquals("456 Fitness Avenue", trainee2.getAddress(), "Address should match");
        assertFalse(trainee2.getIsActive(), "isActive should be false");
    }

    @Test
    void testParseWithEmptyList() {
        List<Trainee> trainees = traineeParser.parse(Collections.emptyList());
        assertNotNull(trainees, "Parsed list should not be null");
        assertTrue(trainees.isEmpty(), "Parsed list should be empty when input is empty");
    }

    @Test
    void testParseWithMissingFields() {
        List<Map<String, String>> inputData = List.of(
                Map.of(
                        "userId", UUID.randomUUID().toString(),
                        "firstname", "Mike",
                        "lastname", "Tyson"
                )
        );

        List<Trainee> trainees = traineeParser.parse(inputData);

        assertNotNull(trainees, "Parsed list should not be null");
        assertEquals(1, trainees.size(), "Should parse 1 trainee");

        Trainee trainee = trainees.get(0);
        assertEquals("Mike.Tyson", trainee.getUsername(), "Username should be 'firstname.lastname'");
        assertNull(trainee.getDateOfBirth(), "Date of birth should be null if missing");
        assertNull(trainee.getAddress(), "Address should be null if missing");
        assertFalse(trainee.getIsActive(), "isActive should default to false");
    }
}
