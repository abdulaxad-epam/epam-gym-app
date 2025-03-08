package epam.service;

import epam.entity.TrainingType;

import java.util.List;

public interface TrainingTypeService {
    TrainingType getTrainingByTrainingName(String trainingName);

    List<String> findAll();
}
