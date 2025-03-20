package epam.repository;

import epam.entity.TrainingType;
import epam.repository.impl.TrainingTypeRepositoryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TrainingTypeRepositoryTest {

    private TrainingTypeRepositoryImpl repository;
    private EntityManager entityManager;
    private TypedQuery<TrainingType> trainingTypeQuery;
    private TypedQuery<String> stringQuery;

    @BeforeEach
    void setUp() {
        entityManager = mock(EntityManager.class);
        trainingTypeQuery = mock(TypedQuery.class);
        stringQuery = mock(TypedQuery.class);

        repository = new TrainingTypeRepositoryImpl(entityManager);
    }

    @Test
    void testFindTrainingByTrainingName_ShouldReturnTrainingType() {
        String trainingName = "Yoga";
        TrainingType mockTrainingType = new TrainingType();
        mockTrainingType.setDescription(trainingName);

        when(entityManager.createQuery(anyString(), eq(TrainingType.class))).thenReturn(trainingTypeQuery);
        when(trainingTypeQuery.setParameter("trainingName", trainingName)).thenReturn(trainingTypeQuery);
        when(trainingTypeQuery.getSingleResult()).thenReturn(mockTrainingType);

        TrainingType result = repository.findTrainingByTrainingName(trainingName);

        assertNotNull(result);
        assertEquals(trainingName, result.getDescription());
        verify(entityManager).createQuery(anyString(), eq(TrainingType.class));
        verify(trainingTypeQuery).setParameter("trainingName", trainingName);
        verify(trainingTypeQuery).getSingleResult();
    }

    @Test
    void testFindTrainingByTrainingName_WhenNoResult_ShouldThrowException() {
        String trainingName = "InvalidTraining";

        when(entityManager.createQuery(anyString(), eq(TrainingType.class))).thenReturn(trainingTypeQuery);
        when(trainingTypeQuery.setParameter("trainingName", trainingName)).thenReturn(trainingTypeQuery);
        when(trainingTypeQuery.getSingleResult()).thenThrow(new RuntimeException("No result found"));

        assertThrows(RuntimeException.class, () -> repository.findTrainingByTrainingName(trainingName));

        verify(entityManager).createQuery(anyString(), eq(TrainingType.class));
        verify(trainingTypeQuery).setParameter("trainingName", trainingName);
        verify(trainingTypeQuery).getSingleResult();
    }

    @Test
    void testFindAll_ShouldReturnListOfTrainingNames() {
        List<String> mockTrainingNames = Arrays.asList("Yoga", "Pilates", "CrossFit");

        when(entityManager.createQuery(anyString(), eq(String.class))).thenReturn(stringQuery);
        when(stringQuery.getResultList()).thenReturn(mockTrainingNames);

        List<String> result = repository.findAll();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("Yoga", result.get(0));
        assertEquals("Pilates", result.get(1));
        assertEquals("CrossFit", result.get(2));

        verify(entityManager).createQuery(anyString(), eq(String.class));
        verify(stringQuery).getResultList();
    }

    @Test
    void testFindAll_WhenNoTrainingTypesExist_ShouldReturnEmptyList() {
        when(entityManager.createQuery(anyString(), eq(String.class))).thenReturn(stringQuery);
        when(stringQuery.getResultList()).thenReturn(List.of());

        List<String> result = repository.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(entityManager).createQuery(anyString(), eq(String.class));
        verify(stringQuery).getResultList();
    }

    @Test
    void testFindAll_WhenQueryFails_ShouldThrowException() {
        when(entityManager.createQuery(anyString(), eq(String.class))).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> repository.findAll());

        verify(entityManager).createQuery(anyString(), eq(String.class));
    }
}
