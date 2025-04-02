package epam.repository;

import epam.entity.Trainee;
import epam.entity.Training;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface TraineeRepository {
    Trainee insert(Trainee trainee);

    Optional<Trainee> findByUsername(String username);

    boolean existsById(UUID id);

    boolean existsByUsername(String username);


    void deleteTraineeByUsername(String username);

    Optional<List<Training>> getTraineeTrainings(String username, String periodFrom, String periodTo, String trainerName, String trainingType);
}
