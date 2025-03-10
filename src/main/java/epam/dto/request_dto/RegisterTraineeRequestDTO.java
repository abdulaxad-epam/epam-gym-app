package epam.dto.request_dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
public class RegisterTraineeRequestDTO {

    private LocalDateTime dateOfBirth;

    private String address;

    private UserRequestDTO user;

}
