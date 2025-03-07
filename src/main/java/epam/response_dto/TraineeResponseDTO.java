package epam.response_dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@ToString
@Getter
@Setter
public class TraineeResponseDTO implements Serializable {

    private LocalDateTime traineeDateOfBirth;

    private String address;

    private UserResponseDTO user;
}
