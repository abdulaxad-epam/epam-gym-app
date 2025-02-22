package epam.service;

import epam.domain.Trainee;

import java.util.List;
import java.util.UUID;


public interface TraineeService {

    Trainee createTrainee(UUID id, Trainee trainee);

    Trainee updateTrainee(UUID id, Trainee trainee);

    void deleteTrainee(UUID id);

    Trainee getTraineeById(UUID id);

    List<Trainee> getAllTrainees();
}
