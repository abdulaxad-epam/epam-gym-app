package epam.repository_impl;


import epam.entity.Trainee;
import epam.entity.Trainer;
import epam.entity.User;
import epam.exception.EntityManagerInsertException;
import epam.exception.TraineeNotFoundException;
import epam.repository.TraineeRepository;
import epam.repository.TrainingUserRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TraineeRepositoryImpl implements TrainingUserRepository, TraineeRepository {

    private final EntityManager entityManager;

    private final Log log = LogFactory.getLog(TraineeRepositoryImpl.class);

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public Trainee insert(Trainee trainee) {
        try {
            entityManager.getTransaction().begin();

            User user = updateUsername(trainee.getUser());
            String username = user.getUsername();
            long serialUsername = serialUsernames(username);
            if (serialUsername == 0L)
                trainee.getUser().setUsername(username);
            else
                trainee.getUser().setUsername(username + serialUsername);

            entityManager.persist(trainee);

            entityManager.getTransaction().commit();
            log.info("Trainee '" + trainee.getTraineeId() + "' inserted with final username '" + trainee.getUser().getUsername() + "'");
            return trainee;

        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Error inserting user: " + e.getMessage());
            throw new EntityManagerInsertException(e.getMessage());
        }
    }

    @Override
    public Trainee update(UUID id, Trainee trainee) {
        try {
            entityManager.getTransaction().begin();

            if (entityManager.find(Trainee.class, id) != null) {
                trainee = entityManager.merge(trainee);

                entityManager.flush();

                entityManager.refresh(trainee);

                entityManager.getTransaction().commit();
            } else
                throw new TraineeNotFoundException("Trainee with id " + id + " not found");

        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error(e.getMessage());
        }
        return trainee;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Trainee> findById(UUID id) {
        Trainee trainee = null;
        try {
            trainee = entityManager.find(Trainee.class, id);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return Optional.ofNullable(trainee);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Trainee> findByUsername(String username) {
        Trainee trainee = null;
        try {
            trainee = entityManager.createQuery("""
                    SELECT t FROM Trainee t WHERE t.user.username = :username
                    """, Trainee.class).getSingleResult();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return Optional.ofNullable(trainee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Trainee> findAll() {
        return entityManager
                .createQuery("from Trainee", Trainee.class)
                .getResultList();
    }

    @Override
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
    public Optional<UUID> getIdByUsername(String username) {
        UUID singleResult = entityManager.createQuery("""
                SELECT id FROM Trainee t WHERE t.user.username = :username
                """, UUID.class).getSingleResult();
        return Optional.of(singleResult);
    }

    @Override
    public void deleteTraineeByUsername(String username) {
        try {
            entityManager.getTransaction().begin();

            entityManager.createQuery("DELETE FROM Trainee t WHERE t.user.username = :username ")
                    .setParameter("username", username)
                    .executeUpdate();

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public List<Trainee> findTraineeByTrainer(String currentUsername) {
        return entityManager.createQuery("""
                        SELECT DISTINCT t FROM Trainee t
                        JOIN TraineeTrainer tt ON t.traineeId = tt.trainee.traineeId
                        JOIN Trainee tr ON tt.trainer.trainerId = tr.traineeId
                        JOIN User u ON tr.user.userId = u.userId
                        WHERE u.username = :username
                        """, Trainee.class)
                .setParameter("username", currentUsername)
                .getResultList();
    }

}
