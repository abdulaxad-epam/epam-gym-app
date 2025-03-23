package epam.shared.facade;

import epam.shared.security.dto.AuthenticateRequestDTO;
import epam.shared.security.dto.ChangePasswordRequestDTO;
import epam.shared.security.dto.RegisterTraineeRequestDTO;
import epam.shared.security.dto.RegisterTrainerRequestDTO;
import epam.trainee.dto.TraineeRequestDTO;
import epam.trainee.dto.TraineeResponseDTO;
import epam.trainer.dto.TrainerRequestDTO;
import epam.trainer.dto.TrainerResponseDTO;
import epam.training.dto.TrainingRequestDTO;
import epam.training.dto.TrainingResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface TrainingFacade {
    Boolean register(RegisterTraineeRequestDTO userRequestDTO);

    Boolean register(RegisterTrainerRequestDTO userRequestDTO);

    Boolean authenticate(AuthenticateRequestDTO authenticateRequestDTO);

    Boolean changePassword(ChangePasswordRequestDTO changePasswordRequestDTO);

    TraineeResponseDTO createTrainee(TraineeRequestDTO trainee);

    TraineeResponseDTO updateTrainee(String username, TraineeRequestDTO trainee);

    void deleteTrainee(String username);

    TraineeResponseDTO getTraineeByUsername(String username);

    List<TraineeResponseDTO> getAllTrainees();

    TrainerResponseDTO createTrainer(TrainerRequestDTO training);

    TrainerResponseDTO updateTrainer(String username, TrainerRequestDTO trainer);

    void deleteTrainer(String username);

    TrainerResponseDTO getTrainerByUsername(String username);

    List<TrainerResponseDTO> getAllTrainers();

    TrainingResponseDTO createTraining(TrainingRequestDTO training);

    TrainingResponseDTO updateTraining(String username, TrainingRequestDTO training);

    TrainingResponseDTO getTrainingByUsername(String username);

    List<TrainingResponseDTO> getAllTrainings();

    void deleteTraining(String username);

    Boolean existsByUsernameAndPassword(String username, String password);

    boolean existsByUsername(String username);

    List<TrainerResponseDTO> getTrainersByTraineeUsername(String currentUsername);

    Boolean addTrainerToTrainee(String currentUsername, String trainerUsername);

    Boolean removeTrainerFromTrainee(String currentUsername, String trainerUsername);

    List<TraineeResponseDTO> getTraineesByTrainerUsername(String currentUsername);

    List<TrainingResponseDTO> getTrainingsByTraineeUsername(String username);

    Boolean toggleStatus(String username);

    List<String> findAllTrainingTypes();

    List<TrainingResponseDTO> getTraineeTrainings(String username, LocalDate fromDate, LocalDate toDate, String trainerName, String trainingType);

    List<TrainingResponseDTO> getTrainerTrainings(String currentUsername, LocalDate fromDate, LocalDate toDate, String trainerName, String trainingType);
}
