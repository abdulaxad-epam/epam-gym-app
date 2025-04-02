package epam.controller;

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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrainingTypeControllerTest {

    @Mock
    private TrainingTypeService trainingTypeService;

    @InjectMocks
    private TrainingTypeController trainingTypeController;

    private List<TrainingTypeResponseDTO> trainingTypes;

    @BeforeEach
    void setUp() {
        trainingTypes = List.of(new TrainingTypeResponseDTO(UUID.randomUUID(),"Yoga"), new TrainingTypeResponseDTO(UUID.randomUUID(),"Pilates"));
    }

    @Test
    void testGetTrainingTypes_Success() {
        when(trainingTypeService.findAll()).thenReturn(trainingTypes);

        ResponseEntity<List<TrainingTypeResponseDTO>> response = trainingTypeController.getTrainingTypes();

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
    }
}