package epam.service;

import epam.dto.request_dto.TraineeRequestDTO;
import epam.dto.response_dto.RegisterTraineeResponseDTO;
import epam.dto.response_dto.TraineeResponseDTO;
import epam.dto.response_dto.TrainingResponseDTO;

import java.util.List;

public interface TraineeService {

    RegisterTraineeResponseDTO createTrainee(TraineeRequestDTO trainee);

    void deleteTrainee(String username);

    TraineeResponseDTO getTraineeByUsername(String username);

    List<TrainingResponseDTO> getTraineeTrainings(String username, String periodFrom, String periodTo, String trainerName, String trainingType);

    void updateTraineeStatus(String username, Boolean isActive);

    TraineeResponseDTO updateTrainee(String username, String firstname, String lastname, String dateOfBirth, String address, Boolean isActive);
}
