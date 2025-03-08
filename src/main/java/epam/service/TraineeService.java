package epam.service;

import epam.request_dto.TraineeRequestDTO;
import epam.response_dto.TraineeResponseDTO;

import java.util.List;

public interface TraineeService {

    TraineeResponseDTO createTrainee(TraineeRequestDTO trainee);

    TraineeResponseDTO updateTrainee(String username, TraineeRequestDTO trainee);

    void deleteTrainee(String username);

    TraineeResponseDTO getTraineeByUsername(String username);

    List<TraineeResponseDTO> getAllTrainees();

    List<TraineeResponseDTO> getTraineesByTrainer(String currentUsername);
}
