package epam.trainee.repository;

import epam.trainee.entity.Trainee;
import epam.training.entity.Training;

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

    Optional<List<Training>> getTraineeTrainings(String username, String periodFrom, String periodTo, String trainerName, String trainingType);
}
