package epam.repository.impl;


import epam.entity.Trainee;
import epam.entity.Trainer;
import epam.entity.User;
import epam.exception.EntityManagerInsertException;
import epam.exception.TrainerNotFoundException;
import epam.repository.TrainerRepository;
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
public class TrainerRepositoryImpl implements TrainingUserRepository, TrainerRepository {

    private final Log log = LogFactory.getLog(TrainerRepositoryImpl.class);

    private final EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public Trainer insert(Trainer trainer) {
        if (trainer == null)
            throw new IllegalArgumentException("Trainer cannot be null");

        try {
            entityManager.getTransaction().begin();

            User user = updateUsername(trainer.getUser());

            trainer.setUser(user);

            entityManager.persist(trainer);

            entityManager.getTransaction().commit();
            log.info("Trainer '" + trainer.getTrainerId() + "' inserted with final username '"
                    + user.getUsername() + " and password " + user.getPassword() + "'");
            return trainer;

        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Error inserting user: " + e.getMessage());
            throw new EntityManagerInsertException(e.getMessage());
        }
    }


    @Override
    public Trainer update(UUID id, Trainer trainer) {
        if (trainer == null)
            throw new IllegalArgumentException("Trainer cannot be null");

        try {
            entityManager.getTransaction().begin();

            if (entityManager.find(Trainer.class, id) != null) {

                trainer = entityManager.merge(trainer);

                entityManager.flush();

                entityManager.refresh(trainer);

                entityManager.getTransaction().commit();

            } else
                throw new TrainerNotFoundException("Trainer with id " + id + " not found");

        } catch (TrainerNotFoundException e) {
            entityManager.getTransaction().rollback();
            throw e;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error(e.getMessage());
            throw new TrainerNotFoundException(e.getMessage());
        }
        return trainer;
    }

    @Override
    public Optional<Trainer> findByUsername(String username) {
        try {
            List<Trainer> trainers = entityManager.createQuery("""
                            SELECT t FROM Trainer t WHERE t.user.username = :username
                            """, Trainer.class)
                    .setParameter("username", username)
                    .getResultList();

            return trainers.isEmpty() ? Optional.empty() : Optional.of(trainers.get(0));
        } catch (Exception e) {
            log.error("Error finding trainer by username: " + e.getMessage(), e);
        }
        return Optional.empty();
    }


    @Transactional(readOnly = true)
    @Override
    public Optional<Trainer> findById(UUID id) {
        Trainer trainer = null;
        try {
            trainer = entityManager.find(Trainer.class, id);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return Optional.ofNullable(trainer);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Trainer> findAll() {
        return entityManager.createQuery("from Trainer", Trainer.class).getResultList();
    }

    @Override
    public boolean existsById(UUID id) {
        return entityManager.createQuery(
                        """
                                SELECT CASE WHEN EXISTS
                                (SELECT 1 FROM Trainer t WHERE t.trainerId = :id)
                                THEN TRUE
                                ELSE
                                FALSE END
                                """,
                        Boolean.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public Optional<UUID> getIdByUsername(String username) {
        UUID singleResult = entityManager.createQuery("""
                        SELECT id FROM Trainer t WHERE t.user.username = :username
                        """, UUID.class)
                .setParameter("username", username)
                .getSingleResult();
        return Optional.of(singleResult);
    }

    @Override
    public void deleteTrainerByUsername(String username) {
        try {
            entityManager.getTransaction().begin();

            entityManager.createQuery("DELETE FROM Trainer t WHERE t.user.username = :username ")
                    .setParameter("username", username)
                    .executeUpdate();

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public List<Trainer> findTrainersByTrainee(String currentUsername) {
        return entityManager.createQuery(
                        """
                                SELECT DISTINCT t FROM Trainer t
                                JOIN TraineeTrainer tt ON t.trainerId = tt.trainer.trainerId
                                JOIN Trainee tr ON tt.trainee.traineeId = tr.traineeId
                                JOIN User u ON tr.user.userId = u.userId
                                WHERE u.username = :username
                                """, Trainer.class)
                .setParameter("username", currentUsername)
                .getResultList();
    }

    @Override
    public void addTrainerToTrainee(Trainee trainee, Trainer trainer) {
        entityManager.createQuery("""
                        INSERT INTO TraineeTrainer (trainee, trainer) VALUES (:trainer, :trainee)
                        """)
                .setParameter("trainee", trainee)
                .setParameter("trainer", trainer);
    }

    @Override
    public Boolean trainerHasTrainee(UUID trainerId, UUID traineeId) {
        return entityManager.createQuery("""
                        SELECT CASE WHEN EXISTS
                        (
                            SELECT 1 FROM TraineeTrainer t
                            WHERE t.trainer.trainerId = :trainerId
                            and t.trainee.traineeId = :traineeId
                            )
                        THEN TRUE ELSE FALSE END
                        """, Boolean.class)
                .setParameter("trainerId", trainerId)
                .setParameter("traineeId", traineeId)
                .getSingleResult();
    }

    @Override
    public void removeTraineeOfTrainer(Trainee trainee, Trainer trainer) {
        entityManager.createQuery("""
                        DELETE FROM TraineeTrainer t WHERE
                        t.trainer.trainerId = :trainerId
                        AND t.trainee.traineeId = :traineeId
                        """)
                .setParameter("trainerId", trainer.getTrainerId())
                .setParameter("traineeId", trainee.getTraineeId())
                .executeUpdate();
    }
}
