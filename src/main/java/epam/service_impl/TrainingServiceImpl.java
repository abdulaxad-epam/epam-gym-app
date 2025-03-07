package epam.service_impl;

import epam.entity.Training;
import epam.exception.TrainingNotFoundException;
import epam.mapper.TrainingMapper;
import epam.repository.TrainingRepository;
import epam.response_dto.TrainingResponseDTO;
import epam.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;

    @Override
    public TrainingResponseDTO createTraining(Training training) {
        Training inserted = trainingRepository.insert(training);
        return trainingMapper.toTrainingResponseDTO(inserted);
    }

    @Override
    public TrainingResponseDTO updateTraining(String username, Training training) {
        Optional<UUID> id = trainingRepository.getIdByUsername(username);
        if (id.isPresent()) {
            Training update = trainingRepository.update(id.get(), training);
            return trainingMapper.toTrainingResponseDTO(update);
        }
        throw new TrainingNotFoundException("Training with id " + id + " not found");
    }

    @Override
    public TrainingResponseDTO getTrainingByUsername(String username) {
        Optional<UUID> id = trainingRepository.getIdByUsername(username);
        if (id.isPresent()) {
            Training trainer = trainingRepository.findById(id.get());
            return trainingMapper.toTrainingResponseDTO(trainer);
        }
        throw new TrainingNotFoundException("Training with id " + id + " not found");
    }

    @Override
    public List<TrainingResponseDTO> getAllTrainings() {
        List<Training> trainers = trainingRepository.findAll();
        return trainers.stream().map(trainingMapper::toTrainingResponseDTO).toList();
    }

    @Override
    public void deleteTraining(String username) {
        Optional<UUID> id = trainingRepository.getIdByUsername(username);
        id.ifPresent(trainingRepository::delete);
    }

    @Override
    public List<TrainingResponseDTO> getTrainingsByTraineeUsername(String username) {
        List<Training> trainings = trainingRepository.findTrainingsByTrainee(username);
        return trainings.stream().map(trainingMapper::toTrainingResponseDTO).toList();
    }
}
