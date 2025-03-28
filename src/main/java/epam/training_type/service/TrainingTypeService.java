package epam.training_type.service;

import epam.training_type.dto.TrainingTypeResponseDTO;
import epam.training_type.entity.TrainingType;

import java.util.List;

public interface TrainingTypeService {
    TrainingType getTrainingByTrainingName(String trainingName);

    List<TrainingTypeResponseDTO> findAll();
}
