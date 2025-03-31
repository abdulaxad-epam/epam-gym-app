package epam.training_type.service.impl;

import epam.shared.exception.exception.TrainingTypeNotFoundException;
import epam.training_type.dto.TrainingTypeResponseDTO;
import epam.training_type.entity.TrainingType;
import epam.training_type.mapper.TrainingTypeMapper;
import epam.training_type.repository.TrainingTypeRepository;
import epam.training_type.service.TrainingTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingTypeServiceImpl implements TrainingTypeService {

    private final TrainingTypeRepository trainingTypeRepository;
    private final TrainingTypeMapper trainingTypeMapper;

    @Override
    public TrainingType getTrainingByTrainingName(String trainingName) {
        return trainingTypeRepository.findTrainingByTrainingName(trainingName)
                .orElseThrow(() -> new TrainingTypeNotFoundException("Training with training name " + trainingName + " not found"));
    }

    @Override
    public List<TrainingTypeResponseDTO> findAll() {
        List<TrainingType> trainingTypes = trainingTypeRepository.findAll();
        return trainingTypes.stream().map(trainingTypeMapper::toTrainingTypeResponseDTO).toList();
    }
}
