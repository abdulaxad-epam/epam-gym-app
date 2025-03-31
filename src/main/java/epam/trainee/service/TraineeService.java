package epam.trainee.service;

import epam.shared.security.dto.RegisterTraineeResponseDTO;
import epam.trainee.dto.TraineeRequestDTO;
import epam.trainee.dto.TraineeResponseDTO;
import epam.trainer.dto.TrainerResponseDTO;
import epam.training.dto.TrainingResponseDTO;

import java.util.List;

public interface TraineeService {

    RegisterTraineeResponseDTO createTrainee(TraineeRequestDTO trainee);

    void deleteTrainee(String username);

    TraineeResponseDTO getTraineeByUsername(String username);

    List<TrainingResponseDTO> getTraineeTrainings(String username, String periodFrom, String periodTo, String trainerName, String trainingType);

    void updateTraineeStatus(String username, Boolean isActive);

    TraineeResponseDTO updateTrainee(String username, String firstname, String lastname, String dateOfBirth, String address, Boolean isActive);
}
