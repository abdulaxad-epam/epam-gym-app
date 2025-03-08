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

    public TrainingResponseDTO toTrainingResponseDTO(Training training) {
        if (training == null) {
            return null;
        }

        return TrainingResponseDTO.builder()
                .trainee(training.getTrainee() != null ? traineeMapper.toTraineeResponseDTO(training.getTrainee()) : null)
                .trainer(training.getTrainer() != null ? trainerMapper.toTrainerResponseDTO(training.getTrainer()) : null)
                .trainingType(training.getTrainingType() != null ? training.getTrainingType().getDescription() : null)
                .trainingName(training.getTrainingName())
                .trainingDate(training.getTrainingDate())
                .build();
    }

    public Training toTraining(TrainingRequestDTO trainingRequestDTO, TrainingType trainingType, Trainer trainer, Trainee trainee) {
        if (trainingRequestDTO == null) {
            return null;
        }

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
