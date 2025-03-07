package epam.service_impl;

import epam.entity.Trainee;
import epam.entity.Trainer;
import epam.entity.Training;
import epam.entity.TrainingType;
import epam.exception.TraineeNotFoundException;
import epam.exception.TrainingNotFoundException;
import epam.mapper.TrainingMapper;
import epam.repository.TraineeRepository;
import epam.repository.TrainerRepository;
import epam.repository.TrainingRepository;
import epam.request_dto.TrainingRequestDTO;
import epam.response_dto.TrainingResponseDTO;
import epam.service.TrainingService;
import epam.service.TrainingTypeService;
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
    private final TrainingTypeService trainingTypeService;
    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;

    @Override
    public TrainingResponseDTO createTraining(TrainingRequestDTO trainingRequestDTO) {

        TrainingType trainingType =
                trainingTypeService.getTrainingByTrainingName(trainingRequestDTO.getTrainingType());

        Trainer trainer = trainerRepository.findByUsername(
                trainingRequestDTO.getTraineeUsername()).orElseThrow(() -> new TraineeNotFoundException("Username not found"));

        Trainee trainee = traineeRepository.findByUsername(
                trainingRequestDTO.getTraineeUsername()).orElseThrow(() -> new TraineeNotFoundException("Username not found"));

        Training training = trainingMapper.toTraining(trainingRequestDTO, trainingType, trainer, trainee);

        Training inserted = trainingRepository.insert(training);

        return trainingMapper.toTrainingResponseDTO(inserted);
    }

    @Override
    public TrainingResponseDTO updateTraining(String username, TrainingRequestDTO trainingRequestDTO) {

        Optional<UUID> id = trainingRepository.getIdByUsername(username);

        TrainingType trainingType =
                trainingTypeService.getTrainingByTrainingName(trainingRequestDTO.getTrainingType());

        Trainer trainer = trainerRepository.findByUsername(
                trainingRequestDTO.getTraineeUsername()).orElseThrow(() -> new TraineeNotFoundException("Username not found"));

        Trainee trainee = traineeRepository.findByUsername(
                trainingRequestDTO.getTraineeUsername()).orElseThrow(() -> new TraineeNotFoundException("Username not found"));


        if (id.isPresent()) {

            Training training = trainingMapper.toTraining(trainingRequestDTO, trainingType, trainer, trainee);

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
