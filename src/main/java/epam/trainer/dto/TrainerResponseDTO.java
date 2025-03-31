package epam.trainer.dto;


import epam.trainee.dto.TraineeResponseDTO;
import epam.user.dto.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TrainerResponseDTO implements Serializable {

    private String trainerSpecialization;

    private UserResponseDTO user;

    private TraineeResponseDTO trainees;
}
