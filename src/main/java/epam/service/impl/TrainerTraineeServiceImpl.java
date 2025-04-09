package epam.service.impl;

import epam.dto.response_dto.TrainerResponseDTO;
import epam.entity.Trainee;
import epam.entity.Trainer;
import epam.entity.TrainerTrainee;
import epam.exception.exception.TraineeHasAssignedBeforeException;
import epam.exception.exception.TraineeHasNotAssignedBeforeException;
import epam.exception.exception.TraineeNotFoundException;
import epam.exception.exception.TrainerNotFoundException;
import epam.mapper.TrainerMapper;
import epam.repository.TraineeRepository;
import epam.repository.TrainerRepository;
import epam.repository.TrainerTraineeRepository;
import epam.service.TraineeTrainerService;
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

        if (!trainerTraineeRepository.existsById_TrainerIdAndId_TraineeId(result.trainer().getTrainerId(), result.trainee().getTraineeId())) {
            TrainerTrainee trainerTrainee = TrainerTrainee.builder()
                    .trainee(result.trainee())
                    .trainer(result.trainer())
                    .id(new TrainerTrainee.TraineeTrainerId(result.trainee().getTraineeId(), result.trainer().getTrainerId()))
                    .build();

            trainerTraineeRepository.save(trainerTrainee);
            return;
        }
        throw new TraineeHasAssignedBeforeException("Trainee has been assigned to trainer before");
    }



    @Transactional
    @Override
    public List<TrainerResponseDTO> updateTraineeTrainer(String traineeUsername, List<String> trainerUsernames) {

        trainerTraineeRepository.removeTrainerTraineeByTrainee_User_Username(traineeUsername);

        List<Trainer> trainers = trainerUsernames.stream().map(trainerUsername -> {
            TrainerTraineeRecord trainerTraineeRecord = getTrainerTraineeRecord(traineeUsername, trainerUsername);

            TrainerTrainee trainerTrainee = TrainerTrainee.builder()
                    .trainee(trainerTraineeRecord.trainee())
                    .trainer(trainerTraineeRecord.trainer())
                    .id(new TrainerTrainee.TraineeTrainerId(trainerTraineeRecord.trainee().getTraineeId(), trainerTraineeRecord.trainer().getTrainerId()))
                    .build();

            trainerTraineeRepository.save(trainerTrainee);
            return trainerTraineeRecord.trainer();
        }).toList();

        return trainers.stream().map(trainerMapper::toTrainerResponseDTO).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<TrainerResponseDTO> getAllNotAssignedTrainers(String username) {
        if (!traineeRepository.existsTraineeByUser_Username(username)){
            throw new TraineeNotFoundException("Trainee not found");
        }
        return trainerTraineeRepository.findByUsernameNotAssignedToTrainee(username)
                .stream().map(trainerMapper::toTrainerResponseDTO).toList();
    }


    private TrainerTraineeRecord getTrainerTraineeRecord(String currentUsername, String trainerUsername) {
        Trainee trainee = traineeRepository.findTraineeByUser_Username(currentUsername)
                .orElseThrow(() -> new TraineeNotFoundException(currentUsername));
        Trainer trainer = trainerRepository.findTraineeByUser_Username(trainerUsername)
                .orElseThrow(() -> new TrainerNotFoundException(trainerUsername));
        return new TrainerTraineeRecord(trainee, trainer);
    }

    private record TrainerTraineeRecord(Trainee trainee, Trainer trainer) {
    }

}
