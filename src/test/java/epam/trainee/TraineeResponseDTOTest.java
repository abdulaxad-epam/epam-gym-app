package epam.trainee;

import epam.trainee.dto.TraineeResponseDTO;
import epam.user.dto.UserResponseDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TraineeResponseDTOTest {

    @Test
    void testTraineeResponseDTO_BuilderAndGetters() {
        // Arrange
        UserResponseDTO user = UserResponseDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .username("johndoe")
                .isActive(true)
                .build();

        TraineeResponseDTO traineeResponse = TraineeResponseDTO.builder()
                .traineeDateOfBirth("2000-01-01")
                .address("123 Main Street")
                .user(user)
                .build();

        // Act & Assert
        assertEquals("2000-01-01", traineeResponse.getTraineeDateOfBirth());
        assertEquals("123 Main Street", traineeResponse.getAddress());
        assertNotNull(traineeResponse.getUser());
        assertEquals("John", traineeResponse.getUser().getFirstName());
        assertEquals("Doe", traineeResponse.getUser().getLastName());
        assertEquals("johndoe", traineeResponse.getUser().getUsername());
        assertTrue(traineeResponse.getUser().getIsActive());
    }

    @Test
    void testTraineeResponseDTO_Setters() {
        // Arrange
        TraineeResponseDTO traineeResponse = new TraineeResponseDTO();
        UserResponseDTO user = new UserResponseDTO("John", "Doe", "johndoe", true);

        // Act
        traineeResponse.setTraineeDateOfBirth("1995-05-15");
        traineeResponse.setAddress("456 Another Street");
        traineeResponse.setUser(user);

        // Assert
        assertEquals("1995-05-15", traineeResponse.getTraineeDateOfBirth());
        assertEquals("456 Another Street", traineeResponse.getAddress());
        assertEquals(user, traineeResponse.getUser());
    }

    @Test
    void testTraineeResponseDTO_ToString() {
        // Arrange
        UserResponseDTO user = new UserResponseDTO("Alice", "Smith", "alicesmith", false);
        TraineeResponseDTO traineeResponse = new TraineeResponseDTO("1998-08-20", "789 New Street", user);

        // Act
        String toStringResult = traineeResponse.toString();

        // Assert
        assertTrue(toStringResult.contains("1998-08-20"));
        assertTrue(toStringResult.contains("789 New Street"));
        assertTrue(toStringResult.contains("Alice"));
        assertTrue(toStringResult.contains("Smith"));
        assertTrue(toStringResult.contains("alicesmith"));
        assertTrue(toStringResult.contains("false"));
    }
}