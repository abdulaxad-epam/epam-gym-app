package epam.facade;


import epam.service.TraineeService;
import epam.service.TrainerService;
import epam.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainingFacade {

    private final TraineeService traineeService;
    private final TrainingService trainingService;
    private final TrainerService trainerService;

}
