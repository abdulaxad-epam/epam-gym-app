package epam.response_dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

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
