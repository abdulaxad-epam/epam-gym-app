package epam.shared.facade;


import epam.shared.security.dto.AuthenticateRequestDTO;
import epam.shared.security.dto.ChangePasswordRequestDTO;
import epam.shared.security.dto.RegisterTraineeRequestDTO;
import epam.shared.security.dto.RegisterTrainerRequestDTO;
import epam.shared.security.service.AuthenticationService;
import epam.trainee.dto.TraineeRequestDTO;
import epam.trainee.dto.TraineeResponseDTO;
import epam.trainee.service.TraineeService;
import epam.shared.trainee_trainer.service.TraineeTrainerService;
import epam.trainer.dto.TrainerRequestDTO;
import epam.trainer.dto.TrainerResponseDTO;
import epam.trainer.service.TrainerService;
import epam.training.dto.TrainingRequestDTO;
import epam.training.dto.TrainingResponseDTO;
import epam.training.service.TrainingService;
import epam.shared.training_type.service.TrainingTypeService;
import epam.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingFacadeImpl implements TrainingFacade {

    private final TraineeService traineeService;

    private final TrainingService trainingService;

    private final TrainerService trainerService;

    private final TrainingTypeService trainingTypeService;

    private final UserService userService;

    private final AuthenticationService authenticationService;

    private final TraineeTrainerService trainerTrainerService;

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

    @Override
    public Boolean changePassword(ChangePasswordRequestDTO changePasswordRequestDTO) {
        return userService.changePassword(changePasswordRequestDTO);
    }

    @Override
    public TraineeResponseDTO createTrainee(TraineeRequestDTO trainee) {
        return traineeService.createTrainee(trainee);
    }

    @Override
    public TraineeResponseDTO updateTrainee(String username, TraineeRequestDTO trainee) {
        return traineeService.updateTrainee(username, trainee);
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
    public TrainerResponseDTO createTrainer(TrainerRequestDTO training) {
        return trainerService.createTrainer(training);
    }

    @Override
    public TrainerResponseDTO updateTrainer(String username, TrainerRequestDTO trainer) {
        return trainerService.updateTrainer(username, trainer);
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
    public TrainingResponseDTO createTraining(TrainingRequestDTO training) {
        return trainingService.createTraining(training);
    }

    @Override
    public TrainingResponseDTO updateTraining(String username, TrainingRequestDTO training) {
        return trainingService.updateTraining(username, training);
    }

    @Override
    public TrainingResponseDTO getTrainingByUsername(String username) {
        return trainingService.getTrainingByUsername(username);
    }

    @Override
    public List<TrainingResponseDTO> getAllTrainings() {
        return trainingService.getAllTrainings();
    }

    @Override
    public void deleteTraining(String username) {
        trainingService.deleteTraining(username);
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
    public List<TrainerResponseDTO> getTrainersByTraineeUsername(String currentUsername) {
        return trainerService.getTrainersByTrainee(currentUsername);
    }

    @Override
    public Boolean addTrainerToTrainee(String currentUsername, String trainerUsername) {
        return trainerTrainerService.addTrainerToTrainee(currentUsername, trainerUsername);
    }

    @Override
    public Boolean removeTrainerFromTrainee(String currentUsername, String trainerUsername) {
        return trainerTrainerService.removeTrainerFromTrainee(currentUsername, trainerUsername);
    }

    @Override
    public List<TraineeResponseDTO> getTraineesByTrainerUsername(String currentUsername) {
        return traineeService.getTraineesByTrainer(currentUsername);
    }

    @Override
    public List<TrainingResponseDTO> getTrainingsByTraineeUsername(String username) {
        return trainingService.getTrainingsByTraineeUsername(username);
    }

    @Override
    public Boolean toggleStatus(String username) {
        return userService.toggleStatus(username);
    }

    @Override
    public List<String> findAllTrainingTypes() {
        return trainingTypeService.findAll();
    }

    @Override
    public List<TrainingResponseDTO> getTraineeTrainings(String username, LocalDate fromDate, LocalDate toDate, String trainerName, String trainingType) {
        return trainingService.getTrainingsByUsernameAndCriteria(username, fromDate, toDate, trainerName, trainingType);
    }

    @Override
    public List<TrainingResponseDTO> getTrainerTrainings(String currentUsername, LocalDate fromDate, LocalDate toDate, String traineeName, String trainingType) {
        return trainingService.getTrainingsByUsernameAndCriteria(currentUsername, fromDate, toDate, traineeName, trainingType);
    }
}
