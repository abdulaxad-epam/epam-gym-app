package epam.controller;


import epam.aop.Authenticated;
import epam.dto.request_dto.TrainingRequestDTO;
import epam.dto.response_dto.TrainingResponseDTO;
import epam.service.TrainingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/trainings")
public class TrainingController {

    private final TrainingService trainingService;

    @Authenticated
    @PostMapping(value = {"", "/"})
    public ResponseEntity<TrainingResponseDTO> createTraining(@Valid @RequestBody TrainingRequestDTO trainingRequestDTO) {
        return ResponseEntity.ok(trainingService.createTraining(trainingRequestDTO));
    }


}
