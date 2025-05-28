package epam.client.service;

import epam.client.dto.TrainingWorkloadRequestDTO;
import epam.client.dto.TrainingWorkloadResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TrainingRestClient {

    private final RestTemplate restTemplate;

    public ResponseEntity<String> deleteTrainerTrainings(String url) {
       return restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
    }

    public ResponseEntity<TrainingWorkloadResponseDTO> postTraining(String url, TrainingWorkloadRequestDTO dto) {
        return restTemplate.postForEntity(url, dto, TrainingWorkloadResponseDTO.class);
    }

    public ResponseEntity<List<TrainingWorkloadResponseDTO>> getTrainings(String url) {
        return restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {});
    }

}
