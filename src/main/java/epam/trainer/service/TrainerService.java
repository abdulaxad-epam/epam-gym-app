package epam.trainer.service;

import epam.trainer.dto.TrainerRequestDTO;
import epam.trainer.dto.TrainerResponseDTO;
import epam.training.dto.TrainingResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TrainerService {

    TrainerResponseDTO createTrainer(TrainerRequestDTO training);

    TrainerResponseDTO updateTrainer(String username, TrainerRequestDTO trainer);

    void deleteTrainer(String username);

    TrainerResponseDTO getTrainerByUsername(String username);

    List<TrainerResponseDTO> getAllTrainers();

    List<TrainerResponseDTO> getTrainersByTrainee(String currentUsername);

    List<TrainingResponseDTO> getTrainerTrainings(String username, String periodFrom, String periodTo, String traineeName);

    void updateTrainerStatus(String username, Boolean isActive);
}
