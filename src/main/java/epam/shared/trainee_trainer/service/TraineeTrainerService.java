package epam.shared.trainee_trainer.service;

public interface TraineeTrainerService {

   Boolean addTrainerToTrainee(String currentUsername, String trainerUsername);

   Boolean removeTrainerFromTrainee(String currentUsername, String trainerUsername);
}
