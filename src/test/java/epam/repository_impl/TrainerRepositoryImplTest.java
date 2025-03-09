package epam.repository_impl;

import epam.entity.Trainer;
import epam.entity.User;
import epam.repository.repository_impl.TrainerRepositoryImpl;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainerRepositoryImplTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private EntityTransaction entityTransaction;

    @InjectMocks
    private TrainerRepositoryImpl trainerRepository;

    private Trainer trainer;
    private UUID trainerId;

    @BeforeEach
    void setUp() {
        trainerId = UUID.randomUUID();
        User user = User.builder()
                .firstname("firstname")
                .lastname("lastname")
                .isActive(true)
                .build();
        user.setUsername("testTrainer");

        trainer = new Trainer();
        trainer.setTrainerId(trainerId);
        trainer.setUser(user);
    }

    @Test
    void testInsertTrainer_NullTrainer() {
        assertThrows(IllegalArgumentException.class, () -> trainerRepository.insert(null));
    }


    @Test
    void testFindByUsername_Success() {
        TypedQuery<Trainer> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(Trainer.class))).thenReturn(query);
        when(query.setParameter("username", "testTrainer")).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(trainer));

        Optional<Trainer> result = trainerRepository.findByUsername("testTrainer");

        assertTrue(result.isPresent());
        assertEquals("testTrainer", result.get().getUser().getUsername());
    }

    @Test
    void testFindByUsername_NotFound() {
        TypedQuery<Trainer> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(Trainer.class))).thenReturn(query);
        when(query.setParameter("username", "unknownUser")).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of());

        Optional<Trainer> result = trainerRepository.findByUsername("unknownUser");

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindById_Success() {
        when(entityManager.find(Trainer.class, trainerId)).thenReturn(trainer);

        Optional<Trainer> result = trainerRepository.findById(trainerId);

        assertTrue(result.isPresent());
        assertEquals(trainerId, result.get().getTrainerId());
    }

    @Test
    void testFindById_NotFound() {
        when(entityManager.find(Trainer.class, trainerId)).thenReturn(null);

        Optional<Trainer> result = trainerRepository.findById(trainerId);

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindAll() {
        TypedQuery<Trainer> query = mock(TypedQuery.class);
        when(entityManager.createQuery("from Trainer", Trainer.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(trainer));

        List<Trainer> trainers = trainerRepository.findAll();

        assertEquals(1, trainers.size());
        assertEquals(trainerId, trainers.get(0).getTrainerId());
    }

    @Test
    void testExistsById_True() {
        TypedQuery<Boolean> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(Boolean.class))).thenReturn(query);
        when(query.setParameter("id", trainerId)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(true);

        assertTrue(trainerRepository.existsById(trainerId));
    }

    @Test
    void testExistsById_False() {
        TypedQuery<Boolean> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(Boolean.class))).thenReturn(query);
        when(query.setParameter("id", trainerId)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(false);

        assertFalse(trainerRepository.existsById(trainerId));
    }

}
