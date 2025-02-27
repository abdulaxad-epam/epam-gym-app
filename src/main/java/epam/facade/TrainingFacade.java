package epam.facade;


import epam.domain.Trainee;
import epam.domain.Trainer;
import epam.domain.Training;
import epam.service.TraineeService;
import epam.service.TrainerService;
import epam.service.TrainingService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TrainingFacade {

    private static final Log log = LogFactory.getLog(TrainingFacade.class);

    private final TrainingService trainingService;
    private final TrainerService trainerService;
    private final TraineeService traineeService;

    @Autowired
    public TrainingFacade(TrainingService trainingService, TrainerService trainerService, TraineeService traineeService) {
        this.trainingService = trainingService;
        this.trainerService = trainerService;
        this.traineeService = traineeService;
    }


/**
    Training Operations
            */
    public Training createTraining(UUID id, Training training) {
        log.info("Creating training with ID: "+ id);
        return trainingService.createTraining(id, training);
    }

    public Training getTrainingById(UUID id) {
        log.info("Fetching training with ID: "+ id);
        return trainingService.getTrainingById(id);
    }

    public List<Training> getAllTrainings() {
        log.info("Fetching all trainings...");
        return trainingService.getAllTrainings();
    }


    /**
    Trainer Operations
     */
    public Trainer createTrainer(UUID id, Trainer trainer) {
        log.info("Creating trainer with ID: "+ id);
        return trainerService.createTrainer(id, trainer);
    }

    public Trainer updateTrainer(UUID id, Trainer trainer) {
        log.info("Updating trainer with ID: "+ id);
        return trainerService.updateTrainer(id, trainer);
    }

    public void deleteTrainer(UUID id) {
        log.info("Deleting trainer with ID: "+ id);
        trainerService.deleteTrainer(id);
    }

    public Trainer getTrainerById(UUID id) {
        log.info("Fetching trainer with ID: "+ id);
        return trainerService.getTrainerById(id);
    }

    public List<Trainer> getAllTrainers() {
        log.info("Fetching all trainers...");
        return trainerService.getAllTrainers();
    }


    /**
    Trainee Operations
     **/
    public Trainee createTrainee(UUID id, Trainee trainee) {
        log.info("Creating trainee with ID: "+ id);
        return traineeService.createTrainee(id, trainee);
    }

    public Trainee updateTrainee(UUID id, Trainee trainee) {
        log.info("Updating trainee with ID: "+ id);
        return traineeService.updateTrainee(id, trainee);
    }

    public void deleteTrainee(UUID id) {
        log.info("Deleting trainee with ID: "+ id);
        traineeService.deleteTrainee(id);
    }

    public Trainee getTraineeById(UUID id) {
        log.info("Fetching trainee with ID: "+ id);
        return traineeService.getTraineeById(id);
    }

    public List<Trainee> getAllTrainees() {
        log.info("Fetching all trainees...");
        return traineeService.getAllTrainees();
    }
}
