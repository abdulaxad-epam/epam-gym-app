package epam.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TraineeTest {

    @Test
    void testNoArgsConstructor() {
        Trainee trainee = new Trainee();
        assertNotNull(trainee, "No-args constructor should create a non-null instance");
    }

    @Test
    void testAllArgsConstructor() {
        Trainee trainee = new Trainee("1995-08-15", "123 Street, City");
        assertNotNull(trainee, "All-args constructor should create a non-null instance");
        assertEquals("1995-08-15", trainee.getDateOfBirth(), "Date of Birth should be set correctly");
        assertEquals("123 Street, City", trainee.getAddress(), "Address should be set correctly");
    }
}

