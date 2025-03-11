package epam.service;

import epam.dto.request_dto.TrainerRequestDTO;
import epam.dto.response_dto.TrainerResponseDTO;
import epam.entity.Trainer;
import epam.entity.TrainingType;
import epam.exception.TrainerNotFoundException;
import epam.mapper.TrainerMapper;
import epam.repository.TrainerRepository;
import epam.service.impl.TrainerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TrainerServiceImplTest {

    private TrainerRepository trainerRepository;
    private TrainingTypeService trainingTypeService;
    private TrainerMapper trainerMapper;
    private UserService userService;
    private TrainerService trainerService;

    @BeforeEach
    void setUp() {
        trainerRepository = mock(TrainerRepository.class);
        trainingTypeService = mock(TrainingTypeService.class);
        trainerMapper = mock(TrainerMapper.class);
        userService = mock(UserService.class);

        trainerService = new TrainerServiceImpl(trainerRepository, trainingTypeService, trainerMapper, userService);
    }

    @Test
    void testCreateTrainer_Success() {
        TrainerRequestDTO requestDTO = TrainerRequestDTO.builder().specialization("Fitness").build();
        TrainingType trainingType = new TrainingType();
        Trainer trainer = new Trainer();
        Trainer savedTrainer = new Trainer();
        TrainerResponseDTO responseDTO = TrainerResponseDTO.builder().trainerSpecialization("Fitness").build();

        when(trainingTypeService.getTrainingByTrainingName("Fitness")).thenReturn(trainingType);
        when(trainerMapper.toTrainer(requestDTO, trainingType)).thenReturn(trainer);
        when(trainerRepository.insert(trainer)).thenReturn(savedTrainer);
        when(trainerMapper.toTrainerResponseDTO(savedTrainer)).thenReturn(responseDTO);

        TrainerResponseDTO result = trainerService.createTrainer(requestDTO);

        assertNotNull(result);
        assertEquals("Fitness", result.getTrainerSpecialization());
    }

    @Test
    void testUpdateTrainer_NotFound() {
        String username = "unknownTrainer";
        when(trainerRepository.getIdByUsername(username)).thenReturn(Optional.empty());

        assertThrows(TrainerNotFoundException.class, () -> trainerService.updateTrainer(username, TrainerRequestDTO.builder().build()));
    }

    @Test
    void testDeleteTrainer_Success() {
        String username = "trainerUser";

        when(userService.existsByUsername(username)).thenReturn(true);
        doNothing().when(trainerRepository).deleteTrainerByUsername(username);

        assertDoesNotThrow(() -> trainerService.deleteTrainer(username));
    }

    @Test
    void testDeleteTrainer_NotFound() {
        String username = "unknownTrainer";

        when(userService.existsByUsername(username)).thenReturn(false);

        assertThrows(TrainerNotFoundException.class, () -> trainerService.deleteTrainer(username));
    }

    @Test
    void testGetTrainerByUsername_Success() {
        String username = "trainerUser";
        Trainer trainer = new Trainer();
        TrainerResponseDTO responseDTO = TrainerResponseDTO.builder().trainerSpecialization("Fitness").build();

        when(trainerRepository.findByUsername(username)).thenReturn(Optional.of(trainer));
        when(trainerMapper.toTrainerResponseDTO(trainer)).thenReturn(responseDTO);

        TrainerResponseDTO result = trainerService.getTrainerByUsername(username);

        assertNotNull(result);
        assertEquals("Fitness", result.getTrainerSpecialization());
    }

    @Test
    void testGetTrainerByUsername_NotFound() {
        String username = "unknownTrainer";
        when(trainerRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(TrainerNotFoundException.class, () -> trainerService.getTrainerByUsername(username));
    }

    @Test
    void testGetAllTrainers() {
        Trainer trainer1 = new Trainer();
        Trainer trainer2 = new Trainer();
        List<Trainer> trainerList = List.of(trainer1, trainer2);

        when(trainerRepository.findAll()).thenReturn(trainerList);
        when(trainerMapper.toTrainerResponseDTO(any(Trainer.class)))
                .thenReturn(TrainerResponseDTO.builder().build(), TrainerResponseDTO.builder().build());

        List<TrainerResponseDTO> result = trainerService.getAllTrainers();

        assertEquals(2, result.size());
    }

    @Test
    void testGetTrainersByTrainee() {
        String traineeUsername = "trainee123";
        Trainer trainer1 = new Trainer();
        Trainer trainer2 = new Trainer();
        List<Trainer> trainers = List.of(trainer1, trainer2);

        when(trainerRepository.findTrainersByTrainee(traineeUsername)).thenReturn(trainers);
        when(trainerMapper.toTrainerResponseDTO(any(Trainer.class)))
                .thenReturn(TrainerResponseDTO.builder().build(), TrainerResponseDTO.builder().build());

        List<TrainerResponseDTO> result = trainerService.getTrainersByTrainee(traineeUsername);

        assertEquals(2, result.size());
    }
}
