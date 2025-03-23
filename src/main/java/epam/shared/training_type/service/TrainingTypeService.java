package epam.shared.training_type.service;

import epam.shared.training_type.entity.TrainingType;

import java.util.List;

public interface TrainingTypeService {
    TrainingType getTrainingByTrainingName(String trainingName);

    List<String> findAll();
}
