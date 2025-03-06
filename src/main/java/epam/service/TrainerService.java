package epam.service;

import epam.entity.Trainer;
import epam.response_dto.TrainerResponseDTO;

import java.util.List;
import java.util.UUID;

public interface TrainerService {

    TrainerResponseDTO createTrainer(UUID id, Trainer training);


    TrainerResponseDTO updateTrainer(UUID id, Trainer trainer);

    void deleteTrainer(String username);

    TrainerResponseDTO getTrainerByUsername(String username);

    List<TrainerResponseDTO> getAllTrainers();
}
