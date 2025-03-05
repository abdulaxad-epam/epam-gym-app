package epam.repositories;

import epam.entity.Trainer;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TrainerRepository {
    Trainer insert(UUID id, Trainer trainer);

    Trainer update(UUID id, Trainer trainer);

    void delete(UUID id);

    Optional<Trainer> findByUsername(String username);

    @Transactional(readOnly = true)
    Optional<Trainer> findById(UUID id);

    @Transactional(readOnly = true)
    List<Trainer> findAll();

    boolean existsById(UUID id);
}
