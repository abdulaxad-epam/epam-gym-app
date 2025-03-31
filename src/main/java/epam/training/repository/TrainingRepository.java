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

    Optional<UUID> getIdByUsername(String username);
}
