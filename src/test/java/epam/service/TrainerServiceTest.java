package epam.service;

import epam.dto.request_dto.TrainerRequestDTO;
import epam.dto.response_dto.RegisterTrainerResponseDTO;
import epam.dto.response_dto.TrainerResponseDTO;
import epam.dto.response_dto.TrainingResponseDTO;
import epam.entity.Trainer;
import epam.entity.Training;
import epam.entity.TrainingType;
import epam.entity.User;
import epam.exception.exception.TrainerNotFoundException;
import epam.mapper.TrainerMapper;
import epam.mapper.TrainingMapper;
import epam.repository.TrainerRepository;
import epam.service.impl.TrainerServiceImpl;
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

import static epam.controller.TrainerControllerTest.settings;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        when(trainerRepository.save(any())).thenReturn(trainer);
        when(trainerMapper.toRegisterTrainerResponseDTO(any())).thenReturn(registerTrainerResponseDTO);

        RegisterTrainerResponseDTO response = trainerService.createTrainer(trainerRequestDTO);

        assertNotNull(response);
        verify(trainerRepository).save(any(Trainer.class));
    }

    @Test
    void testUpdateTrainer_Success() {
        when(trainerRepository.findTraineeByUser_Username(anyString())).thenReturn(Optional.of(trainer));
        when(trainingTypeService.getTrainingByTrainingName(anyString())).thenReturn(Instancio.create(TrainingType.class));
        when(trainerMapper.toTrainerResponseDTO(any())).thenReturn(trainerResponseDTO);

        TrainerResponseDTO response = trainerService.updateTrainer("testTrainer", trainerRequestDTO);

        assertNotNull(response);
        verify(trainerRepository).findTraineeByUser_Username(anyString());
    }

    @Test
    void testUpdateTrainer_NotFound() {
        when(trainerRepository.findTraineeByUser_Username(anyString())).thenReturn(Optional.empty());

        assertThrows(TrainerNotFoundException.class, () -> trainerService.updateTrainer("testTrainer", trainerRequestDTO));
    }

    @Test
    void testDeleteTrainer_NotFound() {
        when(userService.existsByUsername(anyString())).thenReturn(false);

        assertThrows(TrainerNotFoundException.class, () -> trainerService.deleteTrainer("testTrainer"));
    }

    @Test
    void testGetTrainerByUsername_Success() {
        when(trainerRepository.findTraineeByUser_Username(anyString())).thenReturn(Optional.of(trainer));
        when(trainerMapper.toTrainerResponseDTO(any())).thenReturn(trainerResponseDTO);

        TrainerResponseDTO response = trainerService.getTrainerByUsername("testTrainer");

        assertNotNull(response);
    }

    @Test
    void testGetTrainerByUsername_NotFound() {
        when(trainerRepository.findTraineeByUser_Username(anyString())).thenReturn(Optional.empty());

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
        when(trainerRepository.findTraineeByUser_Username(anyString())).thenReturn(Optional.of(trainer));

        trainerService.updateTrainerStatus("testTrainer", false);

        assertFalse(trainer.getUser().getIsActive());
    }

    @Test
    void testUpdateTrainerStatus_NotFound() {
        when(trainerRepository.findTraineeByUser_Username(anyString())).thenReturn(Optional.empty());

        assertThrows(TrainerNotFoundException.class, () -> trainerService.updateTrainerStatus("testTrainer", true));
    }
}
