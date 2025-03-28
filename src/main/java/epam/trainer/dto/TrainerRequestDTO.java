package epam.trainer.dto;


import epam.user.dto.UserRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TrainerRequestDTO implements Serializable {
    private String specialization;

    private UserRequestDTO user;
}
