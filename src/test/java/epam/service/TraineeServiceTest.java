package epam.service;

import epam.dto.request_dto.TraineeRequestDTO;
import epam.dto.request_dto.UpdateTraineeRequestDTO;
import epam.dto.response_dto.RegisterTraineeResponseDTO;
import epam.dto.response_dto.TraineeResponseDTO;
import epam.dto.response_dto.TrainingResponseDTO;
import epam.entity.Trainee;
import epam.entity.Training;
import epam.entity.User;
import epam.exception.exception.TraineeNotFoundException;
import epam.mapper.TraineeMapper;
import epam.mapper.TrainingMapper;
import epam.repository.TraineeRepository;
import epam.service.impl.TraineeServiceImpl;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TraineeServiceTest {

    @Mock
    private TraineeRepository traineeRepository;

    @Mock
    private TraineeMapper traineeMapper;

    @Mock
    private TrainingService trainingService;

    @Mock
    private TrainingMapper trainingMapper;

    @InjectMocks
    private TraineeServiceImpl traineeService;

    private Trainee trainee;
    private TraineeRequestDTO traineeRequestDTO;

    @BeforeEach
    void setUp() {
        trainee = new Trainee();
        trainee.setUser(new User());

        traineeRequestDTO = new TraineeRequestDTO();
    }


    @Test
    void testCreateTrainee() {
        RegisterTraineeResponseDTO responseDTO = new RegisterTraineeResponseDTO();
        when(traineeMapper.toTrainee(traineeRequestDTO)).thenReturn(trainee);
        when(traineeRepository.save(trainee)).thenReturn(trainee);
        when(traineeMapper.toRegisterTraineeResponseDTO(trainee)).thenReturn(responseDTO);

        RegisterTraineeResponseDTO result = traineeService.createTrainee(traineeRequestDTO);
        assertNotNull(result);
    }

    @Test
    void testUpdateTraineeSuccess() {
        UpdateTraineeRequestDTO updateRequestDTO = Instancio.create(UpdateTraineeRequestDTO.class);
        when(traineeRepository.findTraineeByUser_Username("testUser")).thenReturn(Optional.of(trainee));
        when(traineeMapper.toTraineeResponseDTO(trainee)).thenReturn(new TraineeResponseDTO());

        TraineeResponseDTO result = traineeService.updateTrainee("testUser", updateRequestDTO);
        assertNotNull(result);
        verify(traineeRepository, times(1)).findTraineeByUser_Username("testUser");
    }

    @Test
    void testUpdateTraineeNotFound() {
        UpdateTraineeRequestDTO updateRequestDTO = Instancio.create(UpdateTraineeRequestDTO.class);
        when(traineeRepository.findTraineeByUser_Username("testUser")).thenReturn(Optional.empty());
        assertThrows(TraineeNotFoundException.class, () -> traineeService.updateTrainee("testUser", updateRequestDTO));
    }

    @Test
    void testDeleteTraineeSuccess() {
        when(traineeRepository.existsTraineeByUser_Username("testUser")).thenReturn(true);
        doNothing().when(trainingService).deleteTraining("testUser");
        doNothing().when(traineeRepository).deleteTraineeByUser_Username("testUser");

        assertDoesNotThrow(() -> traineeService.deleteTrainee("testUser"));
    }


    @Test
    void testDeleteTraineeNotFound() {
        when(traineeRepository.existsTraineeByUser_Username("testUser")).thenReturn(false);
        assertThrows(TraineeNotFoundException.class, () -> traineeService.deleteTrainee("testUser"));
    }

    @Test
    void testGetTraineeByUsername() {
        lenient().when(traineeRepository.findTraineeByUser_Username("testUser".toLowerCase())).thenReturn(Optional.of(trainee));
        when(traineeMapper.toTraineeResponseDTO(trainee)).thenReturn(new TraineeResponseDTO());

        TraineeResponseDTO result = traineeService.getTraineeByUsername("testUser");
        assertNotNull(result);
    }

    @Test
    void testGetTraineeByUsernameNotFound() {
        lenient().when(traineeRepository.findTraineeByUser_Username("testUser")).thenReturn(Optional.of(trainee));
        assertThrows(TraineeNotFoundException.class, () -> traineeService.getTraineeByUsername("testUser"));
    }

    @Test
    void testUpdateTraineeStatus() {
        when(traineeRepository.findTraineeByUser_Username("testUser")).thenReturn(Optional.of(trainee));
        assertDoesNotThrow(() -> traineeService.updateTraineeStatus("testUser", true));
    }

    @Test
    void testUpdateTraineeStatusNotFound() {
        when(traineeRepository.findTraineeByUser_Username("testUser")).thenReturn(Optional.empty());
        assertThrows(TraineeNotFoundException.class, () -> traineeService.updateTraineeStatus("testUser", true));
    }

    @Test
    void testGetTraineeTrainingsSuccess() {
        List<Training> trainings = List.of(new Training());
        when(traineeRepository.getTraineeTrainings("testUser", "2023-01-01", "2023-12-31", null, null)).thenReturn(Optional.of(trainings));
        when(trainingMapper.toTrainingResponseDTO(any())).thenReturn(new TrainingResponseDTO());

        List<TrainingResponseDTO> result = traineeService.getTraineeTrainings("testUser", "2023-01-01", "2023-12-31", null, null);
        assertFalse(result.isEmpty());
    }

    @Test
    void testGetTraineeTrainingsNotFound() {
        when(traineeRepository.getTraineeTrainings("testUser", "2023-01-01", "2023-12-31", null, null)).thenReturn(Optional.empty());
        assertThrows(TraineeNotFoundException.class, () -> traineeService.getTraineeTrainings("testUser", "2023-01-01", "2023-12-31", null, null));
    }
}