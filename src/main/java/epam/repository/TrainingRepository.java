package epam.repository;

import epam.entity.Training;

import java.util.Optional;
import java.util.UUID;

public interface TrainingRepository {

    Training insert(Training trainer);

    void delete(UUID id);

    Optional<UUID> getIdByUsername(String username);
}
