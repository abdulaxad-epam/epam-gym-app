package epam.repository;

import epam.entity.Trainee;
import epam.entity.User;
import epam.exception.EntityManagerInsertException;
import epam.repository.impl.TraineeRepositoryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TraineeRepositoryTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private TraineeRepositoryImpl traineeRepository;

    private UUID traineeId;
    private Trainee trainee;
    private User user;

    @BeforeEach
    void setUp() {
        traineeId = UUID.randomUUID();
        user = User.builder()
                .firstname("firstname")
                .lastname("lastname")
                .isActive(true)
                .build();
        user.setUsername("testUser");

        trainee = new Trainee();
        trainee.setTraineeId(traineeId);
        trainee.setUser(user);
    }

    @Test
    void testInsert_Success() {
        when(entityManager.getTransaction()).thenReturn(mock(EntityTransaction.class));

        Trainee insertedTrainee = traineeRepository.insert(trainee);
        assertNotNull(insertedTrainee);
        assertEquals("firstname.lastname", insertedTrainee.getUser().getUsername());

        verify(entityManager).persist(trainee);
        verify(entityManager.getTransaction()).commit();
    }

    @Test
    void testInsert_Exception() {
        when(entityManager.getTransaction()).thenReturn(mock(EntityTransaction.class));
        doThrow(new RuntimeException("Insert error")).when(entityManager).persist(trainee);

        assertThrows(EntityManagerInsertException.class, () -> traineeRepository.insert(trainee));

        verify(entityManager.getTransaction()).rollback();
    }

    @Test
    void testFindById_Success() {
        when(entityManager.find(Trainee.class, traineeId)).thenReturn(trainee);

        Optional<Trainee> result = traineeRepository.findById(traineeId);
        assertTrue(result.isPresent());
        assertEquals(traineeId, result.get().getTraineeId());
    }

    @Test
    void testFindById_NotFound() {
        when(entityManager.find(Trainee.class, traineeId)).thenReturn(null);

        Optional<Trainee> result = traineeRepository.findById(traineeId);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByUsername_Success() {
        TypedQuery<Trainee> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(Trainee.class))).thenReturn(query);
        when(query.setParameter("username", "testUser")).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(trainee));

        Optional<Trainee> result = traineeRepository.findByUsername("testUser");
        assertTrue(result.isPresent());
        assertEquals("testUser", result.get().getUser().getUsername());
    }

    @Test
    void testFindByUsername_NotFound() {
        TypedQuery<Trainee> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(Trainee.class))).thenReturn(query);
        when(query.setParameter("username", "unknownUser")).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of()); // Return empty list

        Optional<Trainee> result = traineeRepository.findByUsername("unknownUser");
        assertTrue(result.isEmpty());
    }


    @Test
    void testFindAll() {
        TypedQuery<Trainee> query = mock(TypedQuery.class);
        when(entityManager.createQuery("from Trainee", Trainee.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(trainee));

        List<Trainee> trainees = traineeRepository.findAll();
        assertEquals(1, trainees.size());
        assertEquals(traineeId, trainees.get(0).getTraineeId());
    }

    @Test
    void testExistsById_True() {
        TypedQuery<Boolean> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(Boolean.class))).thenReturn(query);
        when(query.setParameter("id", traineeId)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(true);

        assertTrue(traineeRepository.existsById(traineeId));
    }

    @Test
    void testExistsById_False() {
        TypedQuery<Boolean> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(Boolean.class))).thenReturn(query);
        when(query.setParameter("id", traineeId)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(false);

        assertFalse(traineeRepository.existsById(traineeId));
    }

}
