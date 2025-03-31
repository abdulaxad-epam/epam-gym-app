package epam.trainer;

import epam.shared.exception.exception.TrainerNotFoundException;
import epam.shared.security.dto.RegisterTrainerResponseDTO;
import epam.trainer.dto.TrainerRequestDTO;
import epam.trainer.dto.TrainerResponseDTO;
import epam.trainer.entity.Trainer;
import epam.trainer.mapper.TrainerMapper;
import epam.trainer.repository.TrainerRepository;
import epam.trainer.service.impl.TrainerServiceImpl;
import epam.training.dto.TrainingResponseDTO;
import epam.training.entity.Training;
import epam.training.mapper.TrainingMapper;
import epam.training_type.entity.TrainingType;
import epam.training_type.service.TrainingTypeService;
import epam.user.entity.User;
import epam.user.service.UserService;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static epam.trainer.TrainerControllerTest.settings;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrainerServiceTest {

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private TrainingTypeService trainingTypeService;

    @Mock
    private TrainerMapper trainerMapper;

    @Mock
    private TrainingMapper trainingMapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private TrainerServiceImpl trainerService;

    private Trainer trainer;
    private TrainerRequestDTO trainerRequestDTO;
    private TrainerResponseDTO trainerResponseDTO;
    private RegisterTrainerResponseDTO registerTrainerResponseDTO;
    private Training training;
    private TrainingResponseDTO trainingResponseDTO;

    @BeforeEach
    void setUp() {
        User user = Instancio.create(User.class);
        user.setUsername("testTrainer");
        user.setIsActive(true);

        trainer = new Trainer();
        trainer.setTrainerId(UUID.randomUUID());
        trainer.setUser(user);

        trainerRequestDTO = Instancio.of(TrainerRequestDTO.class)
                .withSettings(settings).create();

        trainerResponseDTO = new TrainerResponseDTO();

        registerTrainerResponseDTO = new RegisterTrainerResponseDTO();

        training = new Training();
        trainingResponseDTO = new TrainingResponseDTO();
    }

    @Test
    void testCreateTrainer_Success() {
        when(trainingTypeService.getTrainingByTrainingName(anyString())).thenReturn(Instancio.create(TrainingType.class));
        when(trainerMapper.toTrainer(any(), any())).thenReturn(trainer);
        when(trainerRepository.insert(any())).thenReturn(trainer);
        when(trainerMapper.toRegisterTrainerResponseDTO(any())).thenReturn(registerTrainerResponseDTO);

        RegisterTrainerResponseDTO response = trainerService.createTrainer(trainerRequestDTO);

        assertNotNull(response);
        verify(trainerRepository).insert(any(Trainer.class));
    }

    @Test
    void testUpdateTrainer_Success() {
        when(trainerRepository.findByUsername(anyString())).thenReturn(Optional.of(trainer));
        when(trainingTypeService.getTrainingByTrainingName(anyString())).thenReturn(Instancio.create(TrainingType.class));
        when(trainerMapper.toTrainerResponseDTO(any())).thenReturn(trainerResponseDTO);

        TrainerResponseDTO response = trainerService.updateTrainer("testTrainer", trainerRequestDTO);

        assertNotNull(response);
        verify(trainerRepository).findByUsername(anyString());
    }

    @Test
    void testUpdateTrainer_NotFound() {
        when(trainerRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        assertThrows(TrainerNotFoundException.class, () -> trainerService.updateTrainer("testTrainer", trainerRequestDTO));
    }

    @Test
    void testDeleteTrainer_NotFound() {
        when(userService.existsByUsername(anyString())).thenReturn(false);

        assertThrows(TrainerNotFoundException.class, () -> trainerService.deleteTrainer("testTrainer"));
    }

    @Test
    void testGetTrainerByUsername_Success() {
        when(trainerRepository.findByUsername(anyString())).thenReturn(Optional.of(trainer));
        when(trainerMapper.toTrainerResponseDTO(any())).thenReturn(trainerResponseDTO);

        TrainerResponseDTO response = trainerService.getTrainerByUsername("testTrainer");

        assertNotNull(response);
    }

    @Test
    void testGetTrainerByUsername_NotFound() {
        when(trainerRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        assertThrows(TrainerNotFoundException.class, () -> trainerService.getTrainerByUsername("testTrainer"));
    }

    @Test
    void testGetTrainerTrainings_Success() {
        when(trainerRepository.getTrainerTrainings(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(Optional.of(List.of(training)));
        when(trainingMapper.toTrainingResponseDTO(any())).thenReturn(trainingResponseDTO);

        List<TrainingResponseDTO> response = trainerService.getTrainerTrainings("testTrainer", "2024-01-01", "2024-12-31", "testTrainee");

        assertFalse(response.isEmpty());
    }

    @Test
    void testGetTrainerTrainings_NotFound() {
        when(trainerRepository.getTrainerTrainings(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(Optional.empty());

        assertThrows(TrainerNotFoundException.class, () ->
                trainerService.getTrainerTrainings("testTrainer", "2024-01-01", "2024-12-31", "testTrainee"));
    }

    @Test
    void testUpdateTrainerStatus_Success() {
        when(trainerRepository.findByUsername(anyString())).thenReturn(Optional.of(trainer));

        trainerService.updateTrainerStatus("testTrainer", false);

        assertFalse(trainer.getUser().getIsActive());
    }

    @Test
    void testUpdateTrainerStatus_NotFound() {
        when(trainerRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        assertThrows(TrainerNotFoundException.class, () -> trainerService.updateTrainerStatus("testTrainer", true));
    }
}
