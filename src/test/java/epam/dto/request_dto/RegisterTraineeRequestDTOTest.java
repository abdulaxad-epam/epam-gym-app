package epam.dto.request_dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.instancio.Instancio;
import org.instancio.junit.InstancioExtension;
import org.instancio.junit.WithSettings;
import org.instancio.settings.Keys;
import org.instancio.settings.Settings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;
import java.util.Set;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(InstancioExtension.class)
public class RegisterTraineeRequestDTOTest {

    private Validator validator;

    @WithSettings
    public static final Settings settings = Settings.create()
            .set(Keys.STRING_MIN_LENGTH, 10).lock();

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidRegisterTraineeRequest() {
        RegisterTraineeRequestDTO request = Instancio.of(RegisterTraineeRequestDTO.class)
                .withSettings(settings)
                .set(field(RegisterTraineeRequestDTO::getDateOfBirth), LocalDate.of(2000, 1, 1))
                .set(field(RegisterTraineeRequestDTO::getAddress), "123 Test Street")
                .set(field(RegisterTraineeRequestDTO::getUser),
                        Instancio.of(UserRequestDTO.class)
                                .set(field(UserRequestDTO::getFirstName), "John")
                                .set(field(UserRequestDTO::getLastName), "Aspect")
                                .set(field(UserRequestDTO::getIsActive), true)
                                .create())
                .create();

        Set<ConstraintViolation<RegisterTraineeRequestDTO>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidFutureDateOfBirth() {
        RegisterTraineeRequestDTO request = Instancio.of(RegisterTraineeRequestDTO.class)
                .withSettings(settings)
                .set(field(UserRequestDTO::getFirstName), "John")
                .set(field(RegisterTraineeRequestDTO::getDateOfBirth), LocalDate.now().plusDays(1))
                .create();

        Set<ConstraintViolation<RegisterTraineeRequestDTO>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertEquals("Date of birth must be in the past", violations.iterator().next().getMessage());
    }



    @Test
    public void testBlankAddress() {
        RegisterTraineeRequestDTO request = Instancio.of(RegisterTraineeRequestDTO.class)
                .withSettings(settings)
                .set(field(RegisterTraineeRequestDTO::getDateOfBirth), LocalDate.of(2000, 1, 1))
                .set(field(RegisterTraineeRequestDTO::getAddress), "")
                .create();

        Set<ConstraintViolation<RegisterTraineeRequestDTO>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertEquals("Address is required", violations.iterator().next().getMessage());
    }

    @Test
    public void testNullUserDetails() {
        RegisterTraineeRequestDTO request = Instancio.of(RegisterTraineeRequestDTO.class)
                .withSettings(settings)
                .set(field(RegisterTraineeRequestDTO::getDateOfBirth), LocalDate.of(2000, 1, 1))
                .set(field(RegisterTraineeRequestDTO::getUser), null)
                .create();

        Set<ConstraintViolation<RegisterTraineeRequestDTO>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertEquals("User details are required", violations.iterator().next().getMessage());
    }
}