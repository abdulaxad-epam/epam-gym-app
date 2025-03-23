package epam.training.service.impl;

import epam.trainee.entity.Trainee;
import epam.trainee.repository.TraineeRepository;
import epam.trainer.entity.Trainer;
import epam.trainer.repository.TrainerRepository;
import epam.training.dto.TrainingRequestDTO;
import epam.training.dto.TrainingResponseDTO;
import epam.training.entity.Training;
import epam.shared.training_type.entity.TrainingType;
import epam.shared.exception.exception.TraineeNotFoundException;
import epam.shared.exception.exception.TrainerNotFoundException;
import epam.shared.exception.exception.TrainingNotFoundException;
import epam.training.mapper.TrainingMapper;
import epam.training.repository.TrainingRepository;
import epam.training.service.TrainingService;
import epam.shared.training_type.service.TrainingTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
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

        Trainer trainer = trainerRepository.findByUsername(trainingRequestDTO.getTrainerUsername())
                .orElseThrow(() -> new TrainerNotFoundException("Trainer with username " + trainingRequestDTO.getTrainerUsername() + " not found"));

        Trainee trainee = traineeRepository.findByUsername(trainingRequestDTO.getTraineeUsername())
                .orElseThrow(() -> new TraineeNotFoundException("Trainee with username " + trainingRequestDTO.getTraineeUsername() + " not found"));

        Training training = trainingMapper.toTraining(trainingRequestDTO, trainingType, trainer, trainee);

        Training inserted = trainingRepository.insert(training);

        return trainingMapper.toTrainingResponseDTO(inserted);
    }

    @Override
    public TrainingResponseDTO updateTraining(String username, TrainingRequestDTO trainingRequestDTO) {

        UUID trainingId = trainingRepository.getIdByUsername(username)
                .orElseThrow(() -> new TrainingNotFoundException("Training with username " + username + " not found"));

        TrainingType trainingType = trainingTypeService.getTrainingByTrainingName(trainingRequestDTO.getTrainingType());

        Trainer trainer = trainerRepository.findByUsername(trainingRequestDTO.getTrainerUsername())
                .orElseThrow(() -> new TrainerNotFoundException("Trainer with username " + trainingRequestDTO.getTrainerUsername() + " not found"));

        Trainee trainee = traineeRepository.findByUsername(trainingRequestDTO.getTraineeUsername())
                .orElseThrow(() -> new TraineeNotFoundException("Trainee with username " + trainingRequestDTO.getTraineeUsername() + " not found"));

        Training updatedTraining = trainingMapper.toTraining(trainingRequestDTO, trainingType, trainer, trainee);

        Training updated = trainingRepository.update(trainingId, updatedTraining);

        return trainingMapper.toTrainingResponseDTO(updated);
    }

    @Override
    public TrainingResponseDTO getTrainingByUsername(String username) {

        UUID trainingId = trainingRepository.getIdByUsername(username)
                .orElseThrow(() -> new TrainingNotFoundException("Training with username " + username + " not found"));

        Training training = trainingRepository.findById(trainingId);

        return trainingMapper.toTrainingResponseDTO(training);
    }

    @Override
    public List<TrainingResponseDTO> getAllTrainings() {
        List<Training> trainings = trainingRepository.findAll();
        return trainings.stream().map(trainingMapper::toTrainingResponseDTO).toList();
    }

    @Override
    public void deleteTraining(String username) {

        trainingRepository.getIdByUsername(username).ifPresent(trainingRepository::delete);
    }

    @Override
    public List<TrainingResponseDTO> getTrainingsByTraineeUsername(String username) {
        List<Training> trainings = trainingRepository.findTrainingsByTrainee(username);
        return trainings.stream().map(trainingMapper::toTrainingResponseDTO).toList();
    }

    @Override
    public List<TrainingResponseDTO> getTrainingsByUsernameAndCriteria(String username, LocalDate fromDate, LocalDate toDate, String trainerName, String trainingType) {
       List<Training> trainings = trainingRepository.getByCriteria(username, fromDate, toDate, trainerName, trainingType);
       return trainings.stream().map(trainingMapper::toTrainingResponseDTO).toList();
    }


}
