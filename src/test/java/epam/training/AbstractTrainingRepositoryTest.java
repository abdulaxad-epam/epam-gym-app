package epam.training;

import epam.shared.exception.exception.DateConversionException;
import epam.training.entity.Training;
import epam.training.repository.AbstractTrainingRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AbstractTrainingRepositoryTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Training> typedQuery;

    @InjectMocks
    private AbstractTrainingRepository trainingRepository = new AbstractTrainingRepository() {
        @Override
        public EntityManager entityManager() {
            return entityManager;
        }
    };

    private String username;
    private String userRole;
    private String periodFrom;
    private String periodTo;
    private Map<String, Object> additionalFilters;
    private List<Training> trainingList;

    @BeforeEach
    void setUp() {
        username = "testUser";
        userRole = "trainee";
        periodFrom = "2024-01-01";
        periodTo = "2024-12-31";
        additionalFilters = new HashMap<>();
        trainingList = List.of(new Training());
    }

    @Test
    void testGetTrainings_Success() {
        when(entityManager.createQuery(anyString(), eq(Training.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(anyString(), any())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(trainingList);

        Optional<List<Training>> result = trainingRepository.getTrainings(username, userRole, periodFrom, periodTo, additionalFilters);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().size());
    }

    @Test
    void testGetTrainings_EmptyResult() {
        when(entityManager.createQuery(anyString(), eq(Training.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(anyString(), any())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Collections.emptyList());

        Optional<List<Training>> result = trainingRepository.getTrainings(username, userRole, periodFrom, periodTo, additionalFilters);

        assertTrue(result.isPresent());
        assertTrue(result.get().isEmpty());
    }

    @Test
    void testGetTrainings_InvalidDateFormat() {
        String invalidDate = "invalid-date";

        assertThrows(DateConversionException.class, () -> trainingRepository.getTrainings(username, userRole, invalidDate, periodTo, additionalFilters));
    }


}