package epam.util.parser;

import epam.domain.Trainee;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TraineeParser implements EntityParser<Trainee> {
    @Override
    public List<Trainee> parse(List<Map<String, String>> trainees) {
        return trainees.stream().map(data -> {
            Trainee trainee = Trainee.builder()
                    .firstname(data.get("firstname"))
                    .lastname(data.get("lastname"))
                    .dateOfBirth(data.get("dateOfBirth"))
                    .address(data.get("address"))
                    .isActive(Boolean.parseBoolean(data.get("isActive")))
                    .build();

            trainee.setUsername(trainee.getFirstname() + "." + trainee.getLastname());

            return trainee;
        }).collect(Collectors.toList());
    }
}
