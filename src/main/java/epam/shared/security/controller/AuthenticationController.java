package epam.shared.security.controller;


import epam.shared.exception.exception.TraineeNotFoundException;
import epam.shared.security.dto.AuthenticateRequestDTO;
import epam.shared.security.dto.ChangePasswordRequestDTO;
import epam.shared.security.dto.RegisterTraineeRequestDTO;
import epam.shared.security.dto.RegisterTrainerRequestDTO;
import epam.shared.security.service.AuthenticationService;
import epam.trainee.dto.TraineeResponseDTO;
import epam.trainer.dto.TrainerResponseDTO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log
@Aspect
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = "/register/trainer", consumes = "application/json", produces = "application/json")
    public ResponseEntity<TrainerResponseDTO> createTrainer(@RequestBody RegisterTrainerRequestDTO traineeRequestDTO, HttpServletResponse response) {
        log.info("New trainer signed up:  {}"+ traineeRequestDTO);
        return ResponseEntity.ok(authenticationService.register(traineeRequestDTO, response));
    }

    @PostMapping(value = "/register/trainee", consumes = "application/json", produces = "application/json")
    public ResponseEntity<TraineeResponseDTO> createTrainee(@RequestBody RegisterTraineeRequestDTO traineeRequestDTO, HttpServletResponse response) {
        log.info("New trainee signed up:  " + traineeRequestDTO);
        return ResponseEntity.ok(authenticationService.register(traineeRequestDTO, response));
    }


    @PostMapping(value = "/authenticate", consumes = "application/json")
    public ResponseEntity<Void> authenticateTrainee(@RequestBody AuthenticateRequestDTO authenticateRequestDTO, HttpServletResponse response) throws TraineeNotFoundException {
        log.info("User tries to authenticate: " + authenticateRequestDTO.getUsername());
        authenticationService.authenticate(authenticateRequestDTO, response);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/changePassword", consumes = "application/json")
    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordRequestDTO changePasswordRequestDTO, HttpServletResponse response) throws TraineeNotFoundException {
        log.info("Change password signed up: " + changePasswordRequestDTO.getOldPassword());
        authenticationService.changePassword(changePasswordRequestDTO, response);
        return ResponseEntity.ok().build();
    }
}
