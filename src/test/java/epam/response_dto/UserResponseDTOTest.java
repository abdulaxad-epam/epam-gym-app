package epam.response_dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserResponseDTOTest {

    @Test
    void testBuilderAndGetters() {
        UserResponseDTO dto = UserResponseDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .username("johndoe")
                .password("securePass123")
                .isActive(true)
                .build();

        assertEquals("John", dto.getFirstName());
        assertEquals("Doe", dto.getLastName());
        assertEquals("johndoe", dto.getUsername());
        assertEquals("securePass123", dto.getPassword());
        assertTrue(dto.getIsActive());
    }

    @Test
    void testSetters() {
        UserResponseDTO dto = UserResponseDTO.builder().build();

        dto.setFirstName("Alice");
        dto.setLastName("Smith");
        dto.setUsername("alice123");
        dto.setPassword("pass456");
        dto.setIsActive(false);

        assertEquals("Alice", dto.getFirstName());
        assertEquals("Smith", dto.getLastName());
        assertEquals("alice123", dto.getUsername());
        assertEquals("pass456", dto.getPassword());
        assertFalse(dto.getIsActive());
    }

    @Test
    void testToString() {
        UserResponseDTO dto = UserResponseDTO.builder()
                .firstName("Charlie")
                .lastName("Brown")
                .username("charlie_b")
                .password("mypassword")
                .isActive(true)
                .build();

        String expected = "UserResponseDTO(firstName=Charlie, lastName=Brown, username=charlie_b, password=mypassword, isActive=true)";
        assertEquals(expected, dto.toString());
    }
}
