package epam.service;


import epam.dto.request_dto.TrainingRequestDTO;
import epam.dto.response_dto.TrainingResponseDTO;
import org.springframework.security.core.Authentication;

public interface TrainingService {

    TrainingResponseDTO createTraining(TrainingRequestDTO training, Authentication authentication);

    void deleteTraining(String username);
}
