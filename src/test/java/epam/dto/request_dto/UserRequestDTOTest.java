package epam.dto.request_dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserRequestDTOTest {

    @Test
    void testBuilderAndGetters() {
        UserRequestDTO dto = UserRequestDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .password("securePass123")
                .isActive(true)
                .build();

        assertEquals("John", dto.getFirstName());
        assertEquals("Doe", dto.getLastName());
        assertEquals("securePass123", dto.getPassword());
        assertTrue(dto.getIsActive());
    }

    @Test
    void testSetters() {
        UserRequestDTO dto = UserRequestDTO.builder().build();

        dto.setFirstName("Alice");
        dto.setLastName("Smith");
        dto.setPassword("newPass456");
        dto.setIsActive(false);

        assertEquals("Alice", dto.getFirstName());
        assertEquals("Smith", dto.getLastName());
        assertEquals("newPass456", dto.getPassword());
        assertFalse(dto.getIsActive());
    }

    @Test
    void testToString() {
        UserRequestDTO dto = UserRequestDTO.builder()
                .firstName("Bob")
                .lastName("Brown")
                .password("hiddenPass")
                .isActive(true)
                .build();

        String expected = "UserRequestDTO(firstName=Bob, lastName=Brown, password=hiddenPass, isActive=true)";
        assertEquals(expected, dto.toString());
    }
}
