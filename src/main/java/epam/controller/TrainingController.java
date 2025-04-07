package epam.controller;

import epam.service.impl.Authentication;
import epam.dto.request_dto.TrainingRequestDTO;
import epam.dto.response_dto.TrainingResponseDTO;
import epam.service.TrainingService;
import jakarta.validation.Valid;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/trainings")
@Tag(name = "Training", description = "Operations related to training sessions")
public class TrainingController {

    private final TrainingService trainingService;

    private final Authentication authentication;

    @Operation(summary = "Create a new training session")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Training created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TrainingResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid training data", content = @Content)
    })
    @PostMapping(value = {"", "/"})
    public ResponseEntity<TrainingResponseDTO> createTraining(
            @Parameter(description = "Training creation request body", required = true)
            @Valid @RequestBody TrainingRequestDTO trainingRequestDTO,
            @RequestHeader("username") String username,
            @RequestHeader("password") String password) {
        authentication.checkAuthentication(username, password);
        return ResponseEntity.ok(trainingService.createTraining(trainingRequestDTO));
    }
}
