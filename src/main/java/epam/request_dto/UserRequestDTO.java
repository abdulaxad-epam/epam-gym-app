package epam.request_dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class UserRequestDTO {

    private String firstName;

    private String lastName;

    private String password;

    private Boolean isActive;
}
