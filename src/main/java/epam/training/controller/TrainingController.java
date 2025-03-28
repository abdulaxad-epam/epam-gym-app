package epam.training.controller;


import epam.shared.security.aop.Authenticated;
import epam.training.dto.TrainingRequestDTO;
import epam.training.dto.TrainingResponseDTO;
import epam.training.service.TrainingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/trainings")
public class TrainingController {

    private final TrainingService trainingService;

    @Authenticated
    @PostMapping(value = {"", "/"})
    public ResponseEntity<TrainingResponseDTO> createTraining(@RequestBody TrainingRequestDTO trainingRequestDTO) {
        log.info("Create new training: {}", trainingRequestDTO);
        return ResponseEntity.ok(trainingService.createTraining(trainingRequestDTO));
    }


}
