package epam.repositories;

import epam.entity.Trainee;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface TraineeRepository {
    Trainee insert(UUID id, Trainee trainee);

    Trainee update(UUID id, Trainee trainee);

    void delete(UUID id);

    Optional<Trainee> findById(UUID id);

    Optional<Trainee> findByUsername(String username);


    List<Trainee> findAll();

    boolean existsById(UUID id);
}
