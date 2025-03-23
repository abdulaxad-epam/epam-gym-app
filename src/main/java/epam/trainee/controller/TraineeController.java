package epam.trainee.controller;

import epam.shared.security.dto.AuthenticateRequestDTO;
import epam.shared.exception.exception.TraineeNotFoundException;
import epam.trainee.dto.TraineeRequestDTO;
import epam.trainee.dto.TraineeResponseDTO;
import epam.trainee.service.TraineeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log
@RestController
@RequestMapping("api/v1/trainee")
@RequiredArgsConstructor
public class TraineeController {

    private final TraineeService traineeService;

    @PostMapping(value = "/authenticate", consumes = "application/json", produces = "application/json")
    public TraineeResponseDTO buy(@RequestBody AuthenticateRequestDTO authenticateRequestDTO) throws TraineeNotFoundException {
        TraineeResponseDTO traineeByUsername = traineeService.getTraineeByUsername(authenticateRequestDTO.getUsername());
            if (traineeByUsername == null) {
                log.info("Trainee with username: " + authenticateRequestDTO.getUsername() + " not found");
                throw new TraineeNotFoundException("Trainee with username " + authenticateRequestDTO.getUsername() + " not found");
            }
        return traineeByUsername;
    }


    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    public TraineeResponseDTO trainee(@RequestBody TraineeRequestDTO traineeRequestDTO){
        log.info("New trainee signed up:  "+traineeRequestDTO);
        return traineeService.createTrainee(traineeRequestDTO);
    }
}
