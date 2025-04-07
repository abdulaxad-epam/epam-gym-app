package epam.controller;

import epam.service.impl.Authentication;
import epam.dto.request_dto.TrainerRequestDTO;
import epam.dto.response_dto.TrainerResponseDTO;
import epam.dto.response_dto.TrainingResponseDTO;
import epam.service.TrainerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@RequestMapping("api/v1/trainers")
@Tag(name = "Trainer", description = "Operations related to trainers")
public class TrainerController {

    private final TrainerService trainerService;

    private final Authentication authentication;

    @Operation(summary = "Get trainer details by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainer details retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TrainerResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Trainer not found", content = @Content)
    })
    @GetMapping(value = "/{username}", produces = "application/json")
    public ResponseEntity<TrainerResponseDTO> getTrainer(
            @Parameter(description = "Username of the trainer", required = true)
            @PathVariable("username") @NotBlank(message = "Username is required") String username,
            @RequestHeader("password") String password) {
        authentication.checkAuthentication(username, password);
        return ResponseEntity.ok(trainerService.getTrainerByUsername(username));
    }

    @Operation(summary = "Update trainer profile by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainer updated successfully",
                    content = @Content(schema = @Schema(implementation = TrainerResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Trainer not found", content = @Content)
    })
    @PutMapping(value = "/update/{username}", produces = "application/json")
    public ResponseEntity<TrainerResponseDTO> updateTrainer(
            @Parameter(description = "Username of the trainer", required = true)
            @PathVariable("username") @NotBlank(message = "Username is required") String username,
            @Parameter(description = "Updated trainer data", required = true)
            @Valid @RequestBody TrainerRequestDTO traineeRequestDTO,
            @RequestHeader("password") String password) {
        authentication.checkAuthentication(username, password);
        return ResponseEntity.ok(trainerService.updateTrainer(username, traineeRequestDTO));
    }

    @Operation(summary = "Get trainings conducted by a trainer with optional filters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainings list retrieved",
                    content = @Content(schema = @Schema(implementation = TrainingResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Trainer not found", content = @Content)
    })
    @GetMapping(value = "/{username}/trainings", produces = "application/json")
    public ResponseEntity<List<TrainingResponseDTO>> getTrainings(
            @Parameter(description = "Trainer's username", required = true)
            @PathVariable(value = "username") @NotBlank(message = "Username is required") String username,

            @Parameter(description = "Filter by start date (yyyy-MM-dd)")
            @RequestParam(value = "periodFrom", required = false) String periodFrom,

            @Parameter(description = "Filter by end date (yyyy-MM-dd)")
            @RequestParam(value = "periodTo", required = false) String periodTo,

            @Parameter(description = "Filter by trainee name")
            @RequestParam(value = "traineeName", required = false) String traineeName,
            @RequestHeader("password") String password) {
        authentication.checkAuthentication(username, password);
        return ResponseEntity.ok(trainerService.getTrainerTrainings(username, periodFrom, periodTo, traineeName));
    }

    @Operation(summary = "Update trainer's active status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainer status updated"),
            @ApiResponse(responseCode = "400", description = "Invalid status update request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Trainer not found", content = @Content)
    })
    @PatchMapping(value = "/status/{username}")
    public ResponseEntity<Void> trainerStatus(
            @Parameter(description = "Username of the trainer", required = true)
            @PathVariable(value = "username") @NotBlank(message = "Username is required") String username,

            @Parameter(description = "New active status (true/false)", required = true)
            @RequestParam(value = "isActive") @NotNull(message = "isActive status must be provided") Boolean isActive,
            @RequestHeader("password") String password) {
        authentication.checkAuthentication(username, password);
        trainerService.updateTrainerStatus(username, isActive);
        return ResponseEntity.ok().build();
    }
}
