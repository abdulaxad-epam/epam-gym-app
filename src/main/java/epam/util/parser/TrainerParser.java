package epam.util.parser;

import epam.domain.Trainer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TrainerParser implements EntityParser<Trainer> {

    @Override
    public List<Trainer> parse(List<Map<String, String>> trainers) {
        return trainers.stream().map(data -> {
            Trainer trainer = Trainer.builder()
                    .firstname(data.get("firstname"))
                    .lastname(data.get("lastname"))
                    .specialization(data.get("specialization"))
                    .isActive(Boolean.parseBoolean(data.get("isActive")))
                    .build();

            trainer.setUsername(trainer.getFirstname() + "." + trainer.getLastname());

            return trainer;
        }).collect(Collectors.toList());
    }
}
