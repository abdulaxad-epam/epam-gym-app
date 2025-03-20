package epam.repository;

import epam.entity.TrainingType;

import java.util.List;

public interface TrainingTypeRepository {
    TrainingType findTrainingByTrainingName(String trainingName);

    List<String> findAll();
}
