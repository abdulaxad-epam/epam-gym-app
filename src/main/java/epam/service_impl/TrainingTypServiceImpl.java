package epam.service_impl;

import epam.entity.Training;
import epam.entity.TrainingType;
import epam.repository.TrainingTypeRepository;
import epam.service.TrainingTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainingTypServiceImpl implements TrainingTypeService {

    private final TrainingTypeRepository trainingRepository;

    @Override
    public TrainingType getTrainingByTrainingName(String trainingName) {
       return trainingRepository.findTrainingByTrainingName(trainingName);
    }
}
