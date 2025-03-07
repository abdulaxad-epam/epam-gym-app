package epam.request_dto;


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
