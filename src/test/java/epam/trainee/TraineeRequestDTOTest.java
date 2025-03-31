package epam.trainee;

import epam.trainee.dto.TraineeRequestDTO;
import epam.user.dto.UserRequestDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TraineeRequestDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidTraineeRequestDTO() {
        // Arrange
        UserRequestDTO user = UserRequestDTO.builder()
                .firstName("John")
                .lastName("Doe2")
                .isActive(true)
                .build();

        TraineeRequestDTO traineeRequest = TraineeRequestDTO.builder()
                .dateOfBirth(LocalDate.of(2000, 1, 1))
                .address("123 Main Street")
                .user(user)
                .build();

        // Act
        Set<ConstraintViolation<TraineeRequestDTO>> violations = validator.validate(traineeRequest);

        // Assert
        assertTrue(violations.isEmpty(), "There should be no validation errors");
    }

    @Test
    void testInvalidTraineeRequestDTO_MissingDateOfBirth() {
        // Arrange
        UserRequestDTO user = UserRequestDTO.builder()
                .firstName("John")
                .lastName("Doe2")
                .isActive(true)
                .build();

        TraineeRequestDTO traineeRequest = TraineeRequestDTO.builder()
                .address("123 Main Street")
                .user(user)
                .build(); // Missing dateOfBirth

        // Act
        Set<ConstraintViolation<TraineeRequestDTO>> violations = validator.validate(traineeRequest);

        // Assert
        assertFalse(violations.isEmpty(), "Validation should fail due to missing dateOfBirth");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Date of Birth is required")));
    }

    @Test
    void testInvalidTraineeRequestDTO_FutureDateOfBirth() {
        // Arrange
        UserRequestDTO user = UserRequestDTO.builder()
                .firstName("John")
                .lastName("Doe2")
                .isActive(true)
                .build();

        TraineeRequestDTO traineeRequest = TraineeRequestDTO.builder()
                .dateOfBirth(LocalDate.now().plusDays(1)) // Future date
                .address("123 Main Street")
                .user(user)
                .build();

        // Act
        Set<ConstraintViolation<TraineeRequestDTO>> violations = validator.validate(traineeRequest);

        // Assert
        assertFalse(violations.isEmpty(), "Validation should fail due to future dateOfBirth");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Date of birth must be in the past")));
    }

    @Test
    void testInvalidTraineeRequestDTO_MissingAddress() {
        // Arrange
        UserRequestDTO user = UserRequestDTO.builder()
                .firstName("John")
                .lastName("Doe2")
                .isActive(true)
                .build();

        TraineeRequestDTO traineeRequest = TraineeRequestDTO.builder()
                .dateOfBirth(LocalDate.of(2000, 1, 1))
                .user(user)
                .build(); // Missing address

        // Act
        Set<ConstraintViolation<TraineeRequestDTO>> violations = validator.validate(traineeRequest);

        // Assert
        assertFalse(violations.isEmpty(), "Validation should fail due to missing address");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Address is required")));
    }

    @Test
    void testInvalidTraineeRequestDTO_MissingUser() {
        // Arrange
        TraineeRequestDTO traineeRequest = TraineeRequestDTO.builder()
                .dateOfBirth(LocalDate.of(2000, 1, 1))
                .address("123 Main Street")
                .build(); // Missing user

        // Act
        Set<ConstraintViolation<TraineeRequestDTO>> violations = validator.validate(traineeRequest);

        // Assert
        assertFalse(violations.isEmpty(), "Validation should fail due to missing user");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("User details are required")));
    }
}
