package epam.controller;

import epam.service.impl.Authentication;
import epam.dto.response_dto.TrainingTypeResponseDTO;
import epam.service.TrainingTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TrainingTypeControllerTest {

    @Mock
    private TrainingTypeService trainingTypeService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private TrainingTypeController trainingTypeController;

    private List<TrainingTypeResponseDTO> trainingTypes;

    @BeforeEach
    void setUp() {
        trainingTypes = List.of(new TrainingTypeResponseDTO(UUID.randomUUID(), "Yoga"),
                new TrainingTypeResponseDTO(UUID.randomUUID(), "Pilates"));
    }

    @Test
    void testGetTrainingTypes_Success() {
        // Mock the behavior of the authentication

        when(trainingTypeService.findAll()).thenReturn(trainingTypes);

        // Call the controller method
        ResponseEntity<List<TrainingTypeResponseDTO>> response = trainingTypeController.getTrainingTypes("username", "password");

        // Verifying that authentication was checked
        verify(authentication).checkAuthentication("username", "password");

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
    }
}
