package epam.entity;

import epam.config.HibernateConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
@ContextConfiguration(classes = HibernateConfig.class)
class TraineeTest {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;
    private EntityTransaction transaction;

    @BeforeEach
    void setUp() {
        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();
        transaction.begin();
    }

    @AfterEach
    void tearDown() {
        if (transaction.isActive()) {
            transaction.rollback();
        }
        entityManager.close();
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
                .dateOfBirth(LocalDateTime.of(1995, 5, 15, 0, 0))
                .address("123 Main Street")
                .user(user)
                .build();

        // When
        entityManager.persist(user);
        entityManager.persist(trainee);
        entityManager.flush();
        entityManager.clear();

        // Then
        Trainee foundTrainee = entityManager.find(Trainee.class, trainee.getTraineeId());
        assertThat(foundTrainee).isNotNull();
        assertThat(foundTrainee.getUser()).isNotNull();
        assertThat(foundTrainee.getUser().getUsername()).isEqualTo("username");
    }


}
