package epam.controller;

import epam.aop.Authenticated;
import epam.dto.response_dto.TraineeResponseDTO;
import epam.dto.response_dto.TrainingResponseDTO;
import epam.service.TraineeService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/trainees")
@RequiredArgsConstructor
public class TraineeController {

    private final TraineeService traineeService;

    @Authenticated
    @GetMapping(value = "/{username}", produces = "application/json")
    public ResponseEntity<TraineeResponseDTO> getTraineeByUsername(@PathVariable("username") @NotBlank(message = "Username is required") String username) {
        return ResponseEntity.ok(traineeService.getTraineeByUsername(username));
    }

    @Authenticated
    @GetMapping(value = "/{username}/trainings", produces = "application/json")
    public ResponseEntity<List<TrainingResponseDTO>> getTrainingByUsername(@PathVariable(value = "username") @NotBlank(message = "Username is required") String username,
                                                                           @RequestParam(value = "periodFrom", required = false) String periodFrom,
                                                                           @RequestParam(value = "periodTo", required = false) String periodTo,
                                                                           @RequestParam(value = "trainerName", required = false) String trainerName,
                                                                           @RequestParam(value = "trainingType", required = false) String trainingType) {
        return ResponseEntity.ok(traineeService.getTraineeTrainings(username, periodFrom, periodTo, trainerName, trainingType));
    }

    @Authenticated
    @PutMapping(value = "/update/{username}", produces = "application/json")
    public ResponseEntity<TraineeResponseDTO> update(@PathVariable(value = "username") @NotBlank(message = "Username is required") String username,
                                                     @RequestParam(value = "firstname") @NotBlank(message = "Firstname is required")  String firstname,
                                                     @RequestParam(value = "lastname") @NotBlank(message = "Lastname is required") String lastname,
                                                     @RequestParam(value = "dateOfBirth", required = false) String dateOfBirth,
                                                     @RequestParam(value = "address", required = false) String address,
                                                     @RequestParam(value = "isActive", required = false) Boolean isActive
    ) {
        return ResponseEntity.ok(traineeService.updateTrainee(username, firstname, lastname, dateOfBirth, address, isActive));
    }

    @Authenticated
    @DeleteMapping(value = "/delete/{username}", produces = "application/json")
    public ResponseEntity<Void> delete(@PathVariable("username") @NotBlank(message = "Username is required") String username) {
        traineeService.deleteTrainee(username);
        return ResponseEntity.ok().build();
    }

    @Authenticated
    @PatchMapping(value = "/status/{username}")
    public ResponseEntity<Void> updateTraineeStatus(@PathVariable(value = "username") @NotBlank(message = "Username is required") String username,
                                                    @RequestParam(value = "isActive") @NotNull(message = "isActive status must be provided") Boolean isActive) {
        traineeService.updateTraineeStatus(username, isActive);
        return ResponseEntity.ok().build();
    }
}
