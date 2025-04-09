package epam.service;

import epam.dto.response_dto.TrainerResponseDTO;

import java.util.List;

public interface TraineeTrainerService {

    List<TrainerResponseDTO> updateTraineeTrainer(String username, List<String> trainerUsernames);

    List<TrainerResponseDTO> getAllNotAssignedTrainers(String username);

    void assignTrainerToTrainee(String traineeUsername, String trainerUsername);
}
