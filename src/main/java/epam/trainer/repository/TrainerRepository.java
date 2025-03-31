package epam.trainer.repository;

import epam.trainer.entity.Trainer;
import epam.training.entity.Training;

import java.util.List;
import java.util.Optional;

public interface TrainerRepository {
    Trainer insert(Trainer trainer);

    Optional<Trainer> findByUsername(String username);

    void deleteTrainerByUsername(String username);

    Optional<List<Training>> getTrainerTrainings(String username, String periodFrom, String periodTo, String traineeName);
}
