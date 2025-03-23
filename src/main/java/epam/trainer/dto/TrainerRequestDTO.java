package epam.trainer.dto;


import epam.user.dto.UserRequestDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Setter
@Getter
@ToString
public class TrainerRequestDTO {
    private String specialization;

    private UserRequestDTO user;
}
