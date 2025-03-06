package epam.repository_impl;


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
    public Trainer insert(UUID id, Trainer trainer) {
        try {
            entityManager.getTransaction().begin();

            User user = updateUsername(trainer.getUser());
            String username = user.getUsername();
            long serialUsername = serialUsernames(username);
            user.setUsername(username + serialUsername + 1);
            entityManager.persist(trainer);

            entityManager.getTransaction().commit();
            log.info("Trainer '" + id + "' inserted with final username '" + username + "'");
            return trainer;

        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Error inserting user: " + e.getMessage());
            throw new EntityManagerInsertException(e.getMessage());
        }
    }


    @Override
    public Trainer update(UUID id, Trainer trainer) {
        try {
            entityManager.getTransaction().begin();

            if (entityManager.find(Trainer.class, id) != null) {

                trainer = entityManager.merge(trainer);

                entityManager.flush();

                entityManager.refresh(trainer);

                entityManager.getTransaction().commit();

            } else
                throw new TrainerNotFoundException("Trainer with id " + id + " not found");

        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error(e.getMessage());
        }
        return trainer;
    }

    @Override
    public Optional<Trainer> findByUsername(String username) {
        Trainer trainer = null;
        try {
            trainer = entityManager.createQuery("""
                    SELECT t FROM Trainer t WHERE t.user.username = :username
                    """, Trainer.class).getSingleResult();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return Optional.ofNullable(trainer);
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

}
