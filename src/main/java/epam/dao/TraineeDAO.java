package epam.dao;


import epam.domain.Trainee;
import epam.exception.TraineeNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TraineeDAO implements Train<Trainee>{

    private final Map<UUID, Trainee> inMemoryTrainee;

    public TraineeDAO(Map<UUID, Trainee> inMemoryTrainee) {
        this.inMemoryTrainee = inMemoryTrainee;
    }

    @Override
    public Map<UUID, Trainee> getInMemoryStorage() {
        return inMemoryTrainee;
    }

    public Trainee update(UUID id, Trainee trainee) {
        if (inMemoryTrainee.containsKey(id)) {
            inMemoryTrainee.replace(id, trainee);
            return trainee;
        }
        throw new TraineeNotFoundException(String.format("Trainee with id %s not found", id));
    }

    public void delete(UUID id) {
        inMemoryTrainee.remove(id);
    }

    public Optional<Trainee> findById(UUID id) {
        return Optional.ofNullable(inMemoryTrainee.get(id));
    }

    public Map<UUID, Trainee> findAll() {
        return new HashMap<>(inMemoryTrainee);
    }

}
