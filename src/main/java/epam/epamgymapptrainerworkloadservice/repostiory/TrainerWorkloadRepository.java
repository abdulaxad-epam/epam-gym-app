package epam.epamgymapptrainerworkloadservice.repostiory;

import epam.epamgymapptrainerworkloadservice.entity.TrainerWorkload;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TrainerWorkloadRepository extends JpaRepository<TrainerWorkload, Long> {
    List<TrainerWorkload> findTrainerWorkloadsByTrainerUsername(String trainerUsername);

    List<TrainerWorkload> findTrainerWorkloadsByTrainerUsernameAndTrainingDateBetween(String trainerUsername, LocalDate trainingDateAfter, LocalDate trainingDateBefore);

    Optional<TrainerWorkload> findTrainerWorkloadByTrainerUsernameAndTrainingDate(String trainerUsername, LocalDate trainingDate);
}

