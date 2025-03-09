package epam.service_impl;

import epam.entity.User;
import epam.repository.UserRepository;
import epam.request_dto.ChangePasswordRequestDTO;
import epam.service.service_impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExistsByUsernameAndPassword_ValidCredentials() {
        when(userRepository.existsByUsernameAndPassword("validUser", "password123")).thenReturn(true);

        assertTrue(userService.existsByUsernameAndPassword("validUser", "password123"));
        verify(userRepository, times(1)).existsByUsernameAndPassword("validUser", "password123");
    }

    @Test
    void testExistsByUsernameAndPassword_InvalidCredentials() {
        when(userRepository.existsByUsernameAndPassword("invalidUser", "wrongPassword")).thenReturn(false);

        assertFalse(userService.existsByUsernameAndPassword("invalidUser", "wrongPassword"));
        verify(userRepository, times(1)).existsByUsernameAndPassword("invalidUser", "wrongPassword");
    }

    @Test
    void testExistsByUsername_UserExists() {
        when(userRepository.existsByUsername("existingUser")).thenReturn(true);

        assertTrue(userService.existsByUsername("existingUser"));
        verify(userRepository, times(1)).existsByUsername("existingUser");
    }

    @Test
    void testExistsByUsername_UserDoesNotExist() {
        when(userRepository.existsByUsername("nonExistingUser")).thenReturn(false);

        assertFalse(userService.existsByUsername("nonExistingUser"));
        verify(userRepository, times(1)).existsByUsername("nonExistingUser");
    }

    @Test
    void testChangePassword_SuccessfulChange() {
        ChangePasswordRequestDTO request = ChangePasswordRequestDTO.builder()
                .username("user123")
                .oldPassword("oldPass")
                .newPassword("newPass")
                .build();

        when(userRepository.changePassword(request)).thenReturn(true);

        assertTrue(userService.changePassword(request));
        verify(userRepository, times(1)).changePassword(request);
    }

    @Test
    void testChangePassword_FailureDueToInvalidOldPassword() {
        ChangePasswordRequestDTO request = ChangePasswordRequestDTO.builder()
                .username("user123")
                .oldPassword("wrongOldPass")
                .newPassword("newPass")
                .build();

        when(userRepository.changePassword(request)).thenReturn(false);

        assertFalse(userService.changePassword(request));
        verify(userRepository, times(1)).changePassword(request);
    }

    @Test
    void testChangePassword_FailureDueToNullValues() {
        ChangePasswordRequestDTO request = ChangePasswordRequestDTO.builder()
                .username(null)
                .oldPassword("oldPass")
                .newPassword("newPass")
                .build();

        assertFalse(userService.changePassword(request));
        verify(userRepository, never()).changePassword(any());
    }

    @Test
    void testToggleStatus_UserExists() {
        User user = User.builder()
                .username("user123")
                .isActive(true)
                .build();

        when(userRepository.existsByUsername("user123")).thenReturn(true);
        when(userRepository.getByUsername("user123")).thenReturn(user);
        when(userRepository.toggleActiveStatus(user)).thenReturn(true);

        assertTrue(userService.toggleStatus("user123"));
        verify(userRepository, times(1)).toggleActiveStatus(user);
    }

    @Test
    void testToggleStatus_UserDoesNotExist() {
        when(userRepository.existsByUsername("unknownUser")).thenReturn(false);

        assertFalse(userService.toggleStatus("unknownUser"));
        verify(userRepository, never()).toggleActiveStatus(any());
    }
}
