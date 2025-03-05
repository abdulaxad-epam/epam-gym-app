package epam.mapper;

import epam.entity.User;
import epam.response_dto.UserResponseDTO;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {

    public UserResponseDTO toUserResponseDTO(User user) {
        return UserResponseDTO.builder()
                .firstName(user.getFirstname())
                .lastName(user.getLastname())
                .isActive(user.getIsActive())
                .password(user.getPassword())
                .username(user.getUsername())
                .build();
    }
}
