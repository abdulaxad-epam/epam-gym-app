package epam.shared.trainee_trainer.service;

import epam.trainer.dto.TrainerResponseDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TraineeTrainerService {

    List<TrainerResponseDTO> updateTraineeTrainer(String username, List<String> trainerUsernames);

    List<TrainerResponseDTO> getAllNotAssignedTrainers(String username);

    void assignTrainerToTrainee(String traineeUsername, String trainerUsername);

    Boolean removeTrainerFromTrainee(String currentUsername, String trainerUsername);
}
