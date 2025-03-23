package epam.repository;

import epam.shared.security.dto.ChangePasswordRequestDTO;
import epam.user.entity.User;
import epam.user.repository.impl.UserRepositoryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {

    @InjectMocks
    private UserRepositoryImpl repository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<User> userQuery;

    @Mock
    private TypedQuery<Boolean> booleanQuery;

    @Mock
    private TypedQuery<Long> longQuery;

    @Test
    void testExistsByUsername_ShouldReturnTrueIfExists() {
        String username = "testUser";
        when(entityManager.createQuery(anyString(), eq(Boolean.class))).thenReturn(booleanQuery);
        when(booleanQuery.setParameter("username", username)).thenReturn(booleanQuery);
        when(booleanQuery.getSingleResult()).thenReturn(true);

        assertTrue(repository.existsByUsername(username));
    }

    @Test
    void testExistsByUsername_ShouldReturnFalseIfNotExists() {
        String username = "unknownUser";
        when(entityManager.createQuery(anyString(), eq(Boolean.class))).thenReturn(booleanQuery);
        when(booleanQuery.setParameter("username", username)).thenReturn(booleanQuery);
        when(booleanQuery.getSingleResult()).thenReturn(false);

        assertFalse(repository.existsByUsername(username));
    }

    @Test
    void testExistsByUsernameAndPassword_ShouldReturnTrueIfExists() {
        String username = "testUser";
        String password = "securePassword";

        when(entityManager.createQuery(anyString(), eq(Long.class))).thenReturn(longQuery);
        when(longQuery.setParameter("username", username)).thenReturn(longQuery);
        when(longQuery.setParameter("password", password)).thenReturn(longQuery);
        when(longQuery.getSingleResult()).thenReturn(1L);

        assertTrue(repository.existsByUsernameAndPassword(username, password));
    }

    @Test
    void testExistsByUsernameAndPassword_ShouldReturnFalseIfNotExists() {
        String username = "testUser";
        String password = "wrongPassword";

        when(entityManager.createQuery(anyString(), eq(Long.class))).thenReturn(longQuery);
        when(longQuery.setParameter("username", username)).thenReturn(longQuery);
        when(longQuery.setParameter("password", password)).thenReturn(longQuery);
        when(longQuery.getSingleResult()).thenReturn(0L);

        assertFalse(repository.existsByUsernameAndPassword(username, password));
    }

    @Test
    void testChangePassword_ShouldReturnFalseIfOldPasswordIncorrect() {
        ChangePasswordRequestDTO dto = ChangePasswordRequestDTO.builder()
                .username("testUser")
                .newPassword("newPassword")
                .oldPassword("oldPassword")
                .build();

        when(entityManager.createQuery(anyString(), eq(Long.class))).thenReturn(longQuery);
        when(longQuery.setParameter("username", dto.getUsername())).thenReturn(longQuery);
        when(longQuery.setParameter("password", dto.getOldPassword())).thenReturn(longQuery);
        when(longQuery.getSingleResult()).thenReturn(0L);

        assertFalse(repository.changePassword(dto));
    }

    @Test
    void testGetByUsername_ShouldReturnUserIfExists() {
        String username = "testUser";
        User mockUser = new User();
        mockUser.setUsername(username);

        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        CriteriaQuery<User> criteriaQuery = mock(CriteriaQuery.class);
        Root<User> root = mock(Root.class);

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(User.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(User.class)).thenReturn(root);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(userQuery);
        when(userQuery.getSingleResult()).thenReturn(mockUser);

        User result = repository.getByUsername(username);

        assertNotNull(result);
        assertEquals(username, result.getUsername());
    }

    @Test
    void testGetByUsername_ShouldThrowExceptionIfNotFound() {
        String username = "unknownUser";

        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        CriteriaQuery<User> criteriaQuery = mock(CriteriaQuery.class);
        Root<User> root = mock(Root.class);

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(User.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(User.class)).thenReturn(root);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(userQuery);
        when(userQuery.getSingleResult()).thenThrow(new RuntimeException("No result found"));

        assertThrows(RuntimeException.class, () -> repository.getByUsername(username));
    }
}
