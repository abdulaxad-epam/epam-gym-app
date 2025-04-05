package epam.repository;

import epam.entity.Trainee;
import epam.entity.Trainer;
import epam.entity.TrainerTrainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TrainerTraineeRepository extends JpaRepository<TrainerTrainee, Integer> {

    @Query(value = """
            SELECT t FROM Trainer t
              WHERE t.user.username NOT IN(
              SELECT tt.trainer.user.username FROM TrainerTrainee tt
              WHERE tt.trainee.user.username = :username
            """, nativeQuery = true)
    List<Trainer> findByUsernameNotAssignedToTrainee(String username);

    void removeTraineeTrainerByTraineeAndTrainer(Trainee trainee, Trainer trainer);

    boolean existsById_TrainerIdAndId_TraineeId(UUID trainer, UUID trainee);


    void removeTrainerTraineeByTrainee_User_Username(String traineeUsername);
}
