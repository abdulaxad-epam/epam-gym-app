package epam.repository;

import epam.entity.User;
import epam.repository.impl.UserRepositoryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Boolean> booleanQuery;

    @Mock
    private TypedQuery<Long> longQuery;

    @Mock
    private TypedQuery<User> userQuery;

    @Mock
    private TypedQuery<Integer> updateQuery;

    @InjectMocks
    private UserRepositoryImpl userRepository;

    @BeforeEach
    void setUp() {
        lenient().when(entityManager.createQuery(anyString(), eq(Boolean.class))).thenReturn(booleanQuery);
        lenient().when(entityManager.createQuery(anyString(), eq(Long.class))).thenReturn(longQuery);
        lenient().when(entityManager.createQuery(anyString(), eq(User.class))).thenReturn(userQuery);
    }

    @Test
    void testExistsByUsername() {
        when(booleanQuery.setParameter("username", "testUser")).thenReturn(booleanQuery);
        when(booleanQuery.getSingleResult()).thenReturn(true);

        boolean result = userRepository.existsByUsername("testUser");
        assertTrue(result);
    }

    @Test
    void testExistsByUsernameAndPassword() {
        when(longQuery.setParameter("username", "testUser")).thenReturn(longQuery);
        when(longQuery.setParameter("password", "testPass")).thenReturn(longQuery);
        when(longQuery.getSingleResult()).thenReturn(1L);

        boolean result = userRepository.existsByUsernameAndPassword("testUser", "testPass");
        assertTrue(result);
    }

    @Test
    void testFindUserByUsername() {
        User mockUser = new User();
        mockUser.setUsername("testUser");
        when(userQuery.setParameter("username", "testUser")).thenReturn(userQuery);
        when(userQuery.getSingleResult()).thenReturn(mockUser);

        Optional<User> result = userRepository.findUserByUsername("testUser");
        assertTrue(result.isPresent());
        assertEquals("testUser", result.get().getUsername());
    }
}
