package epam.shared.trainee_trainer.repository;

import epam.shared.trainee_trainer.entity.TrainerTrainee;
import epam.trainee.entity.Trainee;
import epam.trainer.entity.Trainer;

import java.util.List;
import java.util.UUID;

public interface TrainerTraineeRepository {
    void assignTrainerToTrainee(TrainerTrainee trainerTrainee);

    List<Trainer> findByUsernameNotAssignedToTrainee(String username);

    void removeTraineeOfTrainer(Trainee trainee, Trainer trainer);

    boolean trainerHasTrainee(UUID trainer, UUID trainee);


    List<TrainerTrainee> findByTrainee_User_username(String username);

    void removeAllByTraineeUsername(String traineeUsername);
}
