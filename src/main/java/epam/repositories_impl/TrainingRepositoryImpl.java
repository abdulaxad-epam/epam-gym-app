package epam.repositories_impl;


import epam.entity.Training;
import epam.exception.TrainingNotFoundException;
import epam.repositories.TrainingRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TrainingRepositoryImpl implements TrainingRepository {

    private final EntityManager entityManager;

    @Override
    @Transactional
    public Training insert(UUID id, Training training) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(training);
            entityManager.getTransaction().commit();
            return training;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException("Failed to insert training: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public Training update(UUID id, Training training) {
        try {
            entityManager.getTransaction().begin();
            Training existingTraining = entityManager.find(Training.class, id);
            if (existingTraining == null) {
                throw new TrainingNotFoundException("Training with ID " + id + " not found.");
            }
            Training updatedTraining = entityManager.merge(training);
            entityManager.getTransaction().commit();
            return updatedTraining;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException("Failed to update training: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        try {
            entityManager.getTransaction().begin();
            Training training = entityManager.find(Training.class, id);
            if (training != null) {
                entityManager.remove(training);
            } else {
                throw new TrainingNotFoundException("Training with ID " + id + " not found.");
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException("Failed to delete training: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Training findById(UUID id) {
        return entityManager.find(Training.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Training> findAll() {
        return entityManager.createQuery("SELECT t FROM Training t", Training.class)
                .getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(UUID id) {
        return entityManager.createQuery(
                        """
                                SELECT CASE WHEN EXISTS
                                (SELECT 1 FROM Training t WHERE t.trainingId = :id)
                                THEN TRUE
                                ELSE
                                FALSE END
                                """,
                        Boolean.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
