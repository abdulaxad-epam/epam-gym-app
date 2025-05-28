package epam.repository;

import epam.entity.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface TraineeRepository extends JpaRepository<Trainee, UUID> {

    Optional<Trainee> findTraineeByUser_Username(String username);

    boolean existsTraineeByUser_Username(String username);

    @Modifying
    void deleteTraineeByUser_Username(String username);

    Optional<Trainee> findTraineeByUser_username(String userUsername);

    Optional<Trainee> findTraineeByTraineeId(UUID traineeId);
}
