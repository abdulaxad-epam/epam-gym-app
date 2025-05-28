package epam.repository;

import epam.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TrainerRepository extends JpaRepository<Trainer, Integer> {

    Optional<Trainer> findTrainerByUser_Username(String username);


    @Modifying
    @Query(value = "delete from TrainerTrainee where trainer.trainerId = :trainerId")
    void deleteFromTrainerTrainee(UUID trainerId);

    @Modifying
    void deleteTrainerByTrainerId(UUID trainerId);

    Optional<Trainer> findTrainerByTrainerId(UUID trainerId);
}
