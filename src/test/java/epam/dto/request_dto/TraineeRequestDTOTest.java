package epam.dto.request_dto;

import epam.trainee.dto.TraineeRequestDTO;
import epam.user.dto.UserRequestDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TraineeRequestDTOTest {

    @Test
    void testBuilderAndGetters() {
        LocalDateTime dob = LocalDateTime.of(2000, 5, 15, 0, 0);
        UserRequestDTO user = UserRequestDTO.builder()
                .firstName("Alice")
                .lastName("Johnson")
                .isActive(true)
                .build();

        TraineeRequestDTO dto = TraineeRequestDTO.builder()
                .dateOfBirth(LocalDate.from(dob))
                .address("123 Main St, City")
                .user(user)
                .build();

        assertEquals(dob, dto.getDateOfBirth());
        assertEquals("123 Main St, City", dto.getAddress());
        assertEquals(user, dto.getUser());
    }

    @Test
    void testSetters() {
        TraineeRequestDTO dto = TraineeRequestDTO.builder().build();
        LocalDateTime dob = LocalDateTime.of(1998, 10, 20, 0, 0);
        UserRequestDTO user = UserRequestDTO.builder()
                .firstName("Bob")
                .lastName("Smith")
                .isActive(false)
                .build();

        dto.setDateOfBirth(dob);
        dto.setAddress("456 Elm St, Town");
        dto.setUser(user);

        assertEquals(dob, dto.getDateOfBirth());
        assertEquals("456 Elm St, Town", dto.getAddress());
        assertEquals(user, dto.getUser());
    }

    @Test
    void testToString() {
        LocalDateTime dob = LocalDateTime.of(1995, 8, 10, 0, 0);
        UserRequestDTO user = UserRequestDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .isActive(true)
                .build();

        TraineeRequestDTO dto = TraineeRequestDTO.builder()
                .dateOfBirth(LocalDate.from(dob))
                .address("789 Pine St, Village")
                .user(user)
                .build();

        String expected = "TraineeRequestDTO(dateOfBirth=" + dob + ", address=789 Pine St, Village, user=" + user + ")";
        assertEquals(expected, dto.toString());
    }
}
