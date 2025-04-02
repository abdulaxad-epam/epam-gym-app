package epam.repository;

import epam.entity.TrainerTrainee;
import epam.entity.Trainee;
import epam.entity.Trainer;

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
