package epam.repository;

import epam.entity.Trainee;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface TraineeRepository {
    Trainee insert(UUID id, Trainee trainee);

    Trainee update(UUID id, Trainee trainee);

    Optional<Trainee> findById(UUID id);

    Optional<Trainee> findByUsername(String username);


    List<Trainee> findAll();

    boolean existsById(UUID id);

    void deleteTraineeByUsername(String username);
}
