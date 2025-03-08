package epam.repository_impl;

import epam.entity.User;
import epam.request_dto.ChangePasswordRequestDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryImplTest {

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
