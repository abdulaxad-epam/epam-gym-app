package epam.repository_impl;


import epam.entity.TrainingType;
import epam.repository.TrainingTypeRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TrainingTypeRepositoryImpl implements TrainingTypeRepository {

    private final EntityManager entityManager;

    @Override
    public TrainingType findTrainingByTrainingName(String trainingName) {
        return entityManager.find(TrainingType.class, trainingName);
    }

    @Override
    public List<String> findAll() {
        return entityManager.createQuery("SELECT t.description FROM TrainingType t", String.class).getResultList();
    }

}
