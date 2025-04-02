package epam.repository;


import epam.entity.TrainerTrainee;
import epam.repository.impl.TrainerTraineeRepositoryImpl;
import epam.entity.Trainee;
import epam.entity.Trainer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.instancio.Instancio;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(InstancioExtension.class)
public class TrainerTraineeRepositoryTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private EntityTransaction transaction;

    @Mock
    private Query mockQuery;

    @Mock
    private TypedQuery<Trainer> trainerQuery;

    @Mock
    private TypedQuery<TrainerTrainee> trainerTraineeQuery;

    @Mock
    private TypedQuery<Boolean> booleanQuery;

    @InjectMocks
    private TrainerTraineeRepositoryImpl trainerTraineeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(entityManager.getTransaction()).thenReturn(transaction);
        when(transaction.isActive()).thenReturn(false);
    }

    @Test
    void testAssignTrainerToTrainee_Success() {
        TrainerTrainee trainerTrainee = Instancio.create(TrainerTrainee.class);

        when(entityManager.getTransaction().isActive()).thenReturn(false);
        when(entityManager.merge(trainerTrainee.getTrainer())).thenReturn(trainerTrainee.getTrainer());
        when(entityManager.merge(trainerTrainee.getTrainee())).thenReturn(trainerTrainee.getTrainee());

        trainerTraineeRepository.assignTrainerToTrainee(trainerTrainee);

        verify(entityManager, times(4)).getTransaction();
        verify(entityManager, times(1)).merge(trainerTrainee.getTrainer());
        verify(entityManager, times(1)).merge(trainerTrainee.getTrainee());
        verify(entityManager, times(1)).merge(trainerTrainee);
        verify(entityManager.getTransaction(), times(1)).begin();
        verify(entityManager.getTransaction(), times(1)).commit();
    }

    @Test
    void testFindByUsernameNotAssignedToTrainee_Success() {
        String username = "trainee123";
        List<Trainer> trainers = List.of(Instancio.create(Trainer.class));

        when(entityManager.createQuery(anyString(), eq(Trainer.class))).thenReturn(trainerQuery);
        when(trainerQuery.setParameter("username", username)).thenReturn(trainerQuery);
        when(trainerQuery.getResultList()).thenReturn(trainers);

        List<Trainer> result = trainerTraineeRepository.findByUsernameNotAssignedToTrainee(username);

        assertNotNull(result);
        assertEquals(trainers, result);
        verify(trainerQuery, times(1)).setParameter("username", username);
        verify(trainerQuery, times(1)).getResultList();
    }

    @Test
    void testRemoveTraineeOfTrainer_Success() {
        Trainee trainee = Instancio.create(Trainee.class);
        Trainer trainer = Instancio.create(Trainer.class);

        when(entityManager.createQuery(anyString())).thenReturn(mockQuery);
        when(mockQuery.setParameter("trainer", trainer)).thenReturn(mockQuery);
        when(mockQuery.setParameter("trainee", trainee)).thenReturn(mockQuery);

        trainerTraineeRepository.removeTraineeOfTrainer(trainee, trainer);

        verify(mockQuery, times(1)).executeUpdate();
    }

    @Test
    void testTrainerHasTrainee_True() {
        UUID trainerId = UUID.randomUUID();
        UUID traineeId = UUID.randomUUID();

        when(entityManager.createQuery(anyString(), eq(Boolean.class))).thenReturn(booleanQuery);
        when(booleanQuery.setParameter("traineeId", traineeId)).thenReturn(booleanQuery);
        when(booleanQuery.setParameter("trainerId", trainerId)).thenReturn(booleanQuery);
        when(booleanQuery.getSingleResult()).thenReturn(true);

        boolean result = trainerTraineeRepository.trainerHasTrainee(trainerId, traineeId);

        assertTrue(result);
        verify(booleanQuery, times(1)).getSingleResult();
    }

    @Test
    void testFindByTrainee_User_Username_Success() {
        String username = "trainee123";
        List<TrainerTrainee> trainerTrainees = List.of(Instancio.create(TrainerTrainee.class));

        when(entityManager.createQuery(anyString(), eq(TrainerTrainee.class))).thenReturn(trainerTraineeQuery);
        when(trainerTraineeQuery.setParameter("username", username)).thenReturn(trainerTraineeQuery);
        when(trainerTraineeQuery.getResultList()).thenReturn(trainerTrainees);

        List<TrainerTrainee> result = trainerTraineeRepository.findByTrainee_User_username(username);

        assertNotNull(result);
        assertEquals(trainerTrainees, result);
        verify(trainerTraineeQuery, times(1)).setParameter("username", username);
        verify(trainerTraineeQuery, times(1)).getResultList();
    }

    @Test
    void testRemoveAllByTraineeUsername_Success() {
        String username = "trainee123";

        when(entityManager.getTransaction().isActive()).thenReturn(false);
        when(entityManager.createQuery(anyString())).thenReturn(mockQuery);
        when(mockQuery.setParameter("username", username)).thenReturn(mockQuery);

        trainerTraineeRepository.removeAllByTraineeUsername(username);

        verify(entityManager.getTransaction(), times(1)).begin();
        verify(mockQuery, times(1)).executeUpdate();
        verify(entityManager.getTransaction(), times(1)).commit();
    }
}