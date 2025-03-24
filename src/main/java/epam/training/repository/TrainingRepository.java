package epam.training.repository;

import epam.training.entity.Training;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TrainingRepository {
    Training insert(Training trainer);
    void delete(UUID id);
    Training findById(UUID id);
    List<Training> findAll();

    boolean existsById(UUID id);

    List<Training> findTrainingsByTrainee(String username);

    List<Training> getByCriteria(String username, LocalDate fromDate, LocalDate toDate, String trainerName, String trainingType);

    @Transactional(readOnly = true)
    Optional<UUID> getIdByUsername(String username);

    Optional<Training> findByUser_Username(String username);
}
