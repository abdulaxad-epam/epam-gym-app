package epam.repositories_impl;


import epam.entity.Trainee;
import epam.entity.User;
import epam.exception.EntityManagerInsertException;
import epam.exception.TraineeNotFoundException;
import epam.repositories.TraineeRepository;
import epam.repositories.TrainingUserRepository;
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
    public Trainee insert(UUID id, Trainee trainee) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();

            User user = updateUsername(trainee.getUser());
            String username = user.getUsername();
            long serialUsername = serialUsernames(username);
            user.setUsername(username + serialUsername + 1);
            entityManager.persist(trainee);

            entityManager.persist(trainee);

            entityManager.getTransaction().commit();
            log.info("User '" + id + "' inserted with final username '" + username + "'");
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

    @Override
    public void delete(UUID id) {
        try {
            entityManager.getTransaction().begin();
            if (entityManager.find(Trainee.class, id) != null) {
                entityManager.remove(entityManager.find(Trainee.class, id));
            } else {
                throw new TraineeNotFoundException("Trainee with id " + id + " not found");
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            log.error(e.getMessage());
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

}
