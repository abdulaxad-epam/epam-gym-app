package epam.controller;

import epam.dto.request_dto.AuthenticateRequestDTO;
import epam.dto.request_dto.TraineeRequestDTO;
import epam.dto.response_dto.TraineeResponseDTO;
import epam.exception.TraineeNotFoundException;
import epam.service.TraineeService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log
@RestController
@RequestMapping
@RequiredArgsConstructor
public class TraineeController {

    private final TraineeService traineeService;

    @PostMapping(value = "/authenticate", consumes = "application/json")
    public TraineeResponseDTO buy(@RequestBody AuthenticateRequestDTO authenticateRequestDTO) throws TraineeNotFoundException {
        TraineeResponseDTO traineeByUsername = traineeService.getTraineeByUsername(authenticateRequestDTO.getUsername());
            if (traineeByUsername == null) {
                log.info("Trainee with username: " + authenticateRequestDTO.getUsername() + " not found");
                throw new TraineeNotFoundException("Trainee with username " + authenticateRequestDTO.getUsername() + " not found");
            }
        return traineeByUsername;
    }


    @PostMapping(value = "/register", consumes = "application/json")
    public TraineeResponseDTO trainee(@RequestBody TraineeRequestDTO traineeRequestDTO){
        log.info("New trainee signed up:  "+traineeRequestDTO);
        return traineeService.createTrainee(traineeRequestDTO);
    }
}
