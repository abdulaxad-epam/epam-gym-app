package epam.service;

import epam.dto.request_dto.TrainerRequestDTO;
import epam.dto.response_dto.TrainerResponseDTO;

import java.util.List;

public interface TrainerService {

    TrainerResponseDTO createTrainer(TrainerRequestDTO training);

    TrainerResponseDTO updateTrainer(String username, TrainerRequestDTO trainer);

    void deleteTrainer(String username);

    TrainerResponseDTO getTrainerByUsername(String username);

    List<TrainerResponseDTO> getAllTrainers();

    List<TrainerResponseDTO> getTrainersByTrainee(String currentUsername);
}
