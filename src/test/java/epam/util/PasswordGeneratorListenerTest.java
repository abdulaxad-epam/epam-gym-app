package epam.util;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PasswordGeneratorListenerTest {



    @Test
    void generatePassword_ShouldGeneratePassword_WhenFieldIsNull() throws NoSuchFieldException, IllegalAccessException {
        Entity entity = new Entity();
        Field passwordField = entity.getClass().getDeclaredField("password");
        passwordField.setAccessible(true);
        passwordField.set(entity, null); // Ensure it's null


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


        assertEquals("PreSetPassword", passwordField.get(entity));
    }

    static class Entity {
        private String password;
    }

}
