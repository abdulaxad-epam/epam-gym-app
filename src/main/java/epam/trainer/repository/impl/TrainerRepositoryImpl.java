package epam.trainer.repository.impl;


import epam.shared.exception.exception.EntityManagerInsertException;
import epam.trainer.entity.Trainer;
import epam.trainer.repository.TrainerRepository;
import epam.training.entity.Training;
import epam.training.repository.AbstractTrainingRepository;
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

@Repository
@RequiredArgsConstructor
public class TrainerRepositoryImpl extends AbstractTrainingRepository implements TrainerRepository {

    private final Log log = LogFactory.getLog(TrainerRepositoryImpl.class);

    private final EntityManager entityManager;


    @Override
    public EntityManager entityManager(){
        return entityManager;
    }

    @Override
    public Trainer insert(Trainer trainer) {
        if (trainer == null)
            throw new IllegalArgumentException("Trainer cannot be null");

        try {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            entityManager.persist(trainer);

            entityManager.getTransaction().commit();
            log.info("Trainer '" + trainer.getTrainerId() + "' inserted with final username '"
                    + trainer.getUser().getUsername() + " and password " + trainer.getUser().getPassword() + "'");
            return trainer;

        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Error inserting user: " + e.getMessage());
            throw new EntityManagerInsertException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Optional<Trainer> findByUsername(String username) {
        try {
            Trainer trainers = entityManager.createQuery("""
                            SELECT t FROM Trainer t WHERE t.user.username = :username
                            """, Trainer.class)
                    .setParameter("username", username)

                    .getSingleResult();

            return Optional.of(trainers);
        } catch (Exception e) {
            log.error("Error finding trainer by username: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public void deleteTrainerByUsername(String username) {
        try {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            entityManager.createQuery("DELETE FROM Trainer t WHERE t.user.username = :username ")
                    .setParameter("username", username)
                    .executeUpdate();

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


    @Override
    public Optional<List<Training>> getTrainerTrainings(String username, String periodFrom, String periodTo, String traineeName) {
        Map<String, Object> filters = new HashMap<>();
        if (traineeName != null && !traineeName.isEmpty()) {
            filters.put("t.trainee.user.username", traineeName);
        }
        return getTrainings(username, "trainer", periodFrom, periodTo, filters);
    }
}
