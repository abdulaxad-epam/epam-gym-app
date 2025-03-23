package epam.repository;

import epam.user.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TrainingUserRepositoryTest {

    private TrainingUserRepository repository;
    private EntityManager entityManager;
    private TypedQuery<Long> typedQuery;

    @BeforeEach
    void setUp() {
        entityManager = mock(EntityManager.class);
        typedQuery = mock(TypedQuery.class);

        repository = new TrainingUserRepository() {
            @Override
            public EntityManager getEntityManager() {
                return entityManager;
            }
        };
    }

    @Test
    void testUpdateUsername_WhenNoExistingUsernames_ShouldSetBaseUsername() {
        User user = new User();
        user.setFirstname("John");
        user.setLastname("Doe");

        when(entityManager.createQuery(anyString(), eq(Long.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(anyString(), anyString())).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenReturn(0L);

        User updatedUser = repository.updateUsername(user);

        assertEquals("John.Doe", updatedUser.getUsername());
        verify(entityManager).createQuery(anyString(), eq(Long.class));
    }

    @Test
    void testUpdateUsername_WhenExistingUsersExist_ShouldAppendSerialNumber() {
        User user = new User();
        user.setFirstname("John");
        user.setLastname("Doe");

        when(entityManager.createQuery(anyString(), eq(Long.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(anyString(), anyString())).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenReturn(5L); // Simulate 5 existing users

        User updatedUser = repository.updateUsername(user);

        assertEquals("John.Doe5", updatedUser.getUsername());
    }

    @Test
    void testUpdateUsername_WhenQueryThrowsException_ShouldSetBaseUsername() {
        User user = new User();
        user.setFirstname("Alice");
        user.setLastname("Smith");

        when(entityManager.createQuery(anyString(), eq(Long.class))).thenThrow(new RuntimeException("Database Error"));

        User updatedUser = repository.updateUsername(user);

        assertEquals("Alice.Smith", updatedUser.getUsername()); // Should default to base username
    }
}
