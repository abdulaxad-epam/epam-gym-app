package epam.controller;

import epam.service.impl.Authentication;
import epam.dto.response_dto.TrainerResponseDTO;
import epam.service.TraineeTrainerService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/trainee-trainer")
@Tag(name = "Trainee-Trainer", description = "Operations for managing trainee-trainer associations")
public class TraineeTrainerController {

    private final TraineeTrainerService traineeTrainerService;

    private final Authentication authentication;

    @Operation(summary = "Get trainers not yet assigned to the trainee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of unassigned trainers retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TrainerResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Trainee not found", content = @Content)
    })
    @GetMapping(value = "/{username}/not-assigned-trainers", produces = "application/json")
    public ResponseEntity<List<TrainerResponseDTO>> getNotAssignedTrainers(
            @Parameter(description = "Username of the trainee", required = true)
            @PathVariable("username") @NotBlank(message = "Username is required") String username,
            @RequestHeader("password") String password) {
        authentication.checkAuthentication(username, password);
        return ResponseEntity.ok(traineeTrainerService.getAllNotAssignedTrainers(username));
    }

    @Operation(summary = "Update the list of trainers assigned to a trainee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainee's trainer list updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TrainerResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid trainer list", content = @Content),
            @ApiResponse(responseCode = "404", description = "Trainee not found", content = @Content)
    })
    @PutMapping(value = "/update/{username}", produces = "application/json")
    public ResponseEntity<List<TrainerResponseDTO>> updateTraineeTrainerList(
            @Parameter(description = "Username of the trainee", required = true)
            @PathVariable(value = "username") @NotBlank(message = "Username is required") String username,

            @Parameter(description = "List of trainer usernames to assign", required = true)
            @RequestParam(value = "trainers") List<String> trainers,
            @RequestHeader("password") String password) {
        authentication.checkAuthentication(username, password);
        return ResponseEntity.ok(traineeTrainerService.updateTraineeTrainer(username, trainers));
    }
}
