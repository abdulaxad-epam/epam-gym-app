package epam.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class UserResponseDTO {

    private String firstName;

    private String lastName;

    private String username;

    private String password;

    private Boolean isActive;
}
