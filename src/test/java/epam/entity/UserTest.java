package epam.entity;

import org.instancio.Instancio;
import org.instancio.junit.InstancioExtension;
import org.instancio.Select;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(InstancioExtension.class)
class UserTest {

    private User user;
    private UUID userId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();

        user = Instancio.of(User.class)
                .set(Select.field(User::getUserId), userId)
                .set(Select.field(User::getFirstname), "John")
                .set(Select.field(User::getLastname), "Doe2")
                .set(Select.field(User::getUsername), "johndoe123")
                .set(Select.field(User::getPassword), "SecurePass@123")
                .set(Select.field(User::getIsActive), true)
                .create();
    }

    @Test
    void testUserCreation() {
        assertNotNull(user);
        assertEquals(userId, user.getUserId());
        assertEquals("John", user.getFirstname());
        assertEquals("Doe2", user.getLastname());
        assertEquals("johndoe123", user.getUsername());
        assertEquals("SecurePass@123", user.getPassword());
        assertTrue(user.getIsActive());
    }

    @Test
    void testUserEquality() {
        User anotherUser = Instancio.of(User.class)
                .set(Select.field(User::getUserId), userId)
                .set(Select.field(User::getFirstname), "John")
                .set(Select.field(User::getLastname), "Doe2")
                .set(Select.field(User::getUsername), "johndoe123")
                .set(Select.field(User::getPassword), "SecurePass@123")
                .set(Select.field(User::getIsActive), true)
                .create();

        assertEquals(user, anotherUser);
    }

    @Test
    void testUserInequality() {
        User differentUser = Instancio.of(User.class)
                .set(Select.field(User::getUserId), UUID.randomUUID())
                .set(Select.field(User::getFirstname), "John2")
                .set(Select.field(User::getLastname), "Doe2")
                .set(Select.field(User::getUsername), "johndoe123")
                .set(Select.field(User::getPassword), "SecurePass@123")
                .set(Select.field(User::getIsActive), true)
                .create();

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
        User anotherUser = Instancio.of(User.class)
                .set(Select.field(User::getUserId), UUID.randomUUID())
                .set(Select.field(User::getFirstname), "Jane2")
                .set(Select.field(User::getLastname), "Doe2")
                .set(Select.field(User::getUsername), "johndoe123") // Same username
                .set(Select.field(User::getPassword), "DifferentPass@456")
                .set(Select.field(User::getIsActive), true)
                .create();

        assertEquals(user.getUsername(), anotherUser.getUsername());
    }
}
