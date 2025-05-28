package epam.epamgymapptrainerworkloadservice.service;

import epam.epamgymapptrainerworkloadservice.dto.TrainerWorkloadRequestDTO;
import epam.epamgymapptrainerworkloadservice.dto.TrainerWorkloadResponseDTO;
import epam.epamgymapptrainerworkloadservice.entity.TrainerWorkload;

import java.util.List;

public interface TrainerWorkloadService {
    TrainerWorkloadResponseDTO actionOn(TrainerWorkloadRequestDTO trainerWorkloadRequestDTO);

    List<TrainerWorkload> getTrainerWorkload(String trainerUsername, Integer year);
}
