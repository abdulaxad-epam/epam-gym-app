package epam.client.service;

import epam.dto.response_dto.TrainingTypeResponseDTO;
import epam.entity.TrainingType;

import java.util.List;
import java.util.UUID;

public interface TrainingTypeService {
    TrainingType getTrainingByTrainingName(String trainingName);

    List<TrainingTypeResponseDTO> findAll();

    String getTrainingNameById(UUID trainingId);
}
