package epam.training.service;


import epam.training.dto.TrainingRequestDTO;
import epam.training.dto.TrainingResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface TrainingService {

    TrainingResponseDTO createTraining(TrainingRequestDTO training);

    TrainingResponseDTO getTrainingByUsername(String username);

    List<TrainingResponseDTO> getAllTrainings();

    void deleteTraining(String username);

    List<TrainingResponseDTO> getTrainingsByTraineeUsername(String username);

}
