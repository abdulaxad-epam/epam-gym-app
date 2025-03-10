package epam.dto.request_dto;

import epam.dto.request_dto.AuthenticateRequestDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthenticateRequestDTOTest {

    @Test
    void testBuilderAndGetters() {
        AuthenticateRequestDTO dto = AuthenticateRequestDTO.builder()
                .username("testUser")
                .password("testPass")
                .build();

        assertEquals("testUser", dto.getUsername());
        assertEquals("testPass", dto.getPassword());
    }

    @Test
    void testSetters() {
        AuthenticateRequestDTO dto = AuthenticateRequestDTO.builder()
        .username("newUser")
        .password("newPass")
        .build();

        assertEquals("newUser", dto.getUsername());
        assertEquals("newPass", dto.getPassword());
    }

    @Test
    void testToString() {
        AuthenticateRequestDTO dto = AuthenticateRequestDTO.builder()
                .username("user123")
                .password("pass123")
                .build();

        String expected = "AuthenticateRequestDTO(username=user123, password=pass123)";
        assertEquals(expected, dto.toString());
    }
}
