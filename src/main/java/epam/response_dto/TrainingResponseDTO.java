package epam.response_dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class TrainingResponseDTO {

    private String traineeId;

    private String trainerId;

    private String trainingName;

    private String trainingDate;

    private String trainingType;
}
