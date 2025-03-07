package epam.service_impl;

import epam.entity.Trainee;
import epam.entity.Trainer;
import epam.exception.TraineeHasNotAssignedBeforeException;
import epam.exception.TraineeNotFoundException;
import epam.exception.TrainerNotFoundException;
import epam.repository.TraineeRepository;
import epam.repository.TrainerRepository;
import epam.service.TraineeTrainerService;
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
        }else throw new TrainerNotFoundException(trainerUsername);

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
        else throw new TraineeHasNotAssignedBeforeException(trainerUsername);

    }
}
