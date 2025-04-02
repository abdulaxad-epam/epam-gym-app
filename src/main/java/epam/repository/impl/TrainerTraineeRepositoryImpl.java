package epam.repository.impl;

import epam.entity.TrainerTrainee;
import epam.repository.TrainerTraineeRepository;
import epam.entity.Trainee;
import epam.entity.Trainer;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TrainerTraineeRepositoryImpl implements TrainerTraineeRepository {
    private final EntityManager entityManager;

    @Override
    public void assignTrainerToTrainee(TrainerTrainee trainerTrainee) {
        try {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }

            trainerTrainee.setTrainee(entityManager.merge(trainerTrainee.getTrainee()));
            trainerTrainee.setTrainer(entityManager.merge(trainerTrainee.getTrainer()));
            entityManager.merge(trainerTrainee);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public List<Trainer> findByUsernameNotAssignedToTrainee(String username) {
        return entityManager.createQuery("""
                        
                        SELECT t FROM Trainer t
                        WHERE t.user.username NOT IN (
                        SELECT tt.trainer.user.username FROM TrainerTrainee tt
                        WHERE tt.trainee.user.username = :username
                        )
                        """, Trainer.class)
                .setParameter("username", username)

                .getResultList();
    }

    @Override
    public void removeTraineeOfTrainer(Trainee trainee, Trainer trainer) {
        entityManager.createQuery("""
                        DELETE FROM TrainerTrainee t WHERE
                        t.trainer = :trainer
                        AND t.trainee = :trainee
                        """)
                .setParameter("trainer", trainer)
                .setParameter("trainee", trainee)
                .executeUpdate();
    }

    @Override
    public boolean trainerHasTrainee(UUID trainerId, UUID traineeId) {
        return entityManager.createQuery("""
                         SELECT CASE WHEN EXISTS
                           (SELECT 1 FROM TrainerTrainee tt WHERE tt.id.traineeId= :traineeId
                           AND tt.id.trainerId= :trainerId)
                           THEN TRUE
                           ELSE
                           FALSE END
                        """, Boolean.class)
                .setParameter("traineeId", traineeId)
                .setParameter("trainerId", trainerId)
                .getSingleResult();
    }

    @Override
    public List<TrainerTrainee> findByTrainee_User_username(String username) {
        return entityManager.createQuery("""
                        FROM TrainerTrainee t WHERE t.trainee.user.username = :username
                        """, TrainerTrainee.class)
                .setParameter("username", username)
                .getResultList();
    }

    @Override
    public void removeAllByTraineeUsername(String traineeUsername) {
        try {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            entityManager.createQuery("""
                            DELETE FROM TrainerTrainee t WHERE t.trainee.user.username = :username
                            """)
                    .setParameter("username", traineeUsername)
                    .executeUpdate();

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
