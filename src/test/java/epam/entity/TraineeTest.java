package epam.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TraineeTest {

    @Mock
    private EntityManagerFactory entityManagerFactory;

    @Mock
    private EntityManager entityManager;

    @Mock
    private EntityTransaction transaction;

    @InjectMocks
    private Trainee trainee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldPersistTraineeWithUser() {
        // Given
        User user = User.builder()
                .firstname("First Name")
                .lastname("Last Name")
                .username("username")
                .password("password")
                .isActive(true)
                .build();

        Trainee trainee = Trainee.builder()
                .traineeId(UUID.randomUUID())
                .dateOfBirth(LocalDateTime.of(1995, 5, 15, 0, 0))
                .address("123 Main Street")
                .user(user)
                .build();

        // When
        when(entityManager.find(Trainee.class, trainee.getTraineeId())).thenReturn(trainee);

        entityManager.persist(user);
        entityManager.persist(trainee);

        // Then
        Trainee foundTrainee = entityManager.find(Trainee.class, trainee.getTraineeId());
        assertThat(foundTrainee).isNotNull();
        assertThat(foundTrainee.getUser()).isNotNull();
        assertThat(foundTrainee.getUser().getUsername()).isEqualTo("username");

        // Verify interactions
        verify(entityManager, times(1)).persist(user);
        verify(entityManager, times(1)).persist(trainee);
        verify(entityManager, times(1)).find(Trainee.class, trainee.getTraineeId());
    }
}
