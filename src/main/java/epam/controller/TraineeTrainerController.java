package epam.controller;

import epam.aop.Authenticated;
import epam.dto.response_dto.TrainerResponseDTO;
import epam.service.TraineeTrainerService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/trainee-trainer")
public class TraineeTrainerController {

    private final TraineeTrainerService traineeTrainerService;

    @Authenticated
    @GetMapping(value = "/{username}/not-assigned-trainers", produces = "application/json")
    public ResponseEntity<List<TrainerResponseDTO>> getNotAssignedTrainers(@PathVariable("username") @NotBlank(message = "Username is required") String username) {
        return ResponseEntity.ok(traineeTrainerService.getAllNotAssignedTrainers(username));
    }

    @Authenticated
    @PutMapping(value = "/update/{username}", produces = "application/json")
    public ResponseEntity<List<TrainerResponseDTO>> updateTraineeTrainerList(@PathVariable(value = "username") @NotBlank(message = "Username is required") String username,
                                                                             @RequestParam(value = "trainers") List<String> trainers) {
        return ResponseEntity.ok(traineeTrainerService.updateTraineeTrainer(username, trainers));
    }

}
