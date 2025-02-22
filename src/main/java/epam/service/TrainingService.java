package epam.service;

import epam.domain.Training;

import java.util.List;
import java.util.UUID;

public interface TrainingService {
    Training createTraining(UUID id, Training training);

    Training getTrainingById(UUID id);

    List<Training> getAllTrainings();
}
