package epam.service;


import epam.entity.Training;
import epam.response_dto.TrainingResponseDTO;

import java.util.List;
import java.util.UUID;

public interface TrainingService {

    TrainingResponseDTO createTraining(UUID id, Training training);

    TrainingResponseDTO updateTraining(UUID id, Training training);

    TrainingResponseDTO getTrainingById(UUID id);

    List<TrainingResponseDTO> getAllTrainings();

    void deleteTraining(UUID id);
}
