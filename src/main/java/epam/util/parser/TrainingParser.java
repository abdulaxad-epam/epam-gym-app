package epam.util.parser;

import epam.domain.Training;
import epam.enums.TrainingType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class TrainingParser implements EntityParser<Training> {

    @Override
    public List<Training> parse(List<Map<String, String>> trainings) {
        return trainings.stream().map(data -> Training.builder()
                .trainingId(UUID.fromString(data.get("trainingId")))
                .traineeId(data.get("traineeId"))
                .trainerId(data.get("trainerId"))
                .trainingName(data.get("trainingName"))
                .trainingDate(data.get("trainingDate"))
                .trainingType(TrainingType.valueOf(data.get("trainingType")))
                .trainingDuration(data.get("trainingDuration"))
                .build()
        ).collect(Collectors.toList());
    }
}
