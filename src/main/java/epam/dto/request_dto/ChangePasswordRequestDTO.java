package epam.dto.request_dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Setter
@Getter
@ToString
public class ChangePasswordRequestDTO {

    private String username;

    private String oldPassword;

    private String newPassword;
}
