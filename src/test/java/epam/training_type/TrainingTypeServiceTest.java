package epam.training_type;

import epam.shared.exception.exception.TrainingTypeNotFoundException;
import epam.training_type.dto.TrainingTypeResponseDTO;
import epam.training_type.entity.TrainingType;
import epam.training_type.mapper.TrainingTypeMapper;
import epam.training_type.repository.TrainingTypeRepository;
import epam.training_type.service.impl.TrainingTypeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrainingTypeServiceTest {

    @Mock
    private TrainingTypeRepository trainingTypeRepository;

    @Mock
    private TrainingTypeMapper trainingTypeMapper;

    @InjectMocks
    private TrainingTypeServiceImpl trainingTypeService;

    private TrainingType trainingType;

    @BeforeEach
    void setUp() {
        trainingType = new TrainingType();
        trainingType.setDescription("Yoga");
    }

    @Test
    void testGetTrainingByTrainingName_Success() {
        when(trainingTypeRepository.findTrainingByTrainingName("Yoga")).thenReturn(Optional.of(trainingType));

        TrainingType result = trainingTypeService.getTrainingByTrainingName("Yoga");

        assertNotNull(result);
        assertEquals("Yoga", result.getDescription());
    }

    @Test
    void testGetTrainingByTrainingName_NotFound() {
        when(trainingTypeRepository.findTrainingByTrainingName("NonExistent")).thenReturn(Optional.empty());

        assertThrows(TrainingTypeNotFoundException.class, () -> trainingTypeService.getTrainingByTrainingName("NonExistent"));
    }

    @Test
    void testFindAllTrainingTypes() {
        List<TrainingType> trainingTypes = List.of(trainingType);
        List<TrainingTypeResponseDTO> responseDTOs = List.of(new TrainingTypeResponseDTO(UUID.randomUUID(),"Yoga"));

        when(trainingTypeRepository.findAll()).thenReturn(trainingTypes);
        when(trainingTypeMapper.toTrainingTypeResponseDTO(any(TrainingType.class))).thenReturn(responseDTOs.get(0));

        List<TrainingTypeResponseDTO> result = trainingTypeService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Yoga", result.get(0).getTrainingType());
    }
}