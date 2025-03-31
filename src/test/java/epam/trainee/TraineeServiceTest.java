package epam.trainee;

import epam.shared.exception.exception.DateConversionException;
import epam.shared.exception.exception.TraineeNotFoundException;
import epam.shared.security.dto.RegisterTraineeResponseDTO;
import epam.trainee.dto.TraineeRequestDTO;
import epam.trainee.dto.TraineeResponseDTO;
import epam.trainee.entity.Trainee;
import epam.trainee.mapper.TraineeMapper;
import epam.trainee.repository.TraineeRepository;
import epam.trainee.service.impl.TraineeServiceImpl;
import epam.training.dto.TrainingResponseDTO;
import epam.training.entity.Training;
import epam.training.mapper.TrainingMapper;
import epam.training.service.TrainingService;
import epam.user.entity.User;
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
        when(traineeRepository.insert(trainee)).thenReturn(trainee);
        when(traineeMapper.toRegisterTraineeResponseDTO(trainee)).thenReturn(responseDTO);

        RegisterTraineeResponseDTO result = traineeService.createTrainee(traineeRequestDTO);
        assertNotNull(result);
    }

    @Test
    void testUpdateTraineeSuccess() {
        when(traineeRepository.findByUsername("testUser")).thenReturn(Optional.of(trainee));
        when(traineeMapper.toTraineeResponseDTO(trainee)).thenReturn(new TraineeResponseDTO());

        TraineeResponseDTO result = traineeService.updateTrainee("testUser", "John", "Doe", "2000-01-01", "Address", true);
        assertNotNull(result);
        verify(traineeRepository, times(1)).findByUsername("testUser");
    }

    @Test
    void testUpdateTraineeNotFound() {
        when(traineeRepository.findByUsername("testUser")).thenReturn(Optional.empty());
        assertThrows(TraineeNotFoundException.class, () -> traineeService.updateTrainee("testUser", "John", "Doe", "2000-01-01", "Address", true));
    }

    @Test
    void testUpdateTraineeInvalidDate() {
        when(traineeRepository.findByUsername("testUser")).thenReturn(Optional.of(trainee));
        assertThrows(DateConversionException.class, () -> traineeService.updateTrainee("testUser", "John", "Doe", "invalid-date", "Address", true));
    }

    @Test
    void testDeleteTraineeSuccess() {
        when(traineeRepository.existsByUsername("testUser")).thenReturn(true);
        doNothing().when(trainingService).deleteTraining("testUser");
        doNothing().when(traineeRepository).deleteTraineeByUsername("testUser");

        assertDoesNotThrow(() -> traineeService.deleteTrainee("testUser"));
    }

    @Test
    void testDeleteTraineeNotFound() {
        when(traineeRepository.existsByUsername("testUser")).thenReturn(false);
        assertThrows(TraineeNotFoundException.class, () -> traineeService.deleteTrainee("testUser"));
    }

    @Test
    void testGetTraineeByUsername() {
        lenient().when(traineeRepository.findByUsername("testUser".toLowerCase())).thenReturn(Optional.of(trainee));
        when(traineeMapper.toTraineeResponseDTO(trainee)).thenReturn(new TraineeResponseDTO());

        TraineeResponseDTO result = traineeService.getTraineeByUsername("testUser");
        assertNotNull(result);
    }

    @Test
    void testGetTraineeByUsernameNotFound() {
        lenient().when(traineeRepository.findByUsername("testUser")).thenReturn(Optional.of(trainee));
        assertThrows(TraineeNotFoundException.class, () -> traineeService.getTraineeByUsername("testUser"));
    }

    @Test
    void testUpdateTraineeStatus() {
        when(traineeRepository.findByUsername("testUser")).thenReturn(Optional.of(trainee));
        assertDoesNotThrow(() -> traineeService.updateTraineeStatus("testUser", true));
    }

    @Test
    void testUpdateTraineeStatusNotFound() {
        when(traineeRepository.findByUsername("testUser")).thenReturn(Optional.empty());
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