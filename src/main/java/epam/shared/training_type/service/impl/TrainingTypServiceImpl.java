package epam.shared.training_type.service.impl;

import epam.shared.training_type.entity.TrainingType;
import epam.shared.training_type.repository.TrainingTypeRepository;
import epam.shared.training_type.service.TrainingTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingTypServiceImpl implements TrainingTypeService {

    private final TrainingTypeRepository trainingRepository;

    @Override
    public TrainingType getTrainingByTrainingName(String trainingName) {
       return trainingRepository.findTrainingByTrainingName(trainingName);
    }

    @Override
    public List<String> findAll() {
        return trainingRepository.findAll();
    }

}
