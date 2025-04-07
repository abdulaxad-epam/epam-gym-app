package epam.controller;


import epam.service.impl.Authentication;
import epam.dto.response_dto.TrainerResponseDTO;
import epam.dto.response_dto.UserResponseDTO;
import epam.service.TraineeTrainerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class TraineeTrainerControllerTest {

    @Mock
    private TraineeTrainerService traineeTrainerService;

    @InjectMocks
    private TraineeTrainerController traineeTrainerController;

    @Mock
    private Authentication authentication;

    private List<TrainerResponseDTO> mockTrainers;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        UserResponseDTO trainerUser = new UserResponseDTO("John", "Doe", "johndoe", true);
        TrainerResponseDTO trainer1 = new TrainerResponseDTO("Fitness", trainerUser, null);
        TrainerResponseDTO trainer2 = new TrainerResponseDTO("Yoga", trainerUser, null);

        mockTrainers = Arrays.asList(trainer1, trainer2);
    }

    @Test
    void testGetNotAssignedTrainers_Success() {
        String username = "trainee123";
        String password = "password";

        List<TrainerResponseDTO> mockTrainers = new ArrayList<>();
        mockTrainers.add(new TrainerResponseDTO("Specialization1", new UserResponseDTO("John", "Doe", "trainer1", true), null));
        mockTrainers.add(new TrainerResponseDTO("Specialization2", new UserResponseDTO("Jane", "Smith", "trainer2", true), null));

        when(traineeTrainerService.getAllNotAssignedTrainers(username)).thenReturn(mockTrainers);

        ResponseEntity<List<TrainerResponseDTO>> response = traineeTrainerController.getNotAssignedTrainers(username, password);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());

        verify(traineeTrainerService, times(1)).getAllNotAssignedTrainers(eq(username));
    }

    @Test
    void testUpdateTraineeTrainerList_Success() {
        String username = "trainee123";
        String password = "password";
        List<String> trainerUsernames = Arrays.asList("trainer1", "trainer2");

        when(traineeTrainerService.updateTraineeTrainer(username, trainerUsernames)).thenReturn(mockTrainers);

        ResponseEntity<List<TrainerResponseDTO>> response = traineeTrainerController.updateTraineeTrainerList(username, trainerUsernames, password);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Yoga", Objects.requireNonNull(response.getBody()).get(1).getTrainerSpecialization());

        verify(traineeTrainerService).updateTraineeTrainer(username, trainerUsernames);
    }

    @Test
    void testGetNotAssignedTrainers_NotFound() {
        String username = "unknown_user";
        String password = "password";


        when(traineeTrainerService.getAllNotAssignedTrainers(username)).thenReturn(List.of());

        ResponseEntity<List<TrainerResponseDTO>> response = traineeTrainerController.getNotAssignedTrainers(username, password);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertTrue(Objects.requireNonNull(response.getBody()).isEmpty());

        verify(traineeTrainerService).getAllNotAssignedTrainers(username);
    }
}