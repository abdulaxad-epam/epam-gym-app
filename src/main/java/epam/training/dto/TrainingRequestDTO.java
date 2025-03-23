package epam.training.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
public class TrainingRequestDTO {

    private String trainerUsername;

    private String traineeUsername;

    private String trainingName;

    private LocalDateTime trainingDate;

    private String trainingType;

    private Integer trainingDuration;
}
