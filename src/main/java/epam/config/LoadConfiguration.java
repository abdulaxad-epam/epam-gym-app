package epam.config;


import epam.dao.TraineeDAO;
import epam.dao.TrainerDAO;
import epam.dao.TrainingDAO;
import epam.domain.Trainee;
import epam.domain.Trainer;
import epam.domain.Training;
import epam.domain.User;
import epam.util.FileLoader;
import epam.util.parser.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;
import java.util.Map;
import java.util.UUID;


@Configuration
@PropertySource("classpath:application.properties")
@RequiredArgsConstructor
public class LoadConfiguration {

    private static final String TRAINEE_KEY = "trainee";
    private static final String TRAINER_KEY = "trainer";
    private static final String TRAINING_KEY = "trainings";

    private final TrainingDAO trainingDAO;
    private final TrainerDAO trainerDAO;
    private final TraineeDAO traineeDAO;

    private final FileLoader fileLoader;
    private final TraineeParser traineeParser;
    private final TrainerParser trainerParser;
    private final TrainingParser trainingParser;


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

        parser.parse(rawData).forEach(item -> {
            if (item instanceof Training)
                inserter.insert(((Training) item).getTrainingId(), item);
            else if (item.getClass() == Trainer.class)
                inserter.insert(((Trainer) item).getUserId(), item);
            else if (item.getClass() ==  Trainee.class)
                inserter.insert(((Trainee) item).getUserId(), item);
        });
    }

}
