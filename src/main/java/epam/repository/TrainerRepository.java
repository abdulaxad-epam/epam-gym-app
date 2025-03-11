package epam.repository;

import epam.entity.Trainee;
import epam.entity.TraineeTrainer;
import epam.entity.Trainer;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TrainerRepository {
    Trainer insert(Trainer trainer);

    Trainer update(UUID id, Trainer trainer);

    Optional<Trainer> findByUsername(String username);

    @Transactional(readOnly = true)
    Optional<Trainer> findById(UUID id);

    @Transactional(readOnly = true)
    List<Trainer> findAll();

    boolean existsById(UUID id);

    Optional<UUID> getIdByUsername(String username);

    void deleteTrainerByUsername(String username);

    List<Trainer> findTrainersByTrainee(String currentUsername);

    void addTrainerToTrainee(TraineeTrainer trainer);

    Boolean trainerHasTrainee(UUID trainerId, UUID traineeId);

    void removeTraineeOfTrainer(Trainee trainee, Trainer trainer);
}
