package epam.repository;

import epam.training.entity.Training;
import epam.training.repository.impl.TrainingRepositoryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class TrainingRepositoryTest {

    @InjectMocks
    private TrainingRepositoryImpl repository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Training> trainingQuery;

    @Mock
    private TypedQuery<Boolean> booleanQuery;

    @Mock
    private TypedQuery<UUID> uuidQuery;

    @Mock
    private TypedQuery<String> stringQuery;

    @Test
    void testFindById_ShouldReturnTraining() {
        UUID id = UUID.randomUUID();
        Training training = new Training();
        when(entityManager.find(Training.class, id)).thenReturn(training);

        Training result = repository.findById(id);

        assertNotNull(result);
        assertEquals(training, result);
    }

    @Test
    void testFindAll_ShouldReturnListOfTrainings() {
        List<Training> mockTrainings = Arrays.asList(new Training(), new Training());
        when(entityManager.createQuery(anyString(), eq(Training.class))).thenReturn(trainingQuery);
        when(trainingQuery.getResultList()).thenReturn(mockTrainings);

        List<Training> result = repository.findAll();

        assertEquals(2, result.size());
    }

    @Test
    void testExistsById_ShouldReturnTrueIfExists() {
        UUID id = UUID.randomUUID();
        when(entityManager.createQuery(anyString(), eq(Boolean.class))).thenReturn(booleanQuery);
        when(booleanQuery.setParameter("id", id)).thenReturn(booleanQuery);
        when(booleanQuery.getSingleResult()).thenReturn(true);

        assertTrue(repository.existsById(id));
    }

    @Test
    void testExistsById_ShouldReturnFalseIfNotExists() {
        UUID id = UUID.randomUUID();
        when(entityManager.createQuery(anyString(), eq(Boolean.class))).thenReturn(booleanQuery);
        when(booleanQuery.setParameter("id", id)).thenReturn(booleanQuery);
        when(booleanQuery.getSingleResult()).thenReturn(false);

        assertFalse(repository.existsById(id));
    }

    @Test
    void testGetIdByUsername_ShouldReturnUUID() {
        UUID expectedId = UUID.randomUUID();
        String username = "testUser";

        when(entityManager.createQuery(anyString(), eq(UUID.class))).thenReturn(uuidQuery);
        when(uuidQuery.setParameter("username", username)).thenReturn(uuidQuery);
        when(uuidQuery.getSingleResult()).thenReturn(expectedId);

        Optional<UUID> result = repository.getIdByUsername(username);

        assertTrue(result.isPresent());
        assertEquals(expectedId, result.get());
    }

    @Test
    void testGetIdByUsername_WhenNoResult_ShouldReturnEmptyOptional() {
        String username = "unknownUser";

        when(entityManager.createQuery(anyString(), eq(UUID.class))).thenReturn(uuidQuery);
        when(uuidQuery.setParameter("username", username)).thenReturn(uuidQuery);
        when(uuidQuery.getSingleResult()).thenReturn(null);

        Optional<UUID> result = repository.getIdByUsername(username);

        assertTrue(result.isEmpty());
    }

    @Test
    void testGetByCriteria_ShouldReturnFilteredTrainings() {
        String username = "testUser";
        LocalDate fromDate = LocalDate.of(2024, 1, 1);
        LocalDate toDate = LocalDate.of(2024, 12, 31);
        String trainerName = "trainerOne";
        String trainingType = "Yoga";

        List<Training> mockTrainings = List.of(new Training());

        when(entityManager.createQuery(anyString(), eq(Training.class))).thenReturn(trainingQuery);
        when(trainingQuery.setParameter("username", username)).thenReturn(trainingQuery);
        when(trainingQuery.setParameter("fromDate", fromDate)).thenReturn(trainingQuery);
        when(trainingQuery.setParameter("toDate", toDate)).thenReturn(trainingQuery);
        when(trainingQuery.setParameter("trainerName", trainerName)).thenReturn(trainingQuery);
        when(trainingQuery.setParameter("trainingType", trainingType)).thenReturn(trainingQuery);
        when(trainingQuery.getResultList()).thenReturn(mockTrainings);

        List<Training> result = repository.getByCriteria(username, fromDate, toDate, trainerName, trainingType);

        assertEquals(1, result.size());
    }

}
