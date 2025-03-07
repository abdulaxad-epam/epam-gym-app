package epam.request_dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class TrainingRequestDTO {

    private TrainerRequestDTO trainer;

    private TrainingRequestDTO training;

    private String trainingName;

    private String trainingDate;

    private String trainingType;
}
