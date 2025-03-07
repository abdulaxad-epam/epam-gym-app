package epam.service;


import epam.entity.Training;
import epam.response_dto.TrainingResponseDTO;

import java.util.List;
import java.util.UUID;

public interface TrainingService {

    TrainingResponseDTO createTraining(Training training);

    TrainingResponseDTO updateTraining(String username, Training training);

    TrainingResponseDTO getTrainingByUsername(String username);

    List<TrainingResponseDTO> getAllTrainings();

    void deleteTraining(String username);

    List<TrainingResponseDTO> getTrainingsByTraineeUsername(String username);
}
