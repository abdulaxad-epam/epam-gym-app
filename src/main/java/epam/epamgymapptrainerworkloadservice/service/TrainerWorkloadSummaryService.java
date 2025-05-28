package epam.epamgymapptrainerworkloadservice.service;

import epam.epamgymapptrainerworkloadservice.dto.TrainerWorkloadSummaryResponseDTO;

import java.time.Year;

public interface TrainerWorkloadSummaryService {
    TrainerWorkloadSummaryResponseDTO getTrainerWorkloadSummary(String trainerUsername, Year year);
}
