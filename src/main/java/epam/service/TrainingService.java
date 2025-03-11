package epam.service;


import epam.dto.request_dto.TrainingRequestDTO;
import epam.dto.response_dto.TrainingResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface TrainingService {

    TrainingResponseDTO createTraining(TrainingRequestDTO training);

    TrainingResponseDTO getTrainingByUsername(String username);

    List<TrainingResponseDTO> getAllTrainings();

    List<TrainingResponseDTO> getTrainingsByTraineeUsername(String username);

    List<TrainingResponseDTO> getTrainingsByUsernameAndCriteria(String username, LocalDate fromDate, LocalDate toDate, String trainerName, String trainingType);
}
