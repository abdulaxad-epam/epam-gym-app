package epam.training.service.impl;

import epam.shared.exception.exception.TraineeNotFoundException;
import epam.shared.exception.exception.TrainerNotFoundException;
import epam.shared.trainee_trainer.service.TraineeTrainerService;
import epam.trainee.repository.TraineeRepository;
import epam.trainer.repository.TrainerRepository;
import epam.training.dto.TrainingRequestDTO;
import epam.training.dto.TrainingResponseDTO;
import epam.training.entity.Training;
import epam.training.mapper.TrainingMapper;
import epam.training.repository.TrainingRepository;
import epam.training.service.TrainingService;
import epam.training_type.service.TrainingTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;
    private final TrainingTypeService trainingTypeService;

    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;

    private final TraineeTrainerService traineeTrainerService;

    @Transactional
    @Override
    public TrainingResponseDTO createTraining(TrainingRequestDTO trainingRequestDTO) {
        Training training = trainingMapper.toTraining(
                trainingRequestDTO,
                trainingTypeService.getTrainingByTrainingName(trainingRequestDTO.getTrainingType()),
                trainerRepository.findByUsername(trainingRequestDTO.getTrainerUsername())
                        .orElseThrow(
                                () -> new TrainerNotFoundException("Trainer with username " + trainingRequestDTO.getTrainerUsername() + " not found")
                        ),
                traineeRepository.findByUsername(trainingRequestDTO.getTraineeUsername())
                        .orElseThrow(
                                () -> new TraineeNotFoundException("Trainee with username " + trainingRequestDTO.getTraineeUsername() + " not found")
                        )
        );

        traineeTrainerService.assignTrainerToTrainee(trainingRequestDTO.getTraineeUsername(), trainingRequestDTO.getTrainerUsername());

        trainingRepository.insert(training);

        return trainingMapper.toTrainingResponseDTO(training);
    }


    @Override
    public void deleteTraining(String username) {
        trainingRepository.getIdByUsername(username).ifPresent(trainingRepository::delete);
    }

}