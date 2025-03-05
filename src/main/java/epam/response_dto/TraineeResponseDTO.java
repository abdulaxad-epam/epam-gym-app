package epam.response_dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@ToString
@Getter
@Setter
public class TraineeResponseDTO {

    private String traineeDateOfBirth;

    private String address;

    private UserResponseDTO user;
}
