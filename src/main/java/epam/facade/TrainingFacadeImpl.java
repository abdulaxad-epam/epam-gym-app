package epam.facade;


import epam.dto.request_dto.AuthenticateRequestDTO;
import epam.dto.request_dto.ChangePasswordRequestDTO;
import epam.dto.request_dto.RegisterTraineeRequestDTO;
import epam.dto.request_dto.RegisterTrainerRequestDTO;
import epam.dto.request_dto.TraineeRequestDTO;
import epam.dto.request_dto.TrainerRequestDTO;
import epam.dto.request_dto.TrainingRequestDTO;
import epam.dto.response_dto.TraineeResponseDTO;
import epam.dto.response_dto.TrainerResponseDTO;
import epam.dto.response_dto.TrainingResponseDTO;
import epam.service.AuthenticationService;
import epam.service.TraineeService;
import epam.service.TraineeTrainerService;
import epam.service.TrainerService;
import epam.service.TrainingService;
import epam.service.TrainingTypeService;
import epam.service.UserService;
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
    public TrainingResponseDTO getTrainingByUsername(String username) {
        return trainingService.getTrainingByUsername(username);
    }

    @Override
    public List<TrainingResponseDTO> getAllTrainings() {
        return trainingService.getAllTrainings();
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
