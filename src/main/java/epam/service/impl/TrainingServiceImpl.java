package epam.service.impl;

import epam.client.dto.TrainingRequestDTO;
import epam.client.dto.TrainingResponseDTO;
import epam.entity.Training;
import epam.exception.exception.TraineeNotFoundException;
import epam.exception.exception.TrainerNotFoundException;
import epam.exception.exception.TrainingNotFoundException;
import epam.mapper.TrainingMapper;
import epam.repository.TraineeRepository;
import epam.repository.TrainerRepository;
import epam.repository.TrainingRepository;
import epam.service.TraineeTrainerService;
import epam.service.TrainingService;
import epam.service.TrainingTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Override
    @Transactional
    @PreAuthorize("hasRole('TRAINER')")
    public TrainingResponseDTO createTraining(TrainingRequestDTO trainingRequestDTO, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Training training = trainingMapper.toTraining(
                trainingRequestDTO,
                trainingTypeService.getTrainingByTrainingName(trainingRequestDTO.getTrainingType()),
                trainerRepository.findTraineeByUser_Username(userDetails.getUsername())
                        .orElseThrow(
                                () -> new TrainerNotFoundException("Trainer with username " +userDetails.getUsername() + " not found")
                        ),
                traineeRepository.findTraineeByUser_Username(trainingRequestDTO.getTraineeUsername())
                        .orElseThrow(
                                () -> new TraineeNotFoundException("Trainee with username " + trainingRequestDTO.getTraineeUsername() + " not found")
                        )
        );

        traineeTrainerService.assignTrainerToTrainee(trainingRequestDTO.getTraineeUsername(), userDetails.getUsername());

        trainingRepository.save(training);

        return trainingMapper.toTrainingResponseDTO(training);
    }

    @Override
    @Transactional
    public void deleteTraining(String username) {
        trainingRepository.getIdByUsername(username).ifPresentOrElse(trainingRepository::deleteTrainingByTrainingId, () -> {
            throw new TrainingNotFoundException("Training with username " + username + " not found");
        });
    }
}