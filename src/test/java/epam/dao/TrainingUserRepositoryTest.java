package epam.dao;

import epam.domain.User;
import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TrainingUserRepositoryTest {

    private TrainingUserRepositoryImpl repository;
    private Map<UUID, TestUser> inMemoryStorage;

    @BeforeEach
    void setUp() {
        inMemoryStorage = new HashMap<>();
        repository = new TrainingUserRepositoryImpl(inMemoryStorage);
    }

    @Test
    void testInsert_UserIsAddedSuccessfully() {
        UUID id = UUID.randomUUID();
        TestUser user = new TestUser("John", "Doe");

        TestUser insertedUser = repository.insert(id, user);

        assertEquals("John.Doe", insertedUser.getUsername(), "Username should be correctly formatted");
        assertEquals(id, insertedUser.getUserId(), "User ID should be set");
        assertTrue(inMemoryStorage.containsKey(id), "User should be stored");
    }

    @Test
    void testInsert_UsernameAlreadyExists_ShouldAppendNumber() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        TestUser user1 = new TestUser("Alice", "Smith");
        TestUser user2 = new TestUser("Alice", "Smith");

        repository.insert(id1, user1);
        TestUser insertedUser2 = repository.insert(id2, user2);

        assertEquals("Alice.Smith1", insertedUser2.getUsername(), "Username should be incremented");
        assertTrue(inMemoryStorage.containsKey(id2), "Second user should be stored");
    }

    @Test
    void testUpdateUsername() {
        TestUser user = new TestUser("Emma", "Brown");

        TestUser updatedUser = repository.updateUsername(user);

        assertEquals("Emma.Brown", updatedUser.getUsername(), "Username should be updated correctly");
    }

    @Test
    void testIsUsernameExists_ReturnsTrue_WhenExists() {
        UUID id = UUID.randomUUID();
        TestUser user = new TestUser("Chris", "White");
        repository.insert(id, user);

        assertTrue(repository.isUsernameExists("Chris.White"), "Username should exist");
    }

    @Test
    void testIsUsernameExists_ReturnsFalse_WhenNotExists() {
        assertFalse(repository.isUsernameExists("Non.Existent"), "Username should not exist");
    }

    private record TrainingUserRepositoryImpl(Map<UUID, TestUser> storage)
            implements TrainingUserRepository<TestUser> {

        @Override
            public Map<UUID, TestUser> getInMemoryStorage() {
                return storage;
            }
        }

    @Getter
    private static class TestUser extends User {
        private final String firstname;
        private final String lastname;

        public TestUser(String firstname, String lastname) {
            this.firstname = firstname;
            this.lastname = lastname;
        }

    }
}

