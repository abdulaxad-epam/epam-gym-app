package epam.repository;

import epam.entity.TrainingType;

public interface TrainingTypeRepository {
    TrainingType findTrainingByTrainingName(String trainingName);
}
