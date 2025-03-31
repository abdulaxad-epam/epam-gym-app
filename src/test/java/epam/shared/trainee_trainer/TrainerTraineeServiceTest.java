package epam.shared.trainee_trainer;

import epam.shared.exception.exception.TraineeHasAssignedBeforeException;
import epam.shared.exception.exception.TraineeNotFoundException;
import epam.shared.trainee_trainer.entity.TrainerTrainee;
import epam.shared.trainee_trainer.repository.TrainerTraineeRepository;
import epam.shared.trainee_trainer.service.impl.TrainerTraineeServiceImpl;
import epam.trainee.entity.Trainee;
import epam.trainee.repository.TraineeRepository;
import epam.trainer.dto.TrainerResponseDTO;
import epam.trainer.entity.Trainer;
import epam.trainer.mapper.TrainerMapper;
import epam.trainer.repository.TrainerRepository;
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
        when(traineeRepository.findByUsername(anyString())).thenReturn(Optional.of(trainee));
        when(trainerRepository.findByUsername(anyString())).thenReturn(Optional.of(trainer));
        when(trainerTraineeRepository.trainerHasTrainee(any(), any())).thenReturn(false);

        assertDoesNotThrow(() -> trainerTraineeService.assignTrainerToTrainee("traineeUser", "trainerUser"));

        verify(trainerTraineeRepository, times(1)).assignTrainerToTrainee(any(TrainerTrainee.class));
    }

    @Test
    void assignTrainerToTrainee_TraineeAlreadyAssigned() {
        when(traineeRepository.findByUsername(anyString())).thenReturn(Optional.of(trainee));
        when(trainerRepository.findByUsername(anyString())).thenReturn(Optional.of(trainer));
        when(trainerTraineeRepository.trainerHasTrainee(any(), any())).thenReturn(true);

        assertThrows(TraineeHasAssignedBeforeException.class, () ->
                trainerTraineeService.assignTrainerToTrainee("traineeUser", "trainerUser")
        );

        verify(trainerTraineeRepository, never()).assignTrainerToTrainee(any(TrainerTrainee.class));
    }

    @Test
    void getAllNotAssignedTrainers_TraineeNotFound() {
        when(traineeRepository.existsByUsername(anyString())).thenReturn(false);

        assertThrows(TraineeNotFoundException.class, () ->
                trainerTraineeService.getAllNotAssignedTrainers("unknownTrainee")
        );

        verify(trainerTraineeRepository, never()).findByUsernameNotAssignedToTrainee(anyString());
    }

    @Test
    void getAllNotAssignedTrainers_Success() {
        when(traineeRepository.existsByUsername(anyString())).thenReturn(true);
        when(trainerTraineeRepository.findByUsernameNotAssignedToTrainee(anyString())).thenReturn(List.of(trainer));
        when(trainerMapper.toTrainerResponseDTO(any(Trainer.class))).thenReturn(new TrainerResponseDTO());

        List<TrainerResponseDTO> result = trainerTraineeService.getAllNotAssignedTrainers("validTrainee");

        assertNotNull(result);
        assertFalse(result.isEmpty());

        verify(trainerTraineeRepository, times(1)).findByUsernameNotAssignedToTrainee("validTrainee");
    }

    @Test
    void updateTraineeTrainer_Success() {
        when(trainerRepository.findByUsername(anyString())).thenReturn(Optional.of(trainer));
        when(traineeRepository.findByUsername(anyString())).thenReturn(Optional.of(trainee));

        List<String> trainerUsernames = List.of("trainer1", "trainer2");

        when(trainerMapper.toTrainerResponseDTO(any())).thenReturn(new TrainerResponseDTO());

        List<TrainerResponseDTO> result = trainerTraineeService.updateTraineeTrainer("validTrainee", trainerUsernames);

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(trainerTraineeRepository, times(1)).removeAllByTraineeUsername("validTrainee");
        verify(trainerTraineeRepository, times(2)).assignTrainerToTrainee(any(TrainerTrainee.class));
    }
}
