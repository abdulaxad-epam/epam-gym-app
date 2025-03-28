package epam.training.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TrainingRequestDTO implements Serializable {

    private String trainerUsername;

    private String traineeUsername;

    private String trainingName;

    private LocalDate trainingDate;

    private String trainingType;

    private Integer trainingDuration;
}
