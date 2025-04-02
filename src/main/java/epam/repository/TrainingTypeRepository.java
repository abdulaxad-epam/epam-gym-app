package epam.repository;

import epam.entity.TrainingType;

import java.util.List;
import java.util.Optional;

public interface TrainingTypeRepository {
    Optional<TrainingType> findTrainingByTrainingName(String trainingName);

    List<TrainingType> findAll();
}
