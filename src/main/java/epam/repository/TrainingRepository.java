package epam.repository;

import epam.entity.Training;

import java.util.List;
import java.util.UUID;

public interface TrainingRepository {
    Training insert(UUID id, Training trainer);
    Training update(UUID id, Training trainer);
    void delete(UUID id);
    Training findById(UUID id);
    List<Training> findAll();

    boolean existsById(UUID id);
}
