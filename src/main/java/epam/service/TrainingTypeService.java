package epam.service;

import epam.entity.Training;
import epam.entity.TrainingType;

public interface TrainingTypeService {
    TrainingType getTrainingByTrainingName(String trainingName);
}
