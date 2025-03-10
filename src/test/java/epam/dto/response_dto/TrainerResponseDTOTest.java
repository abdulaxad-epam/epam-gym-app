package epam.dto.response_dto;

import epam.dto.response_dto.TrainerResponseDTO;
import epam.dto.response_dto.UserResponseDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TrainerResponseDTOTest {

    @Test
    void testBuilderAndGetters() {
        UserResponseDTO user = UserResponseDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .isActive(true)
                .build();

        TrainerResponseDTO dto = TrainerResponseDTO.builder()
                .trainerSpecialization("Fitness")
                .user(user)
                .build();

        assertEquals("Fitness", dto.getTrainerSpecialization());
        assertEquals(user, dto.getUser());
    }

    @Test
    void testSetters() {
        TrainerResponseDTO dto = TrainerResponseDTO.builder().build();
        UserResponseDTO user = UserResponseDTO.builder()
                .firstName("Alice")
                .lastName("Smith")
                .isActive(false)
                .build();

        dto.setTrainerSpecialization("Yoga");
        dto.setUser(user);

        assertEquals("Yoga", dto.getTrainerSpecialization());
        assertEquals(user, dto.getUser());
    }

    @Test
    void testToString() {
        UserResponseDTO user = UserResponseDTO.builder()
                .firstName("Charlie")
                .lastName("Brown")
                .isActive(true)
                .build();

        TrainerResponseDTO dto = TrainerResponseDTO.builder()
                .trainerSpecialization("Martial Arts")
                .user(user)
                .build();

        String expected = "TrainerResponseDTO(trainerSpecialization=Martial Arts, user=" + user + ")";
        assertEquals(expected, dto.toString());
    }
}
