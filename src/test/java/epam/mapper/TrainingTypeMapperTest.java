package epam.mapper;

import epam.dto.request_dto.RegisterTrainerRequestDTO;
import epam.entity.TrainingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TrainingTypeMapperTest {

    private TrainingTypeMapper trainingTypeMapper;

    @BeforeEach
    void setUp() {
        trainingTypeMapper = TrainingTypeMapper.INSTANCE;
    }

    @Test
    void testToTrainingType_FromRegisterTrainerRequestDTO() {
        RegisterTrainerRequestDTO requestDTO = RegisterTrainerRequestDTO.builder().build();
        requestDTO.setSpecialization("Cardio");

        TrainingType result = trainingTypeMapper.toTrainingType(requestDTO);

        assertNotNull(result);
        assertEquals("Cardio", result.getDescription());
    }

    @Test
    void testToTrainingType_FromSpecializationString() {
        String specialization = "Strength Training";

        TrainingType result = trainingTypeMapper.toTrainingType(specialization);

        assertNotNull(result);
        assertEquals("Strength Training", result.getDescription());
    }

    @Test
    void testToTrainingType_NullRegisterTrainerRequestDTO() {
        TrainingType result = trainingTypeMapper.toTrainingType((RegisterTrainerRequestDTO) null);
        assertNull(result);
    }

    @Test
    void testToTrainingType_NullSpecializationString() {
        TrainingType result = trainingTypeMapper.toTrainingType((String) null);
        assertNull(result);
    }
}
