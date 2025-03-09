package epam.repository.repository_impl;


import epam.entity.Trainee;
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

            trainee.setUser(user);

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
    public List<Trainee> findAll() {
        return entityManager
                .createQuery("from Trainee", Trainee.class)
                .getResultList();
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
    @Transactional(readOnly = true)
    public Optional<UUID> getIdByUsername(String username) {
        UUID singleResult = entityManager.createQuery("""
                        SELECT id FROM Trainee t WHERE t.user.username = :username
                        """, UUID.class)
                .setParameter("username", username)
                .getSingleResult();
        return Optional.of(singleResult);
    }

    @Override
    public void deleteTraineeByUsername(String username) {
        try {
            log.info("User " + username + " is deleted");

            entityManager.getTransaction().begin();

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
