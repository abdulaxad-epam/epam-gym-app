package epam.controller;


import epam.aop.Authenticated;
import epam.dto.response_dto.TrainingTypeResponseDTO;
import epam.service.TrainingTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/training-types")
public class TrainingTypeController {
    private final TrainingTypeService trainingTypeService;

    @Authenticated
    @GetMapping(value = {"","/"}, produces = "application/json")
    public ResponseEntity<List<TrainingTypeResponseDTO>> getTrainingTypes() {
        return ResponseEntity.ok(trainingTypeService.findAll());
    }
}
