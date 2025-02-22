package epam.service;


import epam.dao.TrainerDAO;
import epam.domain.Trainer;
import epam.exception.TrainerNotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class TrainerServiceImpl implements TrainerService {

    private static final Log log = LogFactory.getLog(TrainerServiceImpl.class);
    private TrainerDAO trainerDAO;

    @Autowired
    public void TrainerServiceImpl(TrainerDAO trainerDAO) {
        this.trainerDAO = trainerDAO;
    }

    @Override
    public Trainer createTrainer(UUID id, Trainer trainer) {
        log.info("Attempting to create trainer with ID: " + id + " and details: " + trainer);
        Trainer insertedTrainer = trainerDAO.insert(id, trainer);
        log.info("Trainer successfully created with ID: " + id);
        return insertedTrainer;
    }

    @Override
    public Trainer updateTrainer(UUID id, Trainer trainer) {
        log.info("Attempting to update trainer with ID: " + id + " and new details: " + trainer);
        Trainer updatedTrainer = trainerDAO.update(id, trainer);
        log.info("Trainer successfully updated with ID: " + id);
        return updatedTrainer;
    }

    @Override
    public void deleteTrainer(UUID id) {
        log.info("Attempting to delete trainer with ID: " + id);
        trainerDAO.delete(id);
        log.info("Trainer successfully deleted with ID: " + id);
    }

    @Override
    public Trainer getTrainerById(UUID id) {
        log.info("Fetching trainer with ID: " + id);
        Optional<Trainer> trainerOpt = trainerDAO.findById(id);

        if (trainerOpt.isPresent()) {
            log.info("Trainer found: " + trainerOpt.get());
            return trainerOpt.get();
        } else {
            log.error("Trainer with ID: " + id + " not found");
            throw new TrainerNotFoundException("Trainer with ID [" + id + "] not found");
        }
    }

    @Override
    public List<Trainer> getAllTrainers() {
        log.info("Fetching all trainers...");
        List<Trainer> trainerList = new ArrayList<>(trainerDAO.findAll().values());
        log.info("Total trainers found: " + trainerList.size());
        return trainerList;
    }
}
