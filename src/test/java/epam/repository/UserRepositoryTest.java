package epam.repository;

import epam.entity.User;
import epam.repository.impl.UserRepositoryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private EntityTransaction entityTransaction;

    @InjectMocks
    private UserRepositoryImpl userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("testUser");
        user.setPassword("oldPassword");
        user.setIsActive(true);

        when(entityManager.getTransaction()).thenReturn(entityTransaction);
    }

    @Test
    void testToggleActiveStatus() {
        when(entityManager.merge(any(User.class))).thenReturn(user);

        boolean newStatus = userRepository.toggleActiveStatus(user);

        verify(entityTransaction).begin();
        verify(entityManager).merge(any(User.class));
        verify(entityTransaction).commit();

        assertFalse(newStatus);
    }

}
