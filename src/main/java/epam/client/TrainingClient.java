package epam.client;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
public class TrainingClient {

    private final RestTemplate restTemplate;
    private final HttpServletRequest request;

    @Value("${training.service.name}")
    private String trainingServiceName;

    public TrainingClient(RestTemplate restTemplate, HttpServletRequest request) {
        this.restTemplate = restTemplate;
        this.request = request;
    }

    public void deleteTrainingsByTrainer(UUID trainerId) {
        String url = buildUrl("/remove/trainer/" + trainerId);

        HttpHeaders headers = new HttpHeaders();
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            headers.set("Authorization", authHeader);
        } else {
            throw new IllegalStateException("Missing Bearer token in the request context.");
        }

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        restTemplate.exchange("http://EPAM-GYM-APP-TRAINING-SERVICE/api/trainings/remove/trainer/"+trainerId, HttpMethod.DELETE, entity, String.class);
    }

    private String buildUrl(String endpoint) {
        return "http://" + trainingServiceName + "/api/v1/trainings" + endpoint;
    }
}
