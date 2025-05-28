package epam.epamgymapptrainerworkloadservice.controller;


import epam.epamgymapptrainerworkloadservice.dto.TrainerWorkloadRequestDTO;
import epam.epamgymapptrainerworkloadservice.dto.TrainerWorkloadResponseDTO;
import epam.epamgymapptrainerworkloadservice.service.TrainerWorkloadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/trainer-workload")
@RequiredArgsConstructor
public class TrainerWorkloadController {

    private final TrainerWorkloadService trainerWorkloadService;

    @PostMapping("/action")
    public ResponseEntity<TrainerWorkloadResponseDTO> action(@RequestBody TrainerWorkloadRequestDTO trainerWorkloadRequestDTO) {
        return ResponseEntity.ok(trainerWorkloadService.actionOn(trainerWorkloadRequestDTO));
    }
}
