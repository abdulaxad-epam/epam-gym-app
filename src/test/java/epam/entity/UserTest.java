package epam.entity;

import epam.util.PasswordGeneratorListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class UserTest {

    @Mock
    private PasswordGeneratorListener mockPasswordListener;

    private User user;
    private UUID userId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();

        user = User.builder()
                .userId(userId)
                .firstname("John")
                .lastname("Doe")
                .username("johndoe123")
                .password("SecurePass@123")
                .isActive(true)
                .build();
    }

    @Test
    void testUserCreation() {
        assertNotNull(user);
        assertEquals(userId, user.getUserId());
        assertEquals("John", user.getFirstname());
        assertEquals("Doe", user.getLastname());
        assertEquals("johndoe123", user.getUsername());
        assertEquals("SecurePass@123", user.getPassword());
        assertTrue(user.getIsActive());
    }

    @Test
    void testUserEquality() {
        User anotherUser = User.builder()
                .userId(userId)  // Same ID should make them equal
                .firstname("John")
                .lastname("Doe")
                .username("johndoe123")
                .password("SecurePass@123")
                .isActive(true)
                .build();

        assertEquals(user, anotherUser);
    }

//    @Test
//    void testMockPasswordListener(){
//
//        when()
//    }

    @Test
    void testUserInequality() {
        User differentUser = User.builder()
                .userId(UUID.randomUUID())  // Different ID should make them unequal
                .firstname("John")
                .lastname("Doe")
                .username("johndoe123")
                .password("SecurePass@123")
                .isActive(true)
                .build();

        assertNotEquals(user, differentUser);
    }

    @Test
    void testUserHandlesNullFirstName() {
        user.setFirstname(null);
        assertNull(user.getFirstname());
    }

    @Test
    void testUserHandlesNullLastName() {
        user.setLastname(null);
        assertNull(user.getLastname());
    }

    @Test
    void testUserHandlesNullUsername() {
        user.setUsername(null);
        assertNull(user.getUsername());
    }

    @Test
    void testUserHandlesEmptyUsername() {
        user.setUsername("");
        assertEquals("", user.getUsername());
    }

    @Test
    void testUserHandlesNullPassword() {
        user.setPassword(null);
        assertNull(user.getPassword());
    }


    @Test
    void testUserHandlesInactiveState() {
        user.setIsActive(false);
        assertFalse(user.getIsActive());
    }

    @Test
    void testUsernameUniqueness() {
        User anotherUser = User.builder()
                .userId(UUID.randomUUID())
                .firstname("Jane")
                .lastname("Doe")
                .username("johndoe123") // Same username
                .password("DifferentPass@456")
                .isActive(true)
                .build();

        assertEquals(user.getUsername(), anotherUser.getUsername());
    }
}
