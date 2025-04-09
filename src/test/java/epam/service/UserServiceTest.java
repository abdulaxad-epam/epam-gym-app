package epam.service;

import epam.dto.request_dto.ChangePasswordRequestDTO;
import epam.entity.User;
import epam.exception.exception.UserNotFoundException;
import epam.repository.UserRepository;
import epam.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        User mockUser = new User();
        mockUser.setUsername("testUser");
        mockUser.setPassword("oldPassword");

        when(userRepository.getByUsernameAndPassword("testUser", "oldPassword"))
                .thenReturn(Optional.of(mockUser));

        assertTrue(userService.changePassword(requestDTO));
        verify(userRepository, times(1)).getByUsernameAndPassword("testUser", "oldPassword");
        // Optional: assert the password was actually changed
        // assertEquals("newPassword", mockUser.getPassword());
    }

    @Test
    void testChangePassword_Failure() {
        ChangePasswordRequestDTO requestDTO = new ChangePasswordRequestDTO("testUser", "oldPassword", "newPassword");

        when(userRepository.getByUsernameAndPassword("testUser", "oldPassword"))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.changePassword(requestDTO));
        verify(userRepository, times(1)).getByUsernameAndPassword("testUser", "oldPassword");
    }
}