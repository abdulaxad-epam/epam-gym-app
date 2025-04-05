package epam.controller;

import epam.aop.Authenticated;
import epam.dto.request_dto.TrainerRequestDTO;
import epam.dto.response_dto.TrainerResponseDTO;
import epam.dto.response_dto.TrainingResponseDTO;
import epam.service.TrainerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/trainers")
public class TrainerController {
    private final TrainerService trainerService;

    @Authenticated
    @GetMapping(value = "/{username}", produces = "application/json")
    public ResponseEntity<TrainerResponseDTO> getTrainer(@PathVariable("username") @NotBlank(message = "Username is required") String username) {
        return ResponseEntity.ok(trainerService.getTrainerByUsername(username));
    }

    @Authenticated
    @PutMapping(value = "/update/{username}", produces = "application/json")
    public ResponseEntity<TrainerResponseDTO> updateTrainer(@PathVariable("username") @NotBlank(message = "Username is required") String username,
                                                            @Valid @RequestBody TrainerRequestDTO traineeRequestDTO) {
        return ResponseEntity.ok(trainerService.updateTrainer(username, traineeRequestDTO));
    }

    @Authenticated
    @GetMapping(value = "/{username}/trainings", produces = "application/json")

    public ResponseEntity<List<TrainingResponseDTO>> getTrainings(@PathVariable(value = "username") @NotBlank(message = "Username is required") String username,
                                                                  @RequestParam(value = "periodFrom", required = false) String periodFrom,
                                                                  @RequestParam(value = "periodTo", required = false) String periodTo,
                                                                  @RequestParam(value = "traineeName", required = false) String traineeName) {
        return ResponseEntity.ok(trainerService.getTrainerTrainings(username, periodFrom, periodTo, traineeName));
    }

    @Authenticated
    @PatchMapping(value = "/status/{username}")
    public ResponseEntity<Void> trainerStatus(@PathVariable(value = "username") @NotBlank(message = "Username is required") String username,
                                              @RequestParam(value = "isActive") @NotNull(message = "isActive status must be provided") Boolean isActive) {
        trainerService.updateTrainerStatus(username, isActive);
        return ResponseEntity.ok().build();
    }
}
