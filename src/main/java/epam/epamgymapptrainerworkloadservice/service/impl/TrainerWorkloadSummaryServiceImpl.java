package epam.epamgymapptrainerworkloadservice.service.impl;

import epam.epamgymapptrainerworkloadservice.dto.TrainerWorkloadSummaryResponseDTO;
import epam.epamgymapptrainerworkloadservice.entity.TrainerWorkload;
import epam.epamgymapptrainerworkloadservice.service.TrainerWorkloadService;
import epam.epamgymapptrainerworkloadservice.service.TrainerWorkloadSummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainerWorkloadSummaryServiceImpl implements TrainerWorkloadSummaryService {

    private final TrainerWorkloadService trainerWorkloadService;

    @Override
    public TrainerWorkloadSummaryResponseDTO getTrainerWorkloadSummary(String trainerUsername, Year year) {
        List<TrainerWorkload> trainerWorkload = trainerWorkloadService.getTrainerWorkload(trainerUsername, year.getValue());

        String trainerFirstName = trainerWorkload.get(0).getTrainerFirstName();
        String trainerLastName = trainerWorkload.get(0).getTrainerLastName();
        String username = trainerWorkload.get(0).getTrainerUsername();
        Boolean isActive = trainerWorkload.get(0).getIsActive();

        return TrainerWorkloadSummaryResponseDTO.builder()
                .firstName(trainerFirstName)
                .lastName(trainerLastName)
                .username(username)
                .status(isActive)
                .workloadSummaryInYears(List.of()).build();
    }
}
