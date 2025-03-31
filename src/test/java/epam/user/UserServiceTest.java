package epam.user;

import epam.shared.security.dto.ChangePasswordRequestDTO;
import epam.user.repository.UserRepository;
import epam.user.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testExistsByUsername() {
        lenient().when(userRepository.existsByUsername("testUser".toLowerCase())).thenReturn(true);

        assertTrue(userService.existsByUsername("testuser"));
        verify(userRepository).existsByUsername("testuser");
    }

    @Test
    void testExistsByUsernameAndPassword() {
        lenient().when(userRepository.existsByUsernameAndPassword("testuser", "password".toLowerCase()))
                .thenReturn(true);

        assertTrue(userService.existsByUsernameAndPassword("testuser", "password"));
        verify(userRepository, times(1)).existsByUsernameAndPassword("testuser", "password");
    }

    @Test
    void testChangePassword_Success() {
        ChangePasswordRequestDTO requestDTO = new ChangePasswordRequestDTO("testUser", "oldPassword", "newPassword");
        when(userRepository.existsByUsername("testUser")).thenReturn(true);
        when(userRepository.changePassword(requestDTO)).thenReturn(true);

        assertTrue(userService.changePassword(requestDTO));
        verify(userRepository, times(1)).existsByUsername("testUser");
        verify(userRepository, times(1)).changePassword(requestDTO);
    }

    @Test
    void testChangePassword_Failure() {
        ChangePasswordRequestDTO requestDTO = new ChangePasswordRequestDTO("testUser", "oldPassword", "newPassword");
        when(userRepository.existsByUsername("testUser")).thenReturn(false);

        assertFalse(userService.changePassword(requestDTO));
        verify(userRepository, times(1)).existsByUsername("testUser");
        verify(userRepository, times(0)).changePassword(requestDTO);
    }
}