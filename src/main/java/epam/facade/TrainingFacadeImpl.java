package epam.facade;


import epam.entity.Trainee;
import epam.entity.Trainer;
import epam.entity.Training;
import epam.request_dto.AuthenticateRequestDTO;
import epam.request_dto.ChangePasswordRequestDTO;
import epam.request_dto.RegisterTraineeRequestDTO;
import epam.request_dto.RegisterTrainerRequestDTO;
import epam.response_dto.TraineeResponseDTO;
import epam.response_dto.TrainerResponseDTO;
import epam.response_dto.TrainingResponseDTO;
import epam.service.AuthenticationService;
import epam.service.TraineeService;
import epam.service.TrainerService;
import epam.service.TrainingService;
import epam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrainingFacadeImpl implements TrainingFacade {

    private final TraineeService traineeService;

    private final TrainingService trainingService;

    private final TrainerService trainerService;

    private final UserService userService;

    private final AuthenticationService authenticationService;

    @Override
    public Boolean changePassword(ChangePasswordRequestDTO changePasswordRequestDTO) {
       return userService.changePassword(changePasswordRequestDTO);
    }

    @Override
    public Boolean existsByUsernameAndPassword(String username, String password) {
        return userService.existsByUsernameAndPassword(username, password);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userService.existsByUsername(username);
    }

    @Override
    public TrainingResponseDTO createTraining(UUID id, Training training) {
        return trainingService.createTraining(id, training);
    }

    @Override
    public TrainingResponseDTO updateTraining(UUID id, Training training) {
        return trainingService.updateTraining(id, training);
    }

    @Override
    public TrainingResponseDTO getTrainingById(UUID id) {
        return trainingService.getTrainingById(id);
    }

    @Override
    public List<TrainingResponseDTO> getAllTrainings() {
        return trainingService.getAllTrainings();
    }

    @Override
    public TrainerResponseDTO createTrainer(UUID id, Trainer training) {
        return trainerService.createTrainer(id, training);
    }

    @Override
    public TrainerResponseDTO updateTrainer(UUID id, Trainer trainer) {
        return trainerService.updateTrainer(id, trainer);
    }

    @Override
    public void deleteTrainer(String username) {
        trainerService.deleteTrainer(username);
    }

    @Override
    public TrainerResponseDTO getTrainerByUsername(String username) {
        return trainerService.getTrainerByUsername(username);
    }

    @Override
    public List<TrainerResponseDTO> getAllTrainers() {
        return trainerService.getAllTrainers();
    }

    @Override
    public TraineeResponseDTO createTrainee(UUID id, Trainee trainee) {
        return traineeService.createTrainee(id, trainee);
    }

    @Override
    public TraineeResponseDTO updateTrainee(UUID id, Trainee trainee) {
        return traineeService.updateTrainee(id, trainee);
    }

    @Override
    public void deleteTrainee(String username) {
        traineeService.deleteTrainee(username);
    }

    @Override
    public TraineeResponseDTO getTraineeByUsername(String username) {
        return traineeService.getTraineeByUsername(username);
    }

    @Override
    public List<TraineeResponseDTO> getAllTrainees() {
        return traineeService.getAllTrainees();
    }

    @Override
    public Boolean register(RegisterTraineeRequestDTO userRequestDTO) {
        return authenticationService.register(userRequestDTO);
    }

    @Override
    public Boolean register(RegisterTrainerRequestDTO userRequestDTO) {
        return authenticationService.register(userRequestDTO);
    }

    @Override
    public Boolean authenticate(AuthenticateRequestDTO authenticateRequestDTO) {
        return authenticationService.authenticate(authenticateRequestDTO);
    }
}
