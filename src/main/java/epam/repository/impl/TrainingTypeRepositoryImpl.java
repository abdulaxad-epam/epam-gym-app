package epam.repository.impl;


import epam.entity.TrainingType;
import epam.repository.TrainingTypeRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TrainingTypeRepositoryImpl implements TrainingTypeRepository {

    private final EntityManager entityManager;

    @Override
    public Optional<TrainingType> findTrainingByTrainingName(String trainingName) {
        List<TrainingType> results = entityManager.createQuery("""
                    SELECT t FROM TrainingType t WHERE t.description = :trainingName
                    """, TrainingType.class)
                .setParameter("trainingName", trainingName)
                .getResultList();

        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }


    @Override
    public List<TrainingType> findAll() {
        return entityManager.createQuery("SELECT t FROM TrainingType t", TrainingType.class).getResultList();
    }

}
