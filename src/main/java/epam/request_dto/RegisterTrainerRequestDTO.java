package epam.request_dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class RegisterTrainerRequestDTO {

    private String specialization;

    private UserRequestDTO user;
}
