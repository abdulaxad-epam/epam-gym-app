package epam.service_impl;

import epam.entity.Trainee;
import epam.exception.TraineeNotFoundException;
import epam.mapper.TraineeMapper;
import epam.repository.TraineeRepository;
import epam.request_dto.TraineeRequestDTO;
import epam.response_dto.TraineeResponseDTO;
import epam.service.TraineeService;
import epam.service.TrainingService;
import epam.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

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

        Trainee trainee = traineeMapper.toTrainee(traineeRequestDTO);

        Trainee inserted = traineeRepository.insert(trainee);

        return traineeMapper.toTraineeResponseDTO(inserted);
    }

    @Override
    public TraineeResponseDTO updateTrainee(String username, TraineeRequestDTO traineeRequestDTO) {

        Optional<UUID> idByUsername = traineeRepository.getIdByUsername(username);

        if (idByUsername.isPresent()) {
            Trainee trainee = traineeRepository.findById(idByUsername.get()).get();
            trainee.setAddress(traineeRequestDTO.getAddress());
            trainee.setDateOfBirth(traineeRequestDTO.getDateOfBirth());
            Trainee update = traineeRepository.update(idByUsername.get(), trainee);
            return traineeMapper.toTraineeResponseDTO(update);

        } else
            throw new TraineeNotFoundException("Trainee not found");
    }

    @Override
    public void deleteTrainee(String username) {
        log.info("Deleting trainee " + username);
        if (userService.existsByUsername(username)) {

            log.info("User " + username + " does exist");
            trainingService.deleteTraining(username);

            log.info("User " + username + " is deleted");
            traineeRepository.deleteTraineeByUsername(username);


        } else
            throw new TraineeNotFoundException("Trainee not found");
    }

    @Override
    public TraineeResponseDTO getTraineeByUsername(String username) {
        Optional<Trainee> byId = traineeRepository.findByUsername(username);
        if (byId.isPresent())
            return traineeMapper.toTraineeResponseDTO(byId.get());
        else
            throw new TraineeNotFoundException("Trainee not found");
    }

    @Override
    public List<TraineeResponseDTO> getAllTrainees() {
        List<Trainee> trainees = traineeRepository.findAll();

        return trainees.stream().map(traineeMapper::toTraineeResponseDTO).toList();

    }

    @Override
    public List<TraineeResponseDTO> getTraineesByTrainer(String currentUsername) {
        List<Trainee> trainees = traineeRepository.findTraineeByTrainer(currentUsername);
        return trainees.stream().map(traineeMapper::toTraineeResponseDTO).toList();
    }
}
