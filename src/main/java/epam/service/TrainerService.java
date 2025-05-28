package epam.service;

import epam.dto.request_dto.TrainerRequestDTO;
import epam.dto.response_dto.RegisterTrainerResponseDTO;
import epam.dto.response_dto.TrainerResponseDTO;
import epam.client.dto.TrainingResponseDTO;
import epam.entity.Trainer;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.UUID;

public interface TrainerService {

    RegisterTrainerResponseDTO createTrainer(TrainerRequestDTO training);

    TrainerResponseDTO updateTrainer(Authentication connectedUser, TrainerRequestDTO trainer);

    void deleteTrainer(Authentication connectedUser);

    TrainerResponseDTO getTrainerProfile(Authentication connectedUser);

    List<TrainingResponseDTO> getTrainerTrainings(Authentication connectedUser, String periodFrom, String periodTo, String traineeName);

    void updateTrainerStatus(Authentication connectedUser, Boolean isActive);

    Trainer getTrainerProfile(String username);

    Trainer getTrainerProfile(UUID trainerId);
}
