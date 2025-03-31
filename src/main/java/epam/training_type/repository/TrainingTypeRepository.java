package epam.training_type.repository;

import epam.training_type.entity.TrainingType;

import java.util.List;
import java.util.Optional;

public interface TrainingTypeRepository {
    Optional<TrainingType> findTrainingByTrainingName(String trainingName);

    List<TrainingType> findAll();
}
