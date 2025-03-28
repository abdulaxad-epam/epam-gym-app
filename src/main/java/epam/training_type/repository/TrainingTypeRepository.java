package epam.training_type.repository;

import epam.training_type.entity.TrainingType;

import java.util.List;

public interface TrainingTypeRepository {
    TrainingType findTrainingByTrainingName(String trainingName);

    List<TrainingType> findAll();
}
