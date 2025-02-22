package epam.util;

import epam.exception.IllegalArgumentPassedException;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;


/**
    This class will generate passwords based on passed argument length
**/
@Component
public class PasswordGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generatePassword(int length) {

        if (length <= 0)
            throw new IllegalArgumentPassedException("Length must be greater than 0");

        StringBuilder password = new StringBuilder(length);

        for (int i = 0; i < length; i++)
            password.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));

        return password.toString();
    }
}