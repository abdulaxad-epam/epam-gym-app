package epam.service;


import epam.client.dto.TrainingRequestDTO;
import epam.client.dto.TrainingResponseDTO;
import org.springframework.security.core.Authentication;

public interface TrainingService {

    TrainingResponseDTO createTraining(TrainingRequestDTO training, Authentication authentication);

    void deleteTraining(String username);
}
