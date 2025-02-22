package epam.dao;


import epam.domain.Training;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TrainingDAO {

    private final Map<UUID, Training> inMemoryTraining;

    public TrainingDAO(Map<UUID, Training> inMemoryTraining) {
        this.inMemoryTraining = inMemoryTraining;
    }

    public Training insert(UUID id, Training trainer) {
        inMemoryTraining.put(id, trainer);
        return trainer;
    }

    public Optional<Training> findById(UUID id) {
        return Optional.ofNullable(inMemoryTraining.get(id));
    }

    public Map<UUID, Training> findAll() {
        return new HashMap<>(inMemoryTraining);
    }
}
