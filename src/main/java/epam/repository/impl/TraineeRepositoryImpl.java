package epam.repository.impl;


import epam.exception.exception.EntityManagerInsertException;
import epam.entity.Trainee;
import epam.repository.TraineeRepository;
import epam.entity.Training;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TraineeRepositoryImpl extends AbstractTrainingRepository implements TraineeRepository {

    private final EntityManager entityManager;

    private final Log log = LogFactory.getLog(TraineeRepositoryImpl.class);

    @Override
    public EntityManager entityManager() {
        return entityManager;
    }

    @Override
    public Trainee insert(Trainee trainee) {
        try {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }

            entityManager.persist(trainee);
            entityManager.getTransaction().commit();

            log.info("Trainee '" + trainee.getTraineeId() + "' inserted with username '" +
                    trainee.getUser().getUsername() + " and password " + trainee.getUser().getPassword() + "'");
            return trainee;

        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Error inserting user: " + e.getMessage());
            throw new EntityManagerInsertException(e.getMessage());
        }
    }


    @Override
    @Transactional
    public Optional<Trainee> findByUsername(String username) {
        List<Trainee> traineeList = entityManager.createQuery("""
                        SELECT t FROM Trainee t WHERE user.username = :username
                        """, Trainee.class)
                .setParameter("username", username)
                .getResultList();

        if (traineeList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(traineeList.get(0));
    }


    @Override
    @Transactional(readOnly = true)
    public boolean existsById(UUID id) {
        return entityManager.createQuery(
                        """
                                SELECT CASE WHEN EXISTS
                                (SELECT 1 FROM Trainee t WHERE t.traineeId = :id)
                                THEN TRUE ELSE
                                FALSE END
                                """,
                        Boolean.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return entityManager.createQuery(
                        """
                                SELECT CASE WHEN EXISTS
                                (SELECT 1 FROM Trainee t WHERE t.user.username = :username)
                                THEN TRUE ELSE
                                FALSE END
                                """,
                        Boolean.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    @Override
    public void deleteTraineeByUsername(String username) {
        try {
            log.info("User " + username + " is deleted");

            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            Trainee trainee = entityManager.createQuery(
                            "SELECT t FROM Trainee t WHERE t.user.username = :username", Trainee.class)
                    .setParameter("username", username)
                    .getSingleResult();

            entityManager.remove(trainee);

            log.info("Trainee '" + trainee.getTraineeId() + "' deleted");

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            log.error(e.getMessage());
            entityManager.getTransaction().rollback();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<Training>> getTraineeTrainings(String username, String periodFrom, String periodTo,
                                                        String trainerName, String trainingType) {
        Map<String, Object> filters = new HashMap<>();
        if (trainerName != null && !trainerName.isEmpty()) {
            filters.put("t.trainer.user.username", trainerName);
        }
        if (trainingType != null && !trainingType.isEmpty()) {
            filters.put("t.trainingType.description", trainingType);
        }
        return getTrainings(username, "trainee", periodFrom, periodTo, filters);
    }

}
