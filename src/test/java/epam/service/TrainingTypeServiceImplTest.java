package epam.service;

import epam.entity.TrainingType;
import epam.repository.TrainingTypeRepository;
import epam.service.impl.TrainingTypServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TrainingTypeServiceImplTest {

    private TrainingTypeRepository trainingTypeRepository;
    private TrainingTypeService trainingTypeService;

    @BeforeEach
    void setUp() {
        trainingTypeRepository = mock(TrainingTypeRepository.class);
        trainingTypeService = new TrainingTypServiceImpl(trainingTypeRepository); // Fix the typo in the actual class!
    }

    @Test
    void testGetTrainingByTrainingName_Success() {
        String trainingName = "Strength Training";
        TrainingType trainingType = new TrainingType();

        when(trainingTypeRepository.findTrainingByTrainingName(trainingName)).thenReturn(trainingType);

        TrainingType result = trainingTypeService.getTrainingByTrainingName(trainingName);

        assertNotNull(result);
        verify(trainingTypeRepository, times(1)).findTrainingByTrainingName(trainingName);
    }

    @Test
    void testGetTrainingByTrainingName_NotFound() {
        String trainingName = "Nonexistent Training";

        when(trainingTypeRepository.findTrainingByTrainingName(trainingName)).thenReturn(null);

        TrainingType result = trainingTypeService.getTrainingByTrainingName(trainingName);

        assertNull(result);
    }

    @Test
    void testFindAllTrainingTypes_Success() {
        List<String> mockTrainingTypes = List.of("Strength Training", "Cardio", "Yoga");

        when(trainingTypeRepository.findAll()).thenReturn(mockTrainingTypes);

        List<String> result = trainingTypeService.findAll();

        assertEquals(3, result.size());
        assertTrue(result.contains("Strength Training"));
    }
}
