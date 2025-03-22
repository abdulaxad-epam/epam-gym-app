package epam.dto.response_dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TraineeResponseDTO implements Serializable {

    private LocalDateTime traineeDateOfBirth;

    private String address;

    private UserResponseDTO user;
}
