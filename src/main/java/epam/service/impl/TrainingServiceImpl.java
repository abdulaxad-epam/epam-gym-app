package epam.service.impl;

import epam.entity.Trainee;
import epam.entity.Trainer;
import epam.entity.Training;
import epam.entity.TrainingType;
import epam.exception.TraineeNotFoundException;
import epam.exception.TrainerNotFoundException;
import epam.exception.TrainingNotFoundException;
import epam.mapper.TrainingMapper;
import epam.repository.TraineeRepository;
import epam.repository.TrainerRepository;
import epam.repository.TrainingRepository;
import epam.dto.request_dto.TrainingRequestDTO;
import epam.dto.response_dto.TrainingResponseDTO;
import epam.service.TrainingService;
import epam.service.TrainingTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
