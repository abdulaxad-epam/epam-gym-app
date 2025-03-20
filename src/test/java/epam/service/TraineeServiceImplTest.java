package epam.service;

import epam.dto.request_dto.TraineeRequestDTO;
import epam.dto.response_dto.TraineeResponseDTO;
import epam.entity.Trainee;
import epam.exception.TraineeNotFoundException;
import epam.mapper.TraineeMapper;
import epam.repository.TraineeRepository;
import epam.service.impl.TraineeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TraineeServiceImplTest {

    private TraineeRepository traineeRepository;
    private TraineeMapper traineeMapper;
    private TrainingService trainingService;
    private UserService userService;
    private TraineeServiceImpl traineeService;

    @BeforeEach
    void setUp() {
        traineeRepository = mock(TraineeRepository.class);
        traineeMapper = mock(TraineeMapper.class);
        trainingService = mock(TrainingService.class);
        userService = mock(UserService.class);

        traineeService = new TraineeServiceImpl(traineeRepository, traineeMapper, trainingService, userService);
    }

    @Test
    void testCreateTrainee_Success() {
        TraineeRequestDTO requestDTO = TraineeRequestDTO.builder()
                .address("123 Street")
                .build();

        Trainee trainee = new Trainee();
        Trainee savedTrainee = new Trainee();
        TraineeResponseDTO responseDTO = TraineeResponseDTO.builder().address("123 Street").build();

        when(traineeMapper.toTrainee(requestDTO)).thenReturn(trainee);
        when(traineeRepository.insert(trainee)).thenReturn(savedTrainee);
        when(traineeMapper.toTraineeResponseDTO(savedTrainee)).thenReturn(responseDTO);

        TraineeResponseDTO result = traineeService.createTrainee(requestDTO);

        assertNotNull(result);
        assertEquals("123 Street", result.getAddress());
    }

    @Test
    void testUpdateTrainee_Success() {
        String username = "testUser";
        UUID traineeId = UUID.randomUUID();
        Trainee trainee = new Trainee();
        TraineeRequestDTO updateRequest = TraineeRequestDTO.builder().address("New Address").build();
        Trainee updatedTrainee = new Trainee();
        updatedTrainee.setAddress("New Address");

        when(traineeRepository.getIdByUsername(username)).thenReturn(Optional.of(traineeId));
        when(traineeRepository.findById(traineeId)).thenReturn(Optional.of(trainee));
        when(traineeMapper.toTraineeResponseDTO(updatedTrainee)).thenReturn(TraineeResponseDTO.builder().address("New Address").build());

        TraineeResponseDTO result = traineeService.updateTrainee(username, updateRequest);

        assertNotNull(result);
        assertEquals("New Address", result.getAddress());
    }

    @Test
    void testUpdateTrainee_NotFound() {
        String username = "unknownUser";
        when(traineeRepository.getIdByUsername(username)).thenReturn(Optional.empty());

        assertThrows(TraineeNotFoundException.class, () -> traineeService.updateTrainee(username, TraineeRequestDTO.builder().build()));
    }

    @Test
    void testDeleteTrainee_Success() {
        String username = "testUser";

        when(userService.existsByUsername(username)).thenReturn(true);
        doNothing().when(trainingService).deleteTraining(username);
        doNothing().when(traineeRepository).deleteTraineeByUsername(username);

        assertDoesNotThrow(() -> traineeService.deleteTrainee(username));
    }

    @Test
    void testDeleteTrainee_NotFound() {
        String username = "unknownUser";

        when(userService.existsByUsername(username)).thenReturn(false);

        assertThrows(TraineeNotFoundException.class, () -> traineeService.deleteTrainee(username));
    }

    @Test
    void testGetTraineeByUsername_Success() {
        String username = "testUser";
        Trainee trainee = new Trainee();
        trainee.setAddress("123 Street");
        TraineeResponseDTO responseDTO = TraineeResponseDTO.builder().address("123 Street").build();

        when(traineeRepository.findByUsername(username)).thenReturn(Optional.of(trainee));
        when(traineeMapper.toTraineeResponseDTO(trainee)).thenReturn(responseDTO);

        TraineeResponseDTO result = traineeService.getTraineeByUsername(username);

        assertNotNull(result);
        assertEquals("123 Street", result.getAddress());
    }

    @Test
    void testGetTraineeByUsername_NotFound() {
        String username = "unknownUser";
        when(traineeRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(TraineeNotFoundException.class, () -> traineeService.getTraineeByUsername(username));
    }

    @Test
    void testGetAllTrainees() {
        Trainee trainee1 = new Trainee();
        Trainee trainee2 = new Trainee();
        List<Trainee> traineeList = List.of(trainee1, trainee2);

        when(traineeRepository.findAll()).thenReturn(traineeList);
        when(traineeMapper.toTraineeResponseDTO(any(Trainee.class)))
                .thenReturn(TraineeResponseDTO.builder().build(), TraineeResponseDTO.builder().build());

        List<TraineeResponseDTO> result = traineeService.getAllTrainees();

        assertEquals(2, result.size());
    }

    @Test
    void testGetTraineesByTrainer() {
        String trainerUsername = "trainer123";
        Trainee trainee1 = new Trainee();
        Trainee trainee2 = new Trainee();
        List<Trainee> trainees = List.of(trainee1, trainee2);

        when(traineeRepository.findTraineeByTrainer(trainerUsername)).thenReturn(trainees);
        when(traineeMapper.toTraineeResponseDTO(any(Trainee.class)))
                .thenReturn(TraineeResponseDTO.builder().build(), TraineeResponseDTO.builder().build());

        List<TraineeResponseDTO> result = traineeService.getTraineesByTrainer(trainerUsername);

        assertEquals(2, result.size());
    }
}
