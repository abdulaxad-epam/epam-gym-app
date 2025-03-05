package epam.mapper;

import epam.entity.Training;
import epam.response_dto.TrainingResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class TrainingMapper {
    public TrainingResponseDTO toTrainingResponseDTO(Training training) {
        return TrainingResponseDTO.builder()
                .traineeId(training.getTraineeId())
                .trainerId(training.getTrainerId())
                .trainingType(training.getTrainingType().getDescription())
                .trainingName(training.getTrainingName())
                .trainingDate(training.getTrainingDate())
                .build();
    }
}
