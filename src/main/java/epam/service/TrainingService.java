package epam.service;


import epam.entity.Training;
import epam.request_dto.TrainingRequestDTO;
import epam.response_dto.TrainingResponseDTO;

import java.util.List;
import java.util.UUID;

public interface TrainingService {

    TrainingResponseDTO createTraining(TrainingRequestDTO training);

    TrainingResponseDTO updateTraining(String username, TrainingRequestDTO training);

    TrainingResponseDTO getTrainingByUsername(String username);

    List<TrainingResponseDTO> getAllTrainings();

    void deleteTraining(String username);

    List<TrainingResponseDTO> getTrainingsByTraineeUsername(String username);
}
