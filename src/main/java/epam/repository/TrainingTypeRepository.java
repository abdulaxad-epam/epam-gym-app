package epam.repository;

import epam.entity.TrainingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface TrainingTypeRepository extends JpaRepository<TrainingType, UUID> {
    Optional<TrainingType> findTrainingTypeByDescription(String trainingName);

    Optional<TrainingType> findTrainingTypeByTrainingTypeId(UUID trainingTypeId);

    @Query("SELECT t.description FROM TrainingType t WHERE t.trainingTypeId = :trainingTypeId")
    Optional<String> findTrainingTypeDescriptionByTrainingTypeId(UUID trainingTypeId);
}
