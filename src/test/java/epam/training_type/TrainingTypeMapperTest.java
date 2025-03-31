package epam.training_type;

import epam.shared.security.dto.RegisterTrainerRequestDTO;
import epam.training_type.dto.TrainingTypeResponseDTO;
import epam.training_type.entity.TrainingType;
import epam.training_type.mapper.TrainingTypeMapper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

public class TrainingTypeMapperTest {

    private final TrainingTypeMapper trainingTypeMapper = Mappers.getMapper(TrainingTypeMapper.class);

    @Test
    void testToTrainingType_FromRegisterTrainerRequestDTO() {
        RegisterTrainerRequestDTO requestDTO = new RegisterTrainerRequestDTO();
        requestDTO.setSpecialization("Yoga");

        TrainingType trainingType = trainingTypeMapper.toTrainingType(requestDTO);

        assertNotNull(trainingType);
        assertEquals("Yoga", trainingType.getDescription());
    }

    @Test
    void testToTrainingType_FromString() {
        TrainingType trainingType = trainingTypeMapper.toTrainingType("Pilates");

        assertNotNull(trainingType);
        assertEquals("Pilates", trainingType.getDescription());
    }

    @Test
    void testToTrainingType_FromNullString() {
        TrainingType trainingType = trainingTypeMapper.toTrainingType((String) null);

        assertNull(trainingType);
    }

    @Test
    void testToTrainingTypeResponseDTO() {
        TrainingType trainingType = TrainingType.builder().description("Cardio").build();

        TrainingTypeResponseDTO responseDTO = trainingTypeMapper.toTrainingTypeResponseDTO(trainingType);

        assertNotNull(responseDTO);
        assertEquals("Cardio", responseDTO.getTrainingType());
    }
}