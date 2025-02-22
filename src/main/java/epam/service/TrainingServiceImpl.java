package epam.service;


import epam.dao.TrainingDAO;
import epam.domain.Training;
import epam.exception.TrainingNotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TrainingServiceImpl implements TrainingService {

    private static final Log log = LogFactory.getLog(TrainingServiceImpl.class);
    private TrainingDAO trainingDAO;

    @Autowired
    public void setTrainingDAO(TrainingDAO trainingDAO) {
        this.trainingDAO = trainingDAO;
    }

    @Override
    public Training createTraining(UUID id, Training training) {
        log.info("Attempting to create training with ID: " + id + " and details: " + training);
        Training insertedTraining = trainingDAO.insert(id, training);
        log.info("Training successfully created with ID: " + id);
        return insertedTraining;
    }

    @Override
    public Training getTrainingById(UUID id) {
        log.info("Fetching training with ID: " + id);
        Optional<Training> trainingOpt = trainingDAO.findById(id);

        if (trainingOpt.isPresent()) {
            log.info("Training found: " + trainingOpt.get());
            return trainingOpt.get();
        } else {
            log.error("Training with ID: " + id + " not found");
            throw new TrainingNotFoundException("Training with ID [" + id + "] not found");
        }
    }

    @Override
    public List<Training> getAllTrainings() {
        log.info("Fetching all trainings...");
        List<Training> trainingList = new ArrayList<>(trainingDAO.findAll().values());
        log.info("Total trainings found: " + trainingList.size());
        return trainingList;
    }
}
