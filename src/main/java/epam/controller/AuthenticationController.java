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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication and registration")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "Register a new trainer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Trainer registered successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RegisterTrainerResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping(value = "/register/trainer", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<RegisterTrainerResponseDTO> createTrainer(
            @Valid @RequestBody RegisterTrainerRequestDTO trainerRequestDTO,
            HttpServletResponse response) {
        return ResponseEntity.ok(authenticationService.register(trainerRequestDTO, response));
    }

    @Operation(summary = "Register a new trainee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainee registered successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RegisterTraineeResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping(value = "/register/trainee", consumes = "application/json", produces = "application/json")
    public ResponseEntity<RegisterTraineeResponseDTO> createTrainee(
            @Valid @RequestBody RegisterTraineeRequestDTO traineeRequestDTO,
            HttpServletResponse response) {
        return ResponseEntity.ok(authenticationService.register(traineeRequestDTO, response));
    }

    @Operation(summary = "Authenticate a user (trainee or trainer)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - invalid credentials", content = @Content),
            @ApiResponse(responseCode = "404", description = "Trainee not found", content = @Content)
    })
    @PostMapping(value = "/authenticate", consumes = "application/json")
    public ResponseEntity<Void> authenticateTrainee(
            @Valid @RequestBody AuthenticateRequestDTO authenticateRequestDTO,
            HttpServletResponse response) throws TraineeNotFoundException {
        authenticationService.authenticate(authenticateRequestDTO, response);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Change password for a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password changed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Trainee not found", content = @Content)
    })
    @PutMapping(value = "/changePassword", consumes = "application/json")
    public ResponseEntity<Void> changePassword(
            @Valid @RequestBody ChangePasswordRequestDTO changePasswordRequestDTO,
            HttpServletResponse response) throws TraineeNotFoundException {
        authenticationService.changePassword(changePasswordRequestDTO, response);
        return ResponseEntity.ok().build();
    }
}
