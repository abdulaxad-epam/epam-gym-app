package epam.service;


import epam.request_dto.TrainingRequestDTO;
import epam.response_dto.TrainingResponseDTO;

import java.util.List;

public interface TrainingService {

    TrainingResponseDTO createTraining(TrainingRequestDTO training);

    TrainingResponseDTO updateTraining(String username, TrainingRequestDTO training);

    TrainingResponseDTO getTrainingByUsername(String username);

    List<TrainingResponseDTO> getAllTrainings();

    void deleteTraining(String username);

    List<TrainingResponseDTO> getTrainingsByTraineeUsername(String username);
}
