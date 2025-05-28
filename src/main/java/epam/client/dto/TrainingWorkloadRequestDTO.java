package epam.client.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;


@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TrainingWorkloadRequestDTO implements Serializable {

    @NotNull(message = "Training type cannot be blank or null")
    private UUID trainingTypeId;

    @NotNull(message = "TrainerId must be specified")
    private UUID trainerId;

    @NotNull(message = "TraineeId must be specified")
    private UUID traineeId;

    @Future(message = "The training start date must be in the future")
    @NotNull(message = "Training date must be specified")
    private LocalDate trainingDate;

    @Min(value = 30, message = "Training duration must be at least 30 minute")
    @Max(value = 480, message = "Training duration cannot exceed 8 hours (480 minutes)")
    @NotNull(message = "Training duration is required")
    private Integer trainingDuration;
}
