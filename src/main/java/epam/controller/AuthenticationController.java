package epam.controller;


import epam.dto.request_dto.AuthenticateRequestDTO;
import epam.dto.request_dto.ChangePasswordRequestDTO;
import epam.dto.request_dto.RegisterTraineeRequestDTO;
import epam.dto.request_dto.RegisterTrainerRequestDTO;
import epam.dto.response_dto.RegisterTraineeResponseDTO;
import epam.dto.response_dto.RegisterTrainerResponseDTO;
import epam.exception.exception.TraineeNotFoundException;
import epam.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = "/register/trainer", consumes = "application/json", produces = "application/json")
    public ResponseEntity<RegisterTrainerResponseDTO> createTrainer(@Valid @RequestBody RegisterTrainerRequestDTO traineeRequestDTO, HttpServletResponse response) {
        return ResponseEntity.ok(authenticationService.register(traineeRequestDTO, response));
    }

    @PostMapping(value = "/register/trainee", consumes = "application/json", produces = "application/json")
    public ResponseEntity<RegisterTraineeResponseDTO> createTrainee(@Valid @RequestBody RegisterTraineeRequestDTO traineeRequestDTO, HttpServletResponse response) {
        return ResponseEntity.ok(authenticationService.register(traineeRequestDTO, response));
    }


    @PostMapping(value = "/authenticate", consumes = "application/json")
    public ResponseEntity<Void> authenticateTrainee(@Valid @RequestBody AuthenticateRequestDTO authenticateRequestDTO, HttpServletResponse response) throws TraineeNotFoundException {
        authenticationService.authenticate(authenticateRequestDTO, response);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/changePassword", consumes = "application/json")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordRequestDTO changePasswordRequestDTO, HttpServletResponse response) throws TraineeNotFoundException {
        authenticationService.changePassword(changePasswordRequestDTO, response);
        return ResponseEntity.ok().build();
    }
}
