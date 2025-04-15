package epam.service;

import epam.dto.response_dto.TrainerResponseDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface TraineeTrainerService {

    List<TrainerResponseDTO> updateTraineeTrainer(Authentication connectedUser, List<String> trainerUsernames);

    List<TrainerResponseDTO> getAllNotAssignedTrainers(Authentication connectedUser);

    void assignTrainerToTrainee(String traineeUsername, String trainerUsername);
}
