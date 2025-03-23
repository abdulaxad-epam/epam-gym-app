package epam.shared.training_type.repository;

import epam.shared.training_type.entity.TrainingType;

import java.util.List;

public interface TrainingTypeRepository {
    TrainingType findTrainingByTrainingName(String trainingName);

    List<String> findAll();
}
