package epam.repository_impl;

import epam.entity.Training;
import epam.repository.repository_impl.TrainingRepositoryImpl;
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
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainingRepositoryImplTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private TrainingRepositoryImpl trainingRepository;

    private Training training;
    private UUID trainingId;

    @BeforeEach
    void setUp() {
        trainingId = UUID.randomUUID();
        training = Training.builder().trainingId(trainingId).build();
    }
    @Test
    void findById_ShouldReturnTraining_WhenExists() {
        // Arrange
        when(entityManager.find(Training.class, trainingId)).thenReturn(training);

        // Act
        Training found = trainingRepository.findById(trainingId);

        // Assert
        assertThat(found).isEqualTo(training);
    }

    @Test
    void findAll_ShouldReturnListOfTrainings() {
        // Arrange
        TypedQuery<Training> query = mock(TypedQuery.class);
        when(entityManager.createQuery("SELECT t FROM Training t", Training.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(training));

        // Act
        List<Training> trainings = trainingRepository.findAll();

        // Assert
        assertThat(trainings).containsExactly(training);
    }

    @Test
    void existsById_ShouldReturnTrue_WhenExists() {
        // Arrange
        TypedQuery<Boolean> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(Boolean.class))).thenReturn(query);
        when(query.setParameter("id", trainingId)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(true);

        // Act
        boolean exists = trainingRepository.existsById(trainingId);

        // Assert
        assertThat(exists).isTrue();
    }

    @Test
    void getIdByUsername_ShouldReturnUUID_WhenExists() {
        // Arrange
        TypedQuery<UUID> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(UUID.class))).thenReturn(query);
        when(query.setParameter("username", "testUser")).thenReturn(query);
        when(query.getSingleResult()).thenReturn(trainingId);

        // Act
        Optional<UUID> result = trainingRepository.getIdByUsername("testUser");

        // Assert
        assertThat(result).isPresent().contains(trainingId);
    }

    @Test
    void findTrainingsByTrainee_ShouldReturnList_WhenTrainingsExist() {
        // Arrange
        TypedQuery<Training> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(Training.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(training));

        // Act
        List<Training> trainings = trainingRepository.findTrainingsByTrainee("testUser");

        // Assert
        assertThat(trainings).containsExactly(training);
    }
}
