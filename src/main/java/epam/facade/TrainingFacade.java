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

import java.util.List;
import java.util.UUID;

public interface TrainingFacade {

    Boolean changePassword(ChangePasswordRequestDTO changePasswordRequestDTO);

    Boolean existsByUsernameAndPassword(String username, String password);

    boolean existsByUsername(String username);

    TrainingResponseDTO createTraining(UUID id, Training training);

    TrainingResponseDTO updateTraining(UUID id, Training training);

    TrainingResponseDTO getTrainingById(UUID id);

    List<TrainingResponseDTO> getAllTrainings();

    TrainerResponseDTO createTrainer(UUID id, Trainer training);

    TrainerResponseDTO updateTrainer(UUID id, Trainer trainer);

    void deleteTrainer(String username);

    TrainerResponseDTO getTrainerByUsername(String username);

    List<TrainerResponseDTO> getAllTrainers();

    TraineeResponseDTO createTrainee(UUID id, Trainee trainee);

    TraineeResponseDTO updateTrainee(UUID id, Trainee trainee);

    void deleteTrainee(String username);

    TraineeResponseDTO getTraineeByUsername(String username);

    List<TraineeResponseDTO> getAllTrainees();

    Boolean register(RegisterTraineeRequestDTO userRequestDTO);

    Boolean register(RegisterTrainerRequestDTO userRequestDTO);

    Boolean authenticate(AuthenticateRequestDTO authenticateRequestDTO);

}
