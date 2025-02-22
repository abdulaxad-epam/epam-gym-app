package epam.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import epam.domain.Trainee;
import epam.domain.Trainer;
import epam.domain.Training;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Configuration
public class StorageConfiguration {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public Map<UUID, Trainee> inMemoryTrainee() {
        return new HashMap<>();
    }

    @Bean
    public Map<UUID, Trainer> inMemoryTrainer() {
        return new HashMap<>();
    }

    @Bean
    public Map<UUID, Training> inMemoryTraining() {
        return new HashMap<>();
    }
}
