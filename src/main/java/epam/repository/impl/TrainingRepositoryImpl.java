package epam.repository.impl;

import epam.exception.exception.TrainingNotFoundException;
import epam.entity.Training;
import epam.repository.TrainingRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TrainingRepositoryImpl implements TrainingRepository {

    private static final Log log = LogFactory.getLog(TrainingRepositoryImpl.class);
    private final EntityManager entityManager;

    @Override
    public Training insert(Training training) {
        try {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            training.setTrainee(entityManager.merge(training.getTrainee()));

            System.out.println(training.getTrainee());
            training.setTrainer(entityManager.merge(training.getTrainer()));

            System.out.println(training.getTrainer());
            training.setTrainingType(entityManager.merge(training.getTrainingType()));

            entityManager.persist(training);
            entityManager.getTransaction().commit();

            return training;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException("Failed to insert training: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(UUID id) {
        try {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            Training training = entityManager.find(Training.class, id);
            log.info("Deleting training with ID " + id);

            if (training != null) {
                entityManager.remove(training);
                log.info("Deleted training with ID " + id);

            } else {
                log.info("Training with ID " + id + " not found.");
                throw new TrainingNotFoundException("Training with ID " + id + " not found.");
            }

            entityManager.getTransaction().commit();

        }catch (TrainingNotFoundException exception){
            entityManager.getTransaction().rollback();
            throw new TrainingNotFoundException("Training not found");
        }catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException("Failed to delete training: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UUID> getIdByUsername(String username) {

        log.info("getIdByUsername: " + username);

        UUID singleResult = entityManager.createQuery("""
                SELECT CASE WHEN EXISTS ( FROM Training t WHERE t.trainee.user.username = :username)
                THEN (SELECT trainingId FROM Training t WHERE t.trainee.user.username = :username)
                ELSE NULL END
                """, UUID.class)
                .setParameter("username", username)
                .getSingleResult();

        log.info("getIdByUsername: " + singleResult);
        return Optional.ofNullable(singleResult);
    }

}