package epam.trainee.service.impl;

import epam.trainee.dto.TraineeRequestDTO;
import epam.trainee.dto.TraineeResponseDTO;
import epam.trainee.entity.Trainee;
import epam.shared.exception.exception.TraineeNotFoundException;
import epam.trainee.mapper.TraineeMapper;
import epam.trainee.repository.TraineeRepository;
import epam.trainee.service.TraineeService;
import epam.training.service.TrainingService;
import epam.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class TraineeServiceImpl implements TraineeService {

    private static final Log log = LogFactory.getLog(TraineeServiceImpl.class);
    private final TraineeRepository traineeRepository;

    private final TraineeMapper traineeMapper;

    private final TrainingService trainingService;

    private final UserService userService;

    @Override
    public TraineeResponseDTO createTrainee(TraineeRequestDTO traineeRequestDTO) {

        return traineeMapper.toTraineeResponseDTO(
                traineeRepository.insert(
                        traineeMapper.toTrainee(traineeRequestDTO)
                )
        );
    }

    @Transactional
    @Override
    public TraineeResponseDTO updateTrainee(String username, TraineeRequestDTO traineeRequestDTO) {

        return traineeMapper.toTraineeResponseDTO(
                traineeRepository.findByUsername(username)
                        .map(trainee -> {
                            trainee.setAddress(traineeRequestDTO.getAddress());
                            trainee.setDateOfBirth(traineeRequestDTO.getDateOfBirth());
                            return trainee;
                        })
                        .orElseThrow(
                                () -> new TraineeNotFoundException(String.format("Trainee not found with username: %s", username))
                        )
        );
    }

    @Override
    public void deleteTrainee(String username) {
        log.info("Deleting trainee " + username);
        if (userService.existsByUsername(username)) {
            log.info("User " + username + " does exist");
            trainingService.deleteTraining(username);

            log.info("User " + username + " is deleted");
            traineeRepository.deleteTraineeByUsername(username);
        }
        throw new TraineeNotFoundException("Trainee not found");
    }

    @Override
    public TraineeResponseDTO getTraineeByUsername(String username) {
        return traineeMapper.toTraineeResponseDTO(traineeRepository.findByUsername(username)
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
}
