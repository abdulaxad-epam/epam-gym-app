package epam.service.impl;

import epam.exception.exception.TraineeHasAssignedBeforeException;
import epam.entity.TrainerTrainee;
import epam.repository.TrainerTraineeRepository;
import epam.entity.Trainee;
import epam.repository.TraineeRepository;
import epam.dto.response_dto.TrainerResponseDTO;
import epam.entity.Trainer;
import epam.exception.exception.TraineeHasNotAssignedBeforeException;
import epam.exception.exception.TraineeNotFoundException;
import epam.exception.exception.TrainerNotFoundException;
import epam.service.TraineeTrainerService;
import epam.mapper.TrainerMapper;
import epam.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainerTraineeServiceImpl implements TraineeTrainerService {

    private final TrainerTraineeRepository trainerTraineeRepository;

    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;

    private final TrainerMapper trainerMapper;

    @Transactional
    @Override
    public void assignTrainerToTrainee(String currentUsername, String trainerUsername) {
        TrainerTraineeRecord result = getTrainerTraineeRecord(currentUsername, trainerUsername);

        if (!trainerTraineeRepository.trainerHasTrainee(result.trainer().getTrainerId(), result.trainee().getTraineeId())) {
            TrainerTrainee trainerTrainee = TrainerTrainee.builder()
                    .trainee(result.trainee())
                    .trainer(result.trainer())
                    .id(new TrainerTrainee.TraineeTrainerId(result.trainee().getTraineeId(), result.trainer().getTrainerId()))
                    .build();

            trainerTraineeRepository.assignTrainerToTrainee(trainerTrainee);
            return;
        }
        throw new TraineeHasAssignedBeforeException("Trainee has been assigned to trainer before");
    }



    @Transactional
    @Override
    public List<TrainerResponseDTO> updateTraineeTrainer(String traineeUsername, List<String> trainerUsernames) {

        trainerTraineeRepository.removeAllByTraineeUsername(traineeUsername);

        List<Trainer> trainers = trainerUsernames.stream().map(trainerUsername -> {
            TrainerTraineeRecord trainerTraineeRecord = getTrainerTraineeRecord(traineeUsername, trainerUsername);

            TrainerTrainee trainerTrainee = TrainerTrainee.builder()
                    .trainee(trainerTraineeRecord.trainee())
                    .trainer(trainerTraineeRecord.trainer())
                    .id(new TrainerTrainee.TraineeTrainerId(trainerTraineeRecord.trainee().getTraineeId(), trainerTraineeRecord.trainer().getTrainerId()))
                    .build();

            trainerTraineeRepository.assignTrainerToTrainee(trainerTrainee);
            return trainerTraineeRecord.trainer();
        }).toList();

        return trainers.stream().map(trainerMapper::toTrainerResponseDTO).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<TrainerResponseDTO> getAllNotAssignedTrainers(String username) {
        if (!traineeRepository.existsByUsername(username)){
            throw new TraineeNotFoundException("Trainee not found");
        }
        return trainerTraineeRepository.findByUsernameNotAssignedToTrainee(username)
                .stream().map(trainerMapper::toTrainerResponseDTO).toList();
    }

    @Override
    public Boolean removeTrainerFromTrainee(String currentUsername, String trainerUsername) {

        TrainerTraineeRecord result = getTrainerTraineeRecord(currentUsername, trainerUsername);

        if (trainerTraineeRepository.trainerHasTrainee(result.trainer().getTrainerId(), result.trainee.getTraineeId())) {
            trainerTraineeRepository.removeTraineeOfTrainer(result.trainee, result.trainer);
            return true;
        }
        throw new TraineeHasNotAssignedBeforeException("Trainee not assigned before");
    }



    private TrainerTraineeRecord getTrainerTraineeRecord(String currentUsername, String trainerUsername) {
        Trainee trainee = traineeRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new TraineeNotFoundException(currentUsername));
        Trainer trainer = trainerRepository.findByUsername(trainerUsername)
                .orElseThrow(() -> new TrainerNotFoundException(trainerUsername));
        return new TrainerTraineeRecord(trainee, trainer);
    }

    private record TrainerTraineeRecord(Trainee trainee, Trainer trainer) {
    }

}
