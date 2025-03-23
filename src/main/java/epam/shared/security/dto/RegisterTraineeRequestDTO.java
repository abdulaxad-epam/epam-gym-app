package epam.shared.security.dto;

import epam.user.dto.UserRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RegisterTraineeRequestDTO implements Serializable {

    private LocalDate dateOfBirth;

    private String address;

    private UserRequestDTO user;

}
