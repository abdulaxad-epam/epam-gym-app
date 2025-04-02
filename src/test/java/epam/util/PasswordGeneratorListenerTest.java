package epam.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PasswordGeneratorListenerTest {

    private PasswordGeneratorListener passwordGeneratorListener;

    @BeforeEach
    void setUp() {
        passwordGeneratorListener = new PasswordGeneratorListener();
    }

    @Test
    void generatePassword_ShouldGeneratePassword_WhenFieldIsNull() throws NoSuchFieldException, IllegalAccessException {
        Entity entity = new Entity();
        Field passwordField = entity.getClass().getDeclaredField("password");
        passwordField.setAccessible(true);
        passwordField.set(entity, null); // Ensure it's null

        passwordGeneratorListener.generatePassword(entity);

        String generatedPassword = (String) passwordField.get(entity);
        assertNotNull(generatedPassword);
        assertEquals(12, generatedPassword.length()); // Default length from annotation
    }

    @Test
    void generatePassword_ShouldNotChangePassword_WhenFieldIsAlreadySet() throws NoSuchFieldException, IllegalAccessException {
        Entity entity = new Entity();
        Field passwordField = entity.getClass().getDeclaredField("password");
        passwordField.setAccessible(true);
        passwordField.set(entity, "PreSetPassword");

        passwordGeneratorListener.generatePassword(entity);

        assertEquals("PreSetPassword", passwordField.get(entity));
    }

    static class Entity {
        @GeneratePassword(length = 12)
        private String password;
    }

}
