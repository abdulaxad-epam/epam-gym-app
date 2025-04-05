package epam.repository;

import epam.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface TrainingRepository extends JpaRepository<Training, UUID> {

    void removeTrainingByTrainingId(UUID trainingId);

    @Query("""
            SELECT CASE WHEN EXISTS ( FROM Training t WHERE t.trainee.user.username = :username)
               THEN (SELECT trainingId FROM Training t WHERE t.trainee.user.username = :username)
               ELSE NULL END
            """)
    Optional<UUID> getIdByUsername(String username);
}
