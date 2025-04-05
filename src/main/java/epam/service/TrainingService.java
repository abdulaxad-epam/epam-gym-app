package epam.service;


import epam.dto.request_dto.TrainingRequestDTO;
import epam.dto.response_dto.TrainingResponseDTO;

public interface TrainingService {

    TrainingResponseDTO createTraining(TrainingRequestDTO training);

    void deleteTraining(String username);
}
