package epam.service;

import epam.entity.Trainee;
import epam.request_dto.TraineeRequestDTO;
import epam.response_dto.TraineeResponseDTO;

import java.util.List;
import java.util.UUID;

public interface TraineeService {

    TraineeResponseDTO createTrainee(TraineeRequestDTO trainee);

    TraineeResponseDTO updateTrainee(String username, TraineeRequestDTO trainee);

    void deleteTrainee(String username);

    TraineeResponseDTO getTraineeByUsername(String username);

    List<TraineeResponseDTO> getAllTrainees();

    List<TraineeResponseDTO> getTraineesByTrainer(String currentUsername);
}
