package epam.controller;

import epam.client.TrainingServiceClient;
import epam.client.dto.TrainingRequestDTO;
import epam.client.dto.TrainingResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/trainings")
@Tag(name = "Training", description = "Operations related to training sessions")
public class TrainingController {

    private final TrainingServiceClient trainingRestClient;

    @Operation(summary = "Create a new training session")
    @ApiResponse(responseCode = "200", description = "Training created successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TrainingResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Invalid training data", content = @Content)
    @PostMapping
    public ResponseEntity<TrainingResponseDTO> createTraining(
            @Parameter(description = "Training creation request body", required = true)
            @Valid @RequestBody TrainingRequestDTO trainingRequestDTO, Authentication authentication) {
        return ResponseEntity.ok(trainingRestClient.createTraining(trainingRequestDTO, authentication));
    }

    @DeleteMapping("/remove/{trainingId}")
    public ResponseEntity<String> deleteTraining(@PathVariable UUID trainingId, Authentication authentication) {
        return ResponseEntity.ok(trainingRestClient.deleteTraining(trainingId, authentication));
    }
}
