package epam.trainer.repository;

import epam.trainee.entity.Trainee;
import epam.trainer.entity.Trainer;
import epam.training.entity.Training;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TrainerRepository {
    Trainer insert(Trainer trainer);

    Optional<Trainer> findByUsername(String username);

    @Transactional(readOnly = true)
    Optional<Trainer> findById(UUID id);

    @Transactional(readOnly = true)
    List<Trainer> findAll();

    boolean existsById(UUID id);

    Optional<UUID> getIdByUsername(String username);

    void deleteTrainerByUsername(String username);

    Optional<List<Training>> getTrainerTrainings(String username, String periodFrom, String periodTo, String traineeName);
}
