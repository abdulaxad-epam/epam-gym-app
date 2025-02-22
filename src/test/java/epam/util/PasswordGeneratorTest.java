package epam.util;

import epam.exception.IllegalArgumentPassedException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordGeneratorTest {

    @Test
    public void testFor_Equals(){
        assertEquals(11, PasswordGenerator.generatePassword(11).length(), String.format("Password length should be %s", 11));
        assertEquals(65, PasswordGenerator.generatePassword(65).length(), String.format("Password length should be %s", 65));
        assertEquals(90, PasswordGenerator.generatePassword(90).length(), String.format("Password length should be %s", 90));
        assertEquals(76, PasswordGenerator.generatePassword(76).length(), String.format("Password length should be %s", 76));
        assertEquals(87, PasswordGenerator.generatePassword(87).length(), String.format("Password length should be %s", 87));
    }

    @Test
    public void testFor_NotEquals(){
        assertNotEquals(123, PasswordGenerator.generatePassword(546).length());
        assertNotEquals(11, PasswordGenerator.generatePassword(154).length());
        assertNotEquals(123, PasswordGenerator.generatePassword(164).length());
        assertNotEquals(2345, PasswordGenerator.generatePassword(4567).length());
        assertNotEquals(1234, PasswordGenerator.generatePassword(4546).length());
    }

    @Test
    public void testFor_ThrowsException(){
        assertThrows(IllegalArgumentPassedException.class, () -> PasswordGenerator.generatePassword(-1));
        assertThrows(IllegalArgumentPassedException.class, () -> PasswordGenerator.generatePassword(-1543));
        assertThrows(IllegalArgumentPassedException.class, () -> PasswordGenerator.generatePassword(-1123));
        assertThrows(IllegalArgumentPassedException.class, () -> PasswordGenerator.generatePassword(-9546));
        assertThrows(IllegalArgumentPassedException.class, () -> PasswordGenerator.generatePassword(-9));

    }
}
