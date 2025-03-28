package epam.shared.security.dto;


import epam.user.dto.UserRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RegisterTrainerRequestDTO implements Serializable {

    private String specialization;

    private UserRequestDTO user;
}
