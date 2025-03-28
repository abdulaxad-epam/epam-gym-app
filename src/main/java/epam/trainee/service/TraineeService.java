package epam.trainee.service;

import epam.trainee.dto.TraineeRequestDTO;
import epam.trainee.dto.TraineeResponseDTO;
import epam.training.dto.TrainingResponseDTO;

import java.util.List;

public interface TraineeService {

    TraineeResponseDTO createTrainee(TraineeRequestDTO trainee);

    void deleteTrainee(String username);

    TraineeResponseDTO getTraineeByUsername(String username);

    List<TraineeResponseDTO> getAllTrainees();

    List<TraineeResponseDTO> getTraineesByTrainer(String currentUsername);

    List<TrainingResponseDTO> getTraineeTrainings(String username, String periodFrom, String periodTo, String trainerName, String trainingType);

    void updateTraineeStatus(String username, Boolean isActive);

    TraineeResponseDTO updateTrainee(String username, String firstname, String lastname, String dateOfBirth, String address, Boolean isActive);
}
