package epam.service;

import epam.entity.Trainee;
import epam.response_dto.TraineeResponseDTO;

import java.util.List;
import java.util.UUID;

public interface TraineeService {

    TraineeResponseDTO createTrainee(UUID id, Trainee trainee);

    TraineeResponseDTO updateTrainee(UUID id, Trainee trainee);

    void deleteTrainee(String username);

    TraineeResponseDTO getTraineeByUsername(String username);

    List<TraineeResponseDTO> getAllTrainees();
}
