package epam.mapper;

import epam.entity.Training;
import epam.response_dto.TrainingResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrainingMapper {

    private final TrainerMapper trainerMapper;
    private final TraineeMapper traineeMapper;

    public TrainingResponseDTO toTrainingResponseDTO(Training training) {
        return TrainingResponseDTO.builder()
                .trainee(traineeMapper.toTraineeResponseDTO(training.getTrainee()))
                .trainer(trainerMapper.toTrainerResponseDTO(training.getTrainer()))
                .trainingType(training.getTrainingType().getDescription())
                .trainingName(training.getTrainingName())
                .trainingDate(training.getTrainingDate())
                .build();
    }
}
