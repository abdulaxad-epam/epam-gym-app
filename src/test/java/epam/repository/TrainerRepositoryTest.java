package epam.repository;

import epam.exception.exception.EntityManagerInsertException;
import epam.entity.Trainer;
import epam.repository.impl.TrainerRepositoryImpl;
import epam.entity.Training;
import epam.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
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
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrainerRepositoryTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private EntityTransaction transaction;

    @InjectMocks
    private TrainerRepositoryImpl trainerRepository;

    private Trainer trainer;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("testTrainer");

        trainer = new Trainer();
        trainer.setTrainerId(UUID.randomUUID());
        trainer.setUser(user);

        lenient().when(entityManager.getTransaction()).thenReturn(transaction);
    }

    @Test
    void testInsert_Success() {
        // Arrange
        when(transaction.isActive()).thenReturn(true);

        // Act
        Trainer result = trainerRepository.insert(trainer);

        // Assert
        assertNotNull(result);
        verify(entityManager).persist(trainer);
        verify(transaction).commit();
    }

    @Test
    void testInsert_Failure() {
        // Arrange
        when(transaction.isActive()).thenReturn(true);
        doThrow(new RuntimeException("Insert Error")).when(entityManager).persist(any(Trainer.class));

        // Act & Assert
        assertThrows(EntityManagerInsertException.class, () -> trainerRepository.insert(trainer));
        verify(transaction).rollback();
    }

    @Test
    void testFindByUsername_Found() {
        // Arrange
        TypedQuery<Trainer> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(Trainer.class))).thenReturn(query);
        when(query.setParameter(anyString(), anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(trainer);

        // Act
        Optional<Trainer> result = trainerRepository.findByUsername("testTrainer");

        // Assert
        assertTrue(result.isPresent());
        assertEquals(trainer, result.get());
    }

    @Test
    void testFindByUsername_NotFound() {
        // Arrange
        TypedQuery<Trainer> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(Trainer.class))).thenReturn(query);
        when(query.setParameter(anyString(), anyString())).thenReturn(query);
        when(query.getSingleResult()).thenThrow(new RuntimeException("Not found"));

        // Act
        Optional<Trainer> result = trainerRepository.findByUsername("testTrainer");

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void testDeleteTrainerByUsername_Success() {
        // Arrange
        when(transaction.isActive()).thenReturn(true);
        when(entityManager.createQuery(anyString())).thenReturn(mock(TypedQuery.class));

        // Act
        trainerRepository.deleteTrainerByUsername("testTrainer");

        // Assert
        verify(entityManager).createQuery("DELETE FROM Trainer t WHERE t.user.username = :username ");
    }

    @Test
    void testGetTrainerTrainings_Empty() {
        // Arrange
        Optional<List<Training>> emptyTrainings = Optional.of(Collections.emptyList());
        TrainerRepository spyRepository = spy(trainerRepository);
        doReturn(emptyTrainings).when(spyRepository)
                .getTrainerTrainings(anyString(), anyString(), anyString(), anyString());

        // Act
        Optional<List<Training>> result = spyRepository.getTrainerTrainings("testTrainer", "2024-01-01", "2024-12-31", "testTrainee");

        // Assert
        assertTrue(result.isPresent());
        assertTrue(result.get().isEmpty());
    }
}
