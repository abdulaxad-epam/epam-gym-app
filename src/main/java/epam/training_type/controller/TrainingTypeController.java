package epam.training_type.controller;


import epam.shared.security.aop.Authenticated;
import epam.training_type.dto.TrainingTypeResponseDTO;
import epam.training_type.service.TrainingTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/training-types")
public class TrainingTypeController {
    private final TrainingTypeService trainingTypeService;

    @Authenticated
    @GetMapping(value = {"","/"}, produces = "application/json")
    public ResponseEntity<List<TrainingTypeResponseDTO>> getTrainingTypes() {
        log.info("Fetch all trainings of trainee by username");
        return ResponseEntity.ok(trainingTypeService.findAll());
    }
}
