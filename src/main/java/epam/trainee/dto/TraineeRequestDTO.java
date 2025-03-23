package epam.trainee.dto;


import epam.user.dto.UserRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TraineeRequestDTO implements Serializable {

    private LocalDate dateOfBirth;

    private String address;

    private UserRequestDTO user;

}
