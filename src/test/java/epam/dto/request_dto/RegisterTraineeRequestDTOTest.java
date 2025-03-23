package epam.dto.request_dto;

import epam.shared.security.dto.RegisterTraineeRequestDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegisterTraineeRequestDTOTest {

    @Test
    void testBuilderAndGetters() {
        LocalDateTime dob = LocalDateTime.of(2000, 1, 1, 0, 0);
        UserRequestDTO user = UserRequestDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .isActive(true)
                .build();

        RegisterTraineeRequestDTO dto = RegisterTraineeRequestDTO.builder()
                .dateOfBirth(dob)
                .address("123 Main St")
                .user(user)
                .build();

        assertEquals(dob, dto.getDateOfBirth());
        assertEquals("123 Main St", dto.getAddress());
        assertEquals(user, dto.getUser());
    }

    @Test
    void testSetters() {
        RegisterTraineeRequestDTO dto = RegisterTraineeRequestDTO.builder().build();
        LocalDateTime dob = LocalDateTime.of(1995, 5, 15, 10, 30);
        UserRequestDTO user = UserRequestDTO.builder()
                .firstName("Jane")
                .lastName("Doe")
                .isActive(false)
                .build();

        dto.setDateOfBirth(dob);
        dto.setAddress("456 Another St");
        dto.setUser(user);

        assertEquals(dob, dto.getDateOfBirth());
        assertEquals("456 Another St", dto.getAddress());
        assertEquals(user, dto.getUser());
    }

    @Test
    void testToString() {
        LocalDateTime dob = LocalDateTime.of(1998, 12, 25, 12, 0);
        UserRequestDTO user = UserRequestDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .isActive(true)
                .build();

        RegisterTraineeRequestDTO dto = RegisterTraineeRequestDTO.builder()
                .dateOfBirth(dob)
                .address("789 Some Rd")
                .user(user)
                .build();

        String expected = "RegisterTraineeRequestDTO(dateOfBirth=" + dob + ", address=789 Some Rd, user=" + user + ")";
        assertEquals(expected, dto.toString());
    }
}
