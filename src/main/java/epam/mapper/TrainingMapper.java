package epam.mapper;

import epam.entity.Trainee;
import epam.entity.Trainer;
import epam.entity.Training;
import epam.entity.TrainingType;
import epam.request_dto.TrainingRequestDTO;
import epam.response_dto.TrainingResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrainingMapper {

    private final TrainerMapper trainerMapper;
    private final TraineeMapper traineeMapper;
    private final TrainingTypeMapper trainingTypeMapper;

    public TrainingResponseDTO toTrainingResponseDTO(Training training) {
        return TrainingResponseDTO.builder()
                .trainee(traineeMapper.toTraineeResponseDTO(training.getTrainee()))
                .trainer(trainerMapper.toTrainerResponseDTO(training.getTrainer()))
                .trainingType(training.getTrainingType().getDescription())
                .trainingName(training.getTrainingName())
                .trainingDate(training.getTrainingDate())
                .build();
    }

    public Training toTraining(TrainingRequestDTO trainingRequestDTO, TrainingType trainingType, Trainer trainer, Trainee trainee) {
        return Training.builder()
                .trainingDate(trainingRequestDTO.getTrainingDate())
                .trainingDuration(trainingRequestDTO.getTrainingDuration())
                .trainee(trainee)
                .trainer(trainer)
                .trainingName(trainingRequestDTO.getTrainingName())
                .trainingType(trainingType)
                .build();

    }
}
