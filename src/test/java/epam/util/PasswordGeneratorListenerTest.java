package epam.util;

import epam.shared.util.GeneratePassword;
import epam.shared.util.PasswordGeneratorListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
class PasswordGeneratorListenerTest {

    private PasswordGeneratorListener passwordGeneratorListener;

    @BeforeEach
    void setUp() {
        passwordGeneratorListener = new PasswordGeneratorListener();
    }

    @Test
    void testGeneratePassword_FieldIsNull() {
        TestEntity entity = new TestEntity(null); // Password is null initially

        passwordGeneratorListener.generatePassword(entity);

        assertNotNull(entity.getPassword(), "Password should be generated");
        assertEquals(10, entity.getPassword().length(), "Generated password should match annotation length");
    }

    @Test
    void testGeneratePassword_FieldIsAlreadySet() {
        TestEntity entity = new TestEntity("existingPass123");

        passwordGeneratorListener.generatePassword(entity);

        assertEquals("existingPass123", entity.getPassword(), "Existing password should not be overwritten");
    }

    @Test
    void testGeneratePassword_FieldIsEmptyString() {
        TestEntity entity = new TestEntity("");

        passwordGeneratorListener.generatePassword(entity);

        assertNotNull(entity.getPassword(), "Password should be generated for empty field");
        assertEquals(10, entity.getPassword().length(), "Generated password should match annotation length");
    }


    private static class TestEntity {
        @GeneratePassword(length = 10)
        private String password;

        public TestEntity(String password) {
            this.password = password;
        }

        public String getPassword() {
            return password;
        }
    }
}
