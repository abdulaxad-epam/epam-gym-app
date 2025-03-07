package epam.util;


import jakarta.persistence.PrePersist;
import java.lang.reflect.Field;
import java.security.SecureRandom;

public class PasswordGeneratorListener {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    @PrePersist
    public void generatePassword(Object entity) {
        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(GeneratePassword.class)) {
                field.setAccessible(true);
                try {
                    if (field.get(entity) == null || field.get(entity).toString().isEmpty()) {
                        int length = field.getAnnotation(GeneratePassword.class).length();
                        field.set(entity, generateRandomPassword(length));
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Could not set generated password", e);
                }
            }
        }
    }

    private String generateRandomPassword(int length) {
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            password.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return password.toString();
    }
}
