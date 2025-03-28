package epam.shared.trainee_trainer.service.impl;

import epam.trainee.entity.Trainee;
import epam.trainee.repository.TraineeRepository;
import epam.trainer.entity.Trainer;
import epam.shared.exception.exception.TraineeHasNotAssignedBeforeException;
import epam.shared.exception.exception.TraineeNotFoundException;
import epam.shared.exception.exception.TrainerNotFoundException;
import epam.shared.trainee_trainer.service.TraineeTrainerService;
import epam.trainer.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainerTraineeServiceImpl implements TraineeTrainerService {

    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;

    @Override
    public Boolean addTrainerToTrainee(String currentUsername, String trainerUsername) {

        Trainee trainee = traineeRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new TraineeNotFoundException(currentUsername));
        Trainer trainer = trainerRepository.findByUsername(trainerUsername)
                .orElseThrow(() -> new TrainerNotFoundException(trainerUsername));

        if (trainerRepository.trainerHasTrainee(trainer.getTrainerId(), trainee.getTraineeId())) {
            trainerRepository.addTrainerToTrainee(trainee, trainer);
            return true;
        }
        throw new TrainerNotFoundException(trainerUsername);
    }

    @Override
    public Boolean removeTrainerFromTrainee(String currentUsername, String trainerUsername) {

        Trainee trainee = traineeRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new TraineeNotFoundException(currentUsername));
        Trainer trainer = trainerRepository.findByUsername(trainerUsername)
                .orElseThrow(() -> new TrainerNotFoundException(trainerUsername));
        if (trainerRepository.trainerHasTrainee(trainer.getTrainerId(), trainee.getTraineeId())) {
            trainerRepository.removeTraineeOfTrainer(trainee, trainer);
            return true;
        }
        throw new TraineeHasNotAssignedBeforeException(trainerUsername);
    }
}
