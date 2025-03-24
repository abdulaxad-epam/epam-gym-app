package epam.training.service.impl;

import epam.shared.exception.exception.TraineeNotFoundException;
import epam.shared.exception.exception.TrainerNotFoundException;
import epam.shared.exception.exception.TrainingNotFoundException;
import epam.shared.training_type.service.TrainingTypeService;
import epam.trainee.repository.TraineeRepository;
import epam.trainer.repository.TrainerRepository;
import epam.training.dto.TrainingRequestDTO;
import epam.training.dto.TrainingResponseDTO;
import epam.training.mapper.TrainingMapper;
import epam.training.repository.TrainingRepository;
import epam.training.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;
    private final TrainingTypeService trainingTypeService;
    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;

    @Transactional
    @Override
    public TrainingResponseDTO createTraining(TrainingRequestDTO trainingRequestDTO) {

        return trainingMapper.toTrainingResponseDTO(
                trainingMapper.toTraining(
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
                )
        );
    }

    @Override
    public TrainingResponseDTO getTrainingByUsername(String username) {
        return trainingMapper.toTrainingResponseDTO(trainingRepository.findByUser_Username(username)
                .orElseThrow(() -> new TrainingNotFoundException("Training with username " + username + " not found")));
    }

    @Override
    public List<TrainingResponseDTO> getAllTrainings() {
        return trainingRepository.findAll()
                .stream().map(trainingMapper::toTrainingResponseDTO).toList();
    }

    @Override
    public void deleteTraining(String username) {
        trainingRepository.getIdByUsername(username).ifPresent(trainingRepository::delete);
    }

    @Override
    public List<TrainingResponseDTO> getTrainingsByTraineeUsername(String username) {
        return trainingRepository.findTrainingsByTrainee(username)
                .stream().map(trainingMapper::toTrainingResponseDTO).toList();
    }

    @Override
    public List<TrainingResponseDTO> getTrainingsByUsernameAndCriteria(String username, LocalDate fromDate,
                                                                       LocalDate toDate, String trainerName, String trainingType) {
        return trainingRepository.getByCriteria(username, fromDate, toDate, trainerName, trainingType)
                .stream().map(trainingMapper::toTrainingResponseDTO).toList();
    }
}