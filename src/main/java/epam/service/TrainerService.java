package epam.service;

import epam.domain.Trainer;

import java.util.List;
import java.util.UUID;

public interface TrainerService {

    Trainer createTrainer(UUID id, Trainer training);

    Trainer updateTrainer(UUID id, Trainer training);

    void deleteTrainer(UUID id);

    Trainer getTrainerById(UUID id);

    List<Trainer> getAllTrainers();
}
