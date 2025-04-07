package epam.controller;

import epam.service.impl.Authentication;
import epam.dto.response_dto.TraineeResponseDTO;
import epam.dto.response_dto.TrainingResponseDTO;
import epam.service.TraineeService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("api/v1/trainees")
@RequiredArgsConstructor
@Tag(name = "Trainee", description = "Operations related to trainees")
public class TraineeController {

    private final TraineeService traineeService;

    private final Authentication authentication;

    @Operation(summary = "Get trainee details by username")
    @ApiResponse(responseCode = "200", description = "Trainee details retrieved successfully",
            content = @Content(schema = @Schema(implementation = TraineeResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Trainee not found", content = @Content)
    @GetMapping(value = "/{username}", produces = "application/json")
    public ResponseEntity<TraineeResponseDTO> getTraineeByUsername(
            @PathVariable("username") @NotBlank(message = "Username is required") String username,
            @RequestHeader("password") String password
            ) {
        authentication.checkAuthentication(username, password);
        return ResponseEntity.ok(traineeService.getTraineeByUsername(username));
    }

    @Operation(summary = "Get trainings for a trainee by username with optional filters")
    @ApiResponse(responseCode = "200", description = "List of trainings retrieved",
            content = @Content(schema = @Schema(implementation = TrainingResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Trainee not found", content = @Content)
    @GetMapping(value = "/{username}/trainings", produces = "application/json")
    public ResponseEntity<List<TrainingResponseDTO>> getTrainingByUsername(
            @PathVariable(value = "username") @NotBlank(message = "Username is required") String username,
            @RequestParam(value = "periodFrom", required = false) String periodFrom,
            @RequestParam(value = "periodTo", required = false) String periodTo,
            @RequestParam(value = "trainerName", required = false) String trainerName,
            @RequestParam(value = "trainingType", required = false) String trainingType,
            @RequestHeader("password") String password) {
        authentication.checkAuthentication(username, password);
        return ResponseEntity.ok(traineeService.getTraineeTrainings(username, periodFrom, periodTo, trainerName, trainingType));
    }

    @Operation(summary = "Update trainee details")
            @ApiResponse(responseCode = "200", description = "Trainee updated successfully",
                    content = @Content(schema = @Schema(implementation = TraineeResponseDTO.class)))
            @ApiResponse(responseCode = "404", description = "Trainee not found", content = @Content)
    @PutMapping(value = "/update/{username}", produces = "application/json")
    public ResponseEntity<TraineeResponseDTO> update(
            @PathVariable(value = "username") @NotBlank(message = "Username is required") String username,
            @RequestParam(value = "firstname") @NotBlank(message = "Firstname is required") String firstname,
            @RequestParam(value = "lastname") @NotBlank(message = "Lastname is required") String lastname,
            @RequestParam(value = "dateOfBirth", required = false) String dateOfBirth,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "isActive", required = false) Boolean isActive,
            @RequestHeader("password") String password) {
        authentication.checkAuthentication(username, password);
        return ResponseEntity.ok(traineeService.updateTrainee(username, firstname, lastname, dateOfBirth, address, isActive));
    }

    @Operation(summary = "Delete a trainee by username")
            @ApiResponse(responseCode = "200", description = "Trainee deleted successfully")
            @ApiResponse(responseCode = "404", description = "Trainee not found", content = @Content)
    @DeleteMapping(value = "/delete/{username}", produces = "application/json")
    public ResponseEntity<Void> delete(
            @PathVariable("username") @NotBlank(message = "Username is required") String username,
            @RequestHeader("password") String password) {
        authentication.checkAuthentication(username, password);
        traineeService.deleteTrainee(username);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Update active status of a trainee")
            @ApiResponse(responseCode = "200", description = "Status updated successfully")
            @ApiResponse(responseCode = "400", description = "Invalid status update request", content = @Content)
            @ApiResponse(responseCode = "404", description = "Trainee not found", content = @Content)
    @PatchMapping(value = "/status/{username}")
    public ResponseEntity<Void> updateTraineeStatus(
            @PathVariable(value = "username") @NotBlank(message = "Username is required") String username,
            @RequestParam(value = "isActive") @NotNull(message = "isActive status must be provided") Boolean isActive,
            @RequestHeader("password") String password) {
        authentication.checkAuthentication(username, password);
        traineeService.updateTraineeStatus(username, isActive);
        return ResponseEntity.ok().build();
    }
}
