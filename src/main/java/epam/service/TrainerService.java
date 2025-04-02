package epam.service;

import epam.dto.response_dto.RegisterTrainerResponseDTO;
import epam.dto.request_dto.TrainerRequestDTO;
import epam.dto.response_dto.TrainerResponseDTO;
import epam.dto.response_dto.TrainingResponseDTO;

import java.util.List;

public interface TrainerService {

    RegisterTrainerResponseDTO createTrainer(TrainerRequestDTO training);

    TrainerResponseDTO updateTrainer(String username, TrainerRequestDTO trainer);

    void deleteTrainer(String username);

    TrainerResponseDTO getTrainerByUsername(String username);


    List<TrainingResponseDTO> getTrainerTrainings(String username, String periodFrom, String periodTo, String traineeName);

    void updateTrainerStatus(String username, Boolean isActive);
}
