package epam.request_dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class RegisterTraineeRequestDTO {

    private String dateOfBirth;

    private String address;

    private UserRequestDTO user;

}
