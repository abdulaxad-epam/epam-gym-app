package epam.repository_impl;

import epam.entity.Training;
import epam.entity.TrainingType;
import epam.repository.TrainingTypeRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TrainingTypeRepositoryImpl implements TrainingTypeRepository {

    private final EntityManager entityManager;

    public TrainingType findTrainingByTrainingName(String trainingName) {
        return entityManager.find(TrainingType.class, trainingName);
    }
}
