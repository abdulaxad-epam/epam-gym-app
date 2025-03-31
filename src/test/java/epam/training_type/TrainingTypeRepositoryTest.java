package epam.training_type;

import epam.training_type.entity.TrainingType;
import epam.training_type.repository.impl.TrainingTypeRepositoryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrainingTypeRepositoryTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<TrainingType> typedQuery;

    @InjectMocks
    private TrainingTypeRepositoryImpl trainingTypeRepository;

    private TrainingType trainingType;

    @BeforeEach
    void setUp() {
        trainingType = new TrainingType();
        trainingType.setDescription("Yoga");
    }

    @Test
    void testFindTrainingByTrainingName_Success() {
        when(entityManager.createQuery(anyString(), eq(TrainingType.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(anyString(), any())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(List.of(trainingType));

        Optional<TrainingType> result = trainingTypeRepository.findTrainingByTrainingName("Yoga");

        assertTrue(result.isPresent());
        assertEquals("Yoga", result.get().getDescription());
    }

    @Test
    void testFindTrainingByTrainingName_NotFound() {
        when(entityManager.createQuery(anyString(), eq(TrainingType.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(anyString(), any())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(List.of());

        Optional<TrainingType> result = trainingTypeRepository.findTrainingByTrainingName("NonExistent");

        assertFalse(result.isPresent());
    }

    @Test
    void testFindAllTrainingTypes() {
        when(entityManager.createQuery(anyString(), eq(TrainingType.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(List.of(trainingType));

        List<TrainingType> result = trainingTypeRepository.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Yoga", result.get(0).getDescription());
    }
}