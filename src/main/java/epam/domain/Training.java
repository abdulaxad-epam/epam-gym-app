package epam.domain;

import epam.enums.TrainingType;
import lombok.Data;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import java.util.UUID;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Training {

    private UUID trainingId;
    private String traineeId;
    private String trainerId;
    private String trainingName;
    private String trainingDate;
    private TrainingType trainingType;
    private String trainingDuration;

}