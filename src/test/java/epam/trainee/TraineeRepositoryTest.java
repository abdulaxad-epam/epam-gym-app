package epam.trainee;

import epam.shared.exception.exception.EntityManagerInsertException;
import epam.trainee.entity.Trainee;
import epam.trainee.repository.impl.TraineeRepositoryImpl;
import epam.user.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.apache.commons.logging.Log;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TraineeRepositoryTest {
    @Mock
    private EntityManager entityManager;

    @Mock
    private Log log;

    @InjectMocks
    private TraineeRepositoryImpl traineeRepository;

    private Trainee trainee;

    private User user;

    @BeforeEach
    void setUp() {
        trainee = new Trainee();
        trainee.setUser(new User());

        trainee.setTraineeId(UUID.randomUUID());
    }

    @Test
    void testInsert_Success() {
        // Mocking the EntityTransaction
        EntityTransaction transaction = mock(EntityTransaction.class);
        when(entityManager.getTransaction()).thenReturn(transaction);
        when(transaction.isActive()).thenReturn(true);

        Trainee result = traineeRepository.insert(trainee);

        verify(entityManager).persist(trainee);
        assertNotNull(result);
    }

    @Test
    void testInsert_Failure() {
        // Mocking the EntityTransaction
        EntityTransaction transaction = mock(EntityTransaction.class);
        when(entityManager.getTransaction()).thenReturn(transaction);
        when(transaction.isActive()).thenReturn(true);
        doThrow(new RuntimeException("Insert Error")).when(entityManager).persist(any(Trainee.class));

        assertThrows(EntityManagerInsertException.class, () -> traineeRepository.insert(trainee));
        verify(transaction).rollback();  // Verifying that rollback() is called
    }

    @Test
    void testFindByUsername_Found() {
        TypedQuery<Trainee> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(Trainee.class))).thenReturn(query);
        when(query.setParameter(anyString(), anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(trainee));

        Optional<Trainee> result = traineeRepository.findByUsername("testuser");

        assertTrue(result.isPresent());
        assertEquals(trainee, result.get());
    }

    @Test
    void testFindByUsername_NotFound() {
        TypedQuery<Trainee> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(Trainee.class))).thenReturn(query);
        when(query.setParameter(anyString(), anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.emptyList());

        Optional<Trainee> result = traineeRepository.findByUsername("testuser");

        assertTrue(result.isEmpty());
    }

    @Test
    void testExistsById() {
        TypedQuery<Boolean> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(Boolean.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(true);

        assertTrue(traineeRepository.existsById(UUID.randomUUID()));
    }

    @Test
    void testExistsByUsername() {
        TypedQuery<Boolean> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(Boolean.class))).thenReturn(query);
        when(query.setParameter(anyString(), anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(true);

        assertTrue(traineeRepository.existsByUsername("testuser"));
    }

    @Test
    void testDeleteTraineeByUsername() {
        EntityTransaction transaction = mock(EntityTransaction.class);
        when(entityManager.getTransaction()).thenReturn(transaction);
        when(transaction.isActive()).thenReturn(true);

        TypedQuery<Trainee> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(Trainee.class))).thenReturn(query);
        when(query.setParameter(anyString(), anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(trainee);

        traineeRepository.deleteTraineeByUsername("testuser");

        verify(entityManager).remove(trainee);
    }


}