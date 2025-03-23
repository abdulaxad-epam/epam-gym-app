package epam.trainee.repository;

import epam.trainee.entity.Trainee;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface TraineeRepository {
    Trainee insert(Trainee trainee);

    Optional<Trainee> findById(UUID id);

    Optional<Trainee> findByUsername(String username);

    List<Trainee> findAll();

    boolean existsById(UUID id);

    boolean existsByUsername(String username);

    Optional<UUID> getIdByUsername(String username);

    void deleteTraineeByUsername(String username);

    List<Trainee> findTraineeByTrainer(String currentUsername);
}
