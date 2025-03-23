package epam.training.repository;

import epam.training.entity.Training;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TrainingRepository {
    Training insert(Training trainer);
    Training update(UUID id, Training trainer);
    void delete(UUID id);
    Training findById(UUID id);
    List<Training> findAll();

    boolean existsById(UUID id);

    Optional<UUID> getIdByUsername(String username);

    List<Training> findTrainingsByTrainee(String username);

    List<Training> getByCriteria(String username, LocalDate fromDate, LocalDate toDate, String trainerName, String trainingType);
}
