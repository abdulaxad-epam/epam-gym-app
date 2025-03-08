package epam.mapper;

import epam.entity.User;
import epam.request_dto.UserRequestDTO;
import epam.response_dto.UserResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapper();
    }

    @Test
    void testToUserResponseDTO_Success() {
        User user = new User();
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setIsActive(true);
        user.setPassword("securePass123");
        user.setUsername("johndoe");

        UserResponseDTO responseDTO = userMapper.toUserResponseDTO(user);

        assertNotNull(responseDTO);
        assertEquals("John", responseDTO.getFirstName());
        assertEquals("Doe", responseDTO.getLastName());
        assertTrue(responseDTO.getIsActive());
        assertEquals("securePass123", responseDTO.getPassword());
        assertEquals("johndoe", responseDTO.getUsername());
    }

    @Test
    void testToUserResponseDTO_NullUser() {
        assertNull(userMapper.toUserResponseDTO(null));
    }

    @Test
    void testToUser_Success() {
        UserRequestDTO requestDTO = UserRequestDTO.builder().build();
        requestDTO.setFirstName("Jane");
        requestDTO.setLastName("Smith");
        requestDTO.setIsActive(false);
        requestDTO.setPassword("pass456");

        User user = userMapper.toUser(requestDTO);

        assertNotNull(user);
        assertEquals("Jane", user.getFirstname());
        assertEquals("Smith", user.getLastname());
        assertFalse(user.getIsActive());
        assertEquals("pass456", user.getPassword());
    }

    @Test
    void testToUser_NullUserRequestDTO() {
        assertNull(userMapper.toUser(null));
    }
}
