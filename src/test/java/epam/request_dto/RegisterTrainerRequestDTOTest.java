package epam.request_dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegisterTrainerRequestDTOTest {

    @Test
    void testBuilderAndGetters() {
        UserRequestDTO user = UserRequestDTO.builder()
                .firstName("Alice")
                .lastName("Smith")
                .isActive(true)
                .build();

        RegisterTrainerRequestDTO dto = RegisterTrainerRequestDTO.builder()
                .specialization("Fitness Coach")
                .user(user)
                .build();

        assertEquals("Fitness Coach", dto.getSpecialization());
        assertEquals(user, dto.getUser());
    }

    @Test
    void testSetters() {
        RegisterTrainerRequestDTO dto = RegisterTrainerRequestDTO.builder().build();
        UserRequestDTO user = UserRequestDTO.builder()
                .firstName("Bob")
                .lastName("Brown")
                .isActive(false)
                .build();

        dto.setSpecialization("Yoga Instructor");
        dto.setUser(user);

        assertEquals("Yoga Instructor", dto.getSpecialization());
        assertEquals(user, dto.getUser());
    }

    @Test
    void testToString() {
        UserRequestDTO user = UserRequestDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .isActive(true)
                .build();

        RegisterTrainerRequestDTO dto = RegisterTrainerRequestDTO.builder()
                .specialization("Personal Trainer")
                .user(user)
                .build();

        String expected = "RegisterTrainerRequestDTO(specialization=Personal Trainer, user=" + user + ")";
        assertEquals(expected, dto.toString());
    }
}
