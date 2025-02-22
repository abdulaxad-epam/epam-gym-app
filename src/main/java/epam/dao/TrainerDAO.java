package epam.dao;


import epam.domain.Trainer;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TrainerDAO implements Train<Trainer> {

    private final Map<UUID, Trainer> inMemoryTrainer;

    @Override
    public Map<UUID, Trainer> getInMemoryStorage() {
        return inMemoryTrainer;
    }

    public TrainerDAO(Map<UUID, Trainer> inMemoryTrainer) {
        this.inMemoryTrainer = inMemoryTrainer;
    }

    public Trainer update(UUID id, Trainer trainer) {
        inMemoryTrainer.replace(id, trainer);
        return trainer;

    }

    public void delete(UUID id) {
        inMemoryTrainer.remove(id);
    }

    public Optional<Trainer> findById(UUID id) {
        return Optional.ofNullable(inMemoryTrainer.get(id));
    }

    public Map<UUID, Trainer> findAll() {

        return new HashMap<>(inMemoryTrainer);
    }

}
