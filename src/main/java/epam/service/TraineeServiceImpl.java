package epam.service;

import epam.dao.TraineeDAO;
import epam.domain.Trainee;
import epam.exception.TraineeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TraineeServiceImpl implements TraineeService {

    private static final Log log = LogFactory.getLog(TraineeServiceImpl.class);
    private final TraineeDAO traineeDAO;

    @Override
    public Trainee createTrainee(UUID id, Trainee trainee) {
        log.info("Attempting to create trainee with ID: " + id + " and details: " + trainee);
        Trainee insertedTrainee = traineeDAO.insert(id, trainee);
        log.info("Trainee successfully created with ID: " + id);
        return insertedTrainee;
    }

    @Override
    public Trainee updateTrainee(UUID id, Trainee trainee) throws TraineeNotFoundException {
        log.info("Attempting to update trainee with ID: " + id + " and new details: " + trainee);
        Trainee updatedTrainee = traineeDAO.update(id, trainee);
        log.info("Trainee successfully updated with ID: " + id);
        return updatedTrainee;
    }

    @Override
    public void deleteTrainee(UUID id) {
        log.info("Attempting to delete trainee with ID: " + id);
        traineeDAO.delete(id);
        log.info("Trainee successfully deleted with ID: " + id);
    }

    @Override
    public Trainee getTraineeById(UUID id) {
        log.info("Fetching trainee with ID: " + id);
        Optional<Trainee> traineeOpt = traineeDAO.findById(id);

        if (traineeOpt.isPresent()) {
            log.info("Trainee found: " + traineeOpt.get());
            return traineeOpt.get();
        } else {
            log.error("Trainee with ID: " + id + " not found");
            throw new TraineeNotFoundException("Trainee with ID [" + id + "] not found");
        }
    }

    @Override
    public List<Trainee> getAllTrainees() {
        log.info("Fetching all trainees...");
        List<Trainee> traineeList = new ArrayList<>(traineeDAO.findAll().values());
        log.info("Total trainees found: " + traineeList.size());
        return traineeList;
    }
}
