package epam.training.dto;


import epam.trainee.dto.TraineeResponseDTO;
import epam.trainer.dto.TrainerResponseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
public class TrainingResponseDTO {

    private TraineeResponseDTO trainee;

    private TrainerResponseDTO trainer;

    private String trainingName;

    private LocalDateTime trainingDate;

    private String trainingType;

    private Integer trainingDuration;
}
