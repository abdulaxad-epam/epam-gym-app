package epam.dto.request_dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@ToString
public class TraineeRequestDTO {

    private LocalDateTime dateOfBirth;

    private String address;

    private UserRequestDTO user;

}
