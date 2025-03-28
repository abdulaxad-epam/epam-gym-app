package epam.trainee.service.impl;

import epam.shared.exception.exception.DateConversionException;
import epam.shared.exception.exception.TraineeNotFoundException;
import epam.trainee.dto.TraineeRequestDTO;
import epam.trainee.dto.TraineeResponseDTO;
import epam.trainee.entity.Trainee;
import epam.trainee.mapper.TraineeMapper;
import epam.trainee.repository.TraineeRepository;
import epam.trainee.service.TraineeService;
import epam.training.dto.TrainingResponseDTO;
import epam.training.entity.Training;
import epam.training.mapper.TrainingMapper;
import epam.training.service.TrainingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Log
@Service
@RequiredArgsConstructor
public class TraineeServiceImpl implements TraineeService {

    private final TraineeRepository traineeRepository;

    private final TraineeMapper traineeMapper;

    private final TrainingService trainingService;

    private final TrainingMapper trainingMapper;

    @Override
    public TraineeResponseDTO createTrainee(TraineeRequestDTO traineeRequestDTO) {

        return traineeMapper.toTraineeResponseDTO(
                traineeRepository.insert(
                        traineeMapper.toTrainee(traineeRequestDTO)
                )
        );
    }

    @Override
    public TraineeResponseDTO updateTrainee(String username, String firstname, String lastname, String dateOfBirth, String address, Boolean isActive) {
        Optional<Trainee> trainee = traineeRepository.findByUsername(username);

        trainee.ifPresentOrElse(t -> {
            try {

                t.getUser().setFirstname(firstname);
                t.getUser().setLastname(lastname);
                t.setDateOfBirth(LocalDate.parse(dateOfBirth));
                t.setAddress(address);
                t.getUser().setIsActive(isActive);

            } catch (Exception exception) {
                throw new DateConversionException("Cannot convert dateOfBirth to date");
            }

        }, () -> {
            log.info("Trainee not found with username: " + username);
            throw new TraineeNotFoundException(String.format("Trainee not found with username: %s", username));
        });
        return traineeMapper.toTraineeResponseDTO(trainee.get());
    }

    @Override
    public void deleteTrainee(String username) {
        if (!traineeRepository.existsByUsername(username)) {
            throw new TraineeNotFoundException(String.format("Trainee not found with username: %s", username));
        }
        log.info("Deleting trainee " + username);
        trainingService.deleteTraining(username);

        log.info("User " + username + " is deleted");
        traineeRepository.deleteTraineeByUsername(username);
    }

    @Override
    public TraineeResponseDTO getTraineeByUsername(String username) {
        return traineeMapper.toTraineeResponseDTO(traineeRepository.findByUsername(username.toLowerCase())
                .orElseThrow(() -> new TraineeNotFoundException("Trainee not found")));
    }

    @Override
    public List<TraineeResponseDTO> getAllTrainees() {
        return traineeRepository.findAll().stream().map(traineeMapper::toTraineeResponseDTO).toList();
    }

    @Override
    public List<TraineeResponseDTO> getTraineesByTrainer(String currentUsername) {
        return traineeRepository.findTraineeByTrainer(currentUsername).stream().map(traineeMapper::toTraineeResponseDTO).toList();
    }

    @Override
    public void updateTraineeStatus(String username, Boolean isActive) {
        Optional<Trainee> trainee = traineeRepository.findByUsername(username);
        trainee.ifPresentOrElse(t -> t.getUser().setIsActive(isActive), () -> {
            log.warning("Trainee " + username + " not found");
            throw new TraineeNotFoundException(String.format("Trainee not found with username: %s", username));
        });
    }

    @Override
    public List<TrainingResponseDTO> getTraineeTrainings(String username, String periodFrom, String periodTo, String trainerName, String trainingType) {
        log.info("Incoming data : " + periodFrom + " " + periodTo + " " + trainerName + " " + trainingType + " " + username);
        List<Training> trainings = traineeRepository.getTraineeTrainings(username, periodFrom, periodTo, trainerName, trainingType)
                .orElseThrow(() -> {
                    log.info("Trainee " + username + " not found");
                    return new TraineeNotFoundException(String.format("Trainee not found with username: %s", username));
                });
        return trainings.stream().map(trainingMapper::toTrainingResponseDTO).toList();
    }
}
