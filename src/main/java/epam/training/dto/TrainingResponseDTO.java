package epam.training.dto;


import epam.trainee.dto.TraineeResponseDTO;
import epam.trainer.dto.TrainerResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TrainingResponseDTO implements Serializable {

    private TraineeResponseDTO trainee;

    private TrainerResponseDTO trainer;

    private String trainingName;

    private LocalDateTime trainingDate;

    private String trainingType;

    private Integer trainingDuration;
}
