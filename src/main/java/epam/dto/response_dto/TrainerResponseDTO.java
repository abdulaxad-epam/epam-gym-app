package epam.dto.response_dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class TrainerResponseDTO {

    private String trainerSpecialization;

    private UserResponseDTO user;
}
