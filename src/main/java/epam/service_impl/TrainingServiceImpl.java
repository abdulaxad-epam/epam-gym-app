package epam.service_impl;

import epam.entity.Training;
import epam.exception.TrainingNotFoundException;
import epam.mapper.TrainingMapper;
import epam.repositories.TrainingRepository;
import epam.response_dto.TrainingResponseDTO;
import epam.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;


    @Override
    public TrainingResponseDTO createTraining(UUID id, Training training) {
        Training inserted = trainingRepository.insert(id, training);
        return trainingMapper.toTrainingResponseDTO(inserted);
    }

    @Override
    public TrainingResponseDTO updateTraining(UUID id, Training training) {
        if (trainingRepository.existsById(id)) {
            Training update = trainingRepository.update(id, training);
            return trainingMapper.toTrainingResponseDTO(update);
        } else throw new TrainingNotFoundException("Training with id " + id + " not found");
    }

    @Override
    public TrainingResponseDTO getTrainingById(UUID id) {
        Training trainer = trainingRepository.findById(id);
        return trainingMapper.toTrainingResponseDTO(trainer);
    }

    @Override
    public List<TrainingResponseDTO> getAllTrainings() {
        List<Training> trainers = trainingRepository.findAll();
        return trainers.stream().map(trainingMapper::toTrainingResponseDTO).toList();
    }

    @Override
    public void deleteTraining(UUID id) {
        if (id != null)
            trainingRepository.delete(id);
    }
}
