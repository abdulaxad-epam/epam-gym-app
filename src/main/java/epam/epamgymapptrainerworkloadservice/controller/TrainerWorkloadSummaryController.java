package epam.epamgymapptrainerworkloadservice.controller;

import epam.epamgymapptrainerworkloadservice.dto.TrainerWorkloadSummaryResponseDTO;
import epam.epamgymapptrainerworkloadservice.service.TrainerWorkloadSummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Year;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/trainer-workload-summary")
public class TrainerWorkloadSummaryController {
    private final TrainerWorkloadSummaryService trainerWorkloadSummaryService;

    @GetMapping("/")
    public ResponseEntity<TrainerWorkloadSummaryResponseDTO> getTrainerWorkloadSummary(
            @RequestParam(value = "trainerUsername") String trainerUsername,
            @RequestParam(value = "year", required = false) Year year
    ) {
        return ResponseEntity.ok(trainerWorkloadSummaryService.getTrainerWorkloadSummary(trainerUsername, year));
    }
}
