package epam.dto.response_dto;

import epam.trainee.dto.TraineeResponseDTO;
import epam.user.dto.UserResponseDTO;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TraineeResponseDTOTest {

    @Test
    void testBuilderAndGetters() {
        UserResponseDTO user = UserResponseDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .isActive(true)
                .build();

        LocalDateTime birthDate = LocalDateTime.of(1995, 5, 10, 0, 0);

        TraineeResponseDTO dto = TraineeResponseDTO.builder()
                .traineeDateOfBirth(String.valueOf(birthDate))
                .address("123 Main St")
                .user(user)
                .build();

        assertEquals(birthDate, dto.getTraineeDateOfBirth());
        assertEquals("123 Main St", dto.getAddress());
        assertEquals(user, dto.getUser());
    }

    @Test
    void testSetters() {
        TraineeResponseDTO dto = TraineeResponseDTO.builder().build();
        LocalDateTime birthDate = LocalDateTime.of(2000, 1, 1, 0, 0);
        UserResponseDTO user = UserResponseDTO.builder()
                .firstName("Alice")
                .lastName("Smith")
                .isActive(false)
                .build();

        dto.setTraineeDateOfBirth(String.valueOf(birthDate));
        dto.setAddress("456 Elm St");
        dto.setUser(user);

        assertEquals(birthDate, dto.getTraineeDateOfBirth());
        assertEquals("456 Elm St", dto.getAddress());
        assertEquals(user, dto.getUser());
    }

    @Test
    void testToString() {
        UserResponseDTO user = UserResponseDTO.builder()
                .firstName("Charlie")
                .lastName("Brown")
                .isActive(true)
                .build();

        LocalDateTime birthDate = LocalDateTime.of(1998, 8, 20, 0, 0);

        TraineeResponseDTO dto = TraineeResponseDTO.builder()
                .traineeDateOfBirth(String.valueOf(birthDate))
                .address("789 Oak St")
                .user(user)
                .build();

        String expected = "TraineeResponseDTO(traineeDateOfBirth=1998-08-20T00:00, address=789 Oak St, user=" + user + ")";
        assertEquals(expected, dto.toString());
    }

    @Test
    void testImplementsSerializable() {
        assertTrue(Serializable.class.isAssignableFrom(TraineeResponseDTO.class));
    }
}
