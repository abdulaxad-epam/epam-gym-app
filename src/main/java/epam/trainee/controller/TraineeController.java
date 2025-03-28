package epam.trainee.controller;

import epam.shared.security.aop.Authenticated;
import epam.trainee.dto.TraineeResponseDTO;
import epam.trainee.service.TraineeService;
import epam.training.dto.TrainingResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.aspectj.lang.annotation.Aspect;
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

@Log
@RestController
@RequestMapping("api/v1/trainees")
@RequiredArgsConstructor
public class TraineeController {

    private final TraineeService traineeService;

    @Authenticated
    @GetMapping(value = "/{username}", produces = "application/json")
    public ResponseEntity<TraineeResponseDTO> getTraineeByUsername(@PathVariable("username") String username, HttpServletRequest request) {
        log.info("Get trainee by username: " + username);
        return ResponseEntity.ok(traineeService.getTraineeByUsername(username));
    }

    @Authenticated
    @GetMapping(value = "/{username}/trainings", produces = "application/json")
    public ResponseEntity<List<TrainingResponseDTO>> getTrainingByUsername(@PathVariable(value = "username") String username,
                                                                           @RequestParam(value = "periodFrom", required = false) String periodFrom,
                                                                           @RequestParam(value = "periodTo", required = false) String periodTo,
                                                                           @RequestParam(value = "trainerName", required = false) String trainerName,
                                                                           @RequestParam(value = "trainingType", required = false) String trainingType) {
        log.info("Fetch all trainings of trainee by username: " + username);
        return ResponseEntity.ok(traineeService.getTraineeTrainings(username, periodFrom, periodTo, trainerName, trainingType));
    }

    @Authenticated
    @PutMapping(value = "/update/{username}", produces = "application/json")
    public ResponseEntity<TraineeResponseDTO> update(@PathVariable(value = "username") String username,
                                                     @RequestParam(value = "firstname") String firstname,
                                                     @RequestParam(value = "lastname") String lastname,
                                                     @RequestParam(value = "dateOfBirth", required = false) String dateOfBirth,
                                                     @RequestParam(value = "address", required = false) String address,
                                                     @RequestParam(value = "isActive", required = false) Boolean isActive
    ) {
        log.info("Updating trainee:  "
                + username + " " + firstname + " " + lastname + " " + dateOfBirth + " " + address + " " + isActive);

        return ResponseEntity.ok(traineeService.updateTrainee(username, firstname, lastname, dateOfBirth, address, isActive));
    }

    @Authenticated
    @DeleteMapping(value = "/delete/{username}", produces = "application/json")
    public ResponseEntity<Void> delete(@PathVariable("username") String username) {
        log.info("Deleting trainee: " + username);
        traineeService.deleteTrainee(username);
        return ResponseEntity.ok().build();
    }

    @Authenticated
    @PatchMapping(value = "/status/{username}")
    public ResponseEntity<Void> updateTraineeStatus(@PathVariable(value = "username") String username,
                                                    @RequestParam(value = "isActive") Boolean isActive) {
        log.info("Trainee status is being updated:  {}, {}" + username + isActive);
        traineeService.updateTraineeStatus(username, isActive);
        return ResponseEntity.ok().build();
    }
}
