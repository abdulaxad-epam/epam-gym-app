package epam.repository.impl;

import epam.entity.Training;
import epam.exception.TrainingNotFoundException;
import epam.repository.TrainingRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
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
            entityManager.getTransaction().begin();

            training.setTrainee(entityManager.merge(training.getTrainee()));
            training.setTrainer(entityManager.merge(training.getTrainer()));
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

    @Override
    public List<Training> findTrainingsByTrainee(String username) {
        return entityManager.createQuery("""
                        SELECT DISTINCT t FROM Training t
                        JOIN FETCH t.trainee trainee
                        JOIN FETCH trainee.user user
                        WHERE user.username = :username
                        """, Training.class)
                .getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Training> getByCriteria(String username, LocalDate fromDate, LocalDate toDate, String trainerName, String trainingType) {
       return entityManager.createQuery( """
            SELECT DISTINCT t FROM Training t
            JOIN t.trainee trainee
            JOIN trainee.user user
            JOIN t.trainer trainer
            JOIN trainer.user trainerUser
            JOIN t.trainingType tt
            WHERE (:username IS NULL OR user.username = :username)
            AND (:fromDate IS NULL OR t.trainingDate >= :fromDate)
            AND (:toDate IS NULL OR t.trainingDate <= :toDate)
            AND (:trainerName IS NULL OR CONCAT(trainerUser.firstname, '.', trainerUser.lastname) = :trainerName)
            AND (:trainingType IS NULL OR tt.description = :trainingType)
            """, Training.class)
       .setParameter("username", username)
       .setParameter("fromDate", fromDate)
       .setParameter("toDate", toDate)
       .setParameter("trainerName", trainerName)
       .setParameter("trainingType", trainingType).getResultList();
    }

}