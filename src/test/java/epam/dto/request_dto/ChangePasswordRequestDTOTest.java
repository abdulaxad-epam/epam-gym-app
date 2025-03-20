package epam.dto.request_dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChangePasswordRequestDTOTest {

    @Test
    void testBuilderAndGetters() {
        ChangePasswordRequestDTO dto = ChangePasswordRequestDTO.builder()
                .username("testUser")
                .oldPassword("oldPass")
                .newPassword("newPass")
                .build();

        assertEquals("testUser", dto.getUsername());
        assertEquals("oldPass", dto.getOldPassword());
        assertEquals("newPass", dto.getNewPassword());
    }

    @Test
    void testSetters() {
        ChangePasswordRequestDTO dto = ChangePasswordRequestDTO.builder()
        .username("newUser")
        .oldPassword("old123")
        .newPassword("new123")
                .build();

        assertEquals("newUser", dto.getUsername());
        assertEquals("old123", dto.getOldPassword());
        assertEquals("new123", dto.getNewPassword());
    }

    @Test
    void testToString() {
        ChangePasswordRequestDTO dto = ChangePasswordRequestDTO.builder()
                .username("user123")
                .oldPassword("oldPass")
                .newPassword("newPass")
                .build();

        String expected = "ChangePasswordRequestDTO(username=user123, oldPassword=oldPass, newPassword=newPass)";
        assertEquals(expected, dto.toString());
    }
}
