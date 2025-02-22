package epam.config;


import epam.dao.TraineeDAO;
import epam.dao.TrainerDAO;
import epam.dao.TrainingDAO;
import epam.util.FileLoader;
import epam.util.parser.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Configuration
@PropertySource("classpath:application.properties")
public class LoadConfiguration {

    private static final String TRAINEE_KEY = "trainee";
    private static final String TRAINER_KEY = "trainer";
    private static final String TRAINING_KEY = "trainings";

    private TrainingDAO trainingDAO;
    private TrainerDAO trainerDAO;
    private TraineeDAO traineeDAO;

    private FileLoader fileLoader;
    private TraineeParser traineeParser;
    private TrainerParser trainerParser;
    private TrainingParser trainingParser;

    @PostConstruct
    public void loadDataFromFile() {
        Map<String, List<Map<String, String>>> data = fileLoader.loadFile();

        parseAndInsertData(data, TRAINEE_KEY, traineeParser, traineeDAO::insert);
        parseAndInsertData(data, TRAINER_KEY, trainerParser, trainerDAO::insert);
        parseAndInsertData(data, TRAINING_KEY, trainingParser, trainingDAO::insert);
    }

    private <T> void parseAndInsertData(Map<String, List<Map<String, String>>> data, String key,
                                        EntityParser<T> parser, DataInserter<T> inserter) {
        List<Map<String, String>> rawData = data.get(key);
        if (rawData == null || rawData.isEmpty()) {
            return;
        }

        parser.parse(rawData).forEach(item -> inserter.insert(UUID.randomUUID(), item));
    }




    /**
        Setter injections
    **/
    @Autowired
    public void setTrainingDAO(TrainingDAO trainingDAO) {
        this.trainingDAO = trainingDAO;
    }

    @Autowired
    public void setTrainerDAO(TrainerDAO trainerDAO) {
        this.trainerDAO = trainerDAO;
    }

    @Autowired
    public void setTraineeDAO(TraineeDAO traineeDAO) {
        this.traineeDAO = traineeDAO;
    }

    @Autowired
    public void setFileLoader(FileLoader fileLoader) {
        this.fileLoader = fileLoader;
    }

    @Autowired
    public void setTrainerParser(TrainerParser trainerParser) {
        this.trainerParser = trainerParser;
    }

    @Autowired
    public void setTrainingParser(TrainingParser trainingParser) {
        this.trainingParser = trainingParser;
    }

    @Autowired
    public void setTraineeParser(TraineeParser traineeParser) {
        this.traineeParser = traineeParser;
    }
}
