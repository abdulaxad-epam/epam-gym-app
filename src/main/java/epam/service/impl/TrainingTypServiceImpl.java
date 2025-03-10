package epam.service.impl;

import epam.entity.TrainingType;
import epam.repository.TrainingTypeRepository;
import epam.service.TrainingTypeService;
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
