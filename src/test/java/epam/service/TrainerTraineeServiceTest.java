package epam.service;

import epam.dto.response_dto.TrainerResponseDTO;
import epam.entity.Trainee;
import epam.entity.Trainer;
import epam.entity.TrainerTrainee;
import epam.exception.exception.TraineeHasAssignedBeforeException;
import epam.exception.exception.TraineeNotFoundException;
import epam.mapper.TrainerMapper;
import epam.repository.TraineeRepository;
import epam.repository.TrainerRepository;
import epam.repository.TrainerTraineeRepository;
import epam.service.impl.TrainerTraineeServiceImpl;
import org.instancio.Instancio;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, InstancioExtension.class})
public class TrainerTraineeServiceTest {

    @Mock
    private TrainerTraineeRepository trainerTraineeRepository;

    @Mock
    private TraineeRepository traineeRepository;

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private TrainerMapper trainerMapper;

    @InjectMocks
    private TrainerTraineeServiceImpl trainerTraineeService;

    private Trainee trainee;
    private Trainer trainer;

    @BeforeEach
    void setUp() {
        trainee = Instancio.create(Trainee.class);
        trainer = Instancio.create(Trainer.class);
    }

    @Test
    void assignTrainerToTrainee_Success() {
        when(traineeRepository.findTraineeByUser_Username(anyString())).thenReturn(Optional.of(trainee));
        when(trainerRepository.findTraineeByUser_Username(anyString())).thenReturn(Optional.of(trainer));
        when(trainerTraineeRepository.existsById_TrainerIdAndId_TraineeId(any(), any())).thenReturn(false);

        assertDoesNotThrow(() -> trainerTraineeService.assignTrainerToTrainee("traineeUser", "trainerUser"));

        verify(trainerTraineeRepository, times(1)).save(any(TrainerTrainee.class));
    }

    @Test
    void assignTrainerToTrainee_TraineeAlreadyAssigned() {
        when(traineeRepository.findTraineeByUser_Username(anyString())).thenReturn(Optional.of(trainee));
        when(trainerRepository.findTraineeByUser_Username(anyString())).thenReturn(Optional.of(trainer));
        when(trainerTraineeRepository.existsById_TrainerIdAndId_TraineeId(any(), any())).thenReturn(true);

        assertThrows(TraineeHasAssignedBeforeException.class, () ->
                trainerTraineeService.assignTrainerToTrainee("traineeUser", "trainerUser")
        );

        verify(trainerTraineeRepository, never()).save(any(TrainerTrainee.class));
    }

    @Test
    void getAllNotAssignedTrainers_TraineeNotFound() {
        when(traineeRepository.existsTraineeByUser_Username(anyString())).thenReturn(false);

        assertThrows(TraineeNotFoundException.class, () ->
                trainerTraineeService.getAllNotAssignedTrainers("unknownTrainee")
        );

        verify(trainerTraineeRepository, never()).findByUsernameNotAssignedToTrainee(anyString());
    }

    @Test
    void getAllNotAssignedTrainers_Success() {
        when(traineeRepository.existsTraineeByUser_Username(anyString())).thenReturn(true);
        when(trainerTraineeRepository.findByUsernameNotAssignedToTrainee(anyString())).thenReturn(List.of(trainer));
        when(trainerMapper.toTrainerResponseDTO(any(Trainer.class))).thenReturn(new TrainerResponseDTO());

        List<TrainerResponseDTO> result = trainerTraineeService.getAllNotAssignedTrainers("validTrainee");

        assertNotNull(result);
        assertFalse(result.isEmpty());

        verify(trainerTraineeRepository, times(1)).findByUsernameNotAssignedToTrainee("validTrainee");
    }

    @Test
    void updateTraineeTrainer_Success() {
        when(trainerRepository.findTraineeByUser_Username(anyString())).thenReturn(Optional.of(trainer));
        when(traineeRepository.findTraineeByUser_Username(anyString())).thenReturn(Optional.of(trainee));

        List<String> trainerUsernames = List.of("trainer1", "trainer2");

        when(trainerMapper.toTrainerResponseDTO(any())).thenReturn(new TrainerResponseDTO());

        List<TrainerResponseDTO> result = trainerTraineeService.updateTraineeTrainer("validTrainee", trainerUsernames);

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(trainerTraineeRepository, times(1)).removeTrainerTraineeByTrainee_User_Username("validTrainee");
        verify(trainerTraineeRepository, times(2)).save(any(TrainerTrainee.class));
    }
}
