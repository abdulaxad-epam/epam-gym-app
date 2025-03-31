package epam.shared.security.dto;


import epam.user.dto.UserRequestDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.instancio.Instancio;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Set;

import static epam.trainer.TrainerControllerTest.settings;
import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(InstancioExtension.class)
public class RegisterTrainerRequestDTOTest {

    private final Validator validator;

    public RegisterTrainerRequestDTOTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    public void testValidRegisterTrainerRequest() {
        RegisterTrainerRequestDTO request = Instancio.of(RegisterTrainerRequestDTO.class)
                .withSettings(settings)
                .set(field(UserRequestDTO::getFirstName), "John")
                .set(field(UserRequestDTO::getLastName),"John")
                .create();

        Set<ConstraintViolation<RegisterTrainerRequestDTO>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
        assertNotNull(request.getSpecialization());
        assertNotNull(request.getUser());
    }

    @Test
    public void testBlankSpecialization() {
        RegisterTrainerRequestDTO request = Instancio.of(RegisterTrainerRequestDTO.class)
                .withSettings(settings)
                .set(field(RegisterTrainerRequestDTO::getSpecialization), "")
                .create();

        Set<ConstraintViolation<RegisterTrainerRequestDTO>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertEquals("Specialization is required", violations.iterator().next().getMessage());
    }

    @Test
    public void testNullUserDetails() {
        RegisterTrainerRequestDTO request = RegisterTrainerRequestDTO.builder()
                .specialization("Fitness")
                .user(null)
                .build();

        Set<ConstraintViolation<RegisterTrainerRequestDTO>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertEquals("User details are required", violations.iterator().next().getMessage());
    }

}