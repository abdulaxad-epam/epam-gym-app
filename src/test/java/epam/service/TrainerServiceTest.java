package epam.service;

import epam.client.TrainingClient;
import epam.dto.request_dto.TrainerRequestDTO;
import epam.dto.response_dto.RegisterTrainerResponseDTO;
import epam.dto.response_dto.TrainerResponseDTO;
import epam.entity.Trainer;
import epam.entity.TrainingType;
import epam.entity.User;
import epam.exception.exception.TrainerNotFoundException;
import epam.mapper.TrainerMapper;
import epam.mapper.TrainingMapper;
import epam.repository.TrainerRepository;
import epam.service.impl.TrainerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TrainerServiceTest {

    private TrainerRepository trainerRepository;
    private TrainingTypeService trainingTypeService;
    private TrainerMapper trainerMapper;
    private UserService userService;
    private TrainerServiceImpl trainerService;
    private TrainingClient trainingClient;

    @BeforeEach
    void setUp() {
        trainerRepository = mock(TrainerRepository.class);
        trainingTypeService = mock(TrainingTypeService.class);
        trainerMapper = mock(TrainerMapper.class);
        TrainingMapper trainingMapper = mock(TrainingMapper.class);
        userService = mock(UserService.class);
        trainerService = new TrainerServiceImpl(
                trainerRepository, trainingTypeService, trainerMapper,trainingClient, trainingMapper, userService
        );
    }

    @Test
    void createTrainer_shouldSaveAndReturnResponse() {
        TrainerRequestDTO requestDTO = new TrainerRequestDTO();
        requestDTO.setSpecialization("Yoga");

        TrainingType trainingType = new TrainingType();
        Trainer trainer = new Trainer();
        RegisterTrainerResponseDTO responseDTO = new RegisterTrainerResponseDTO();

        when(trainingTypeService.getTrainingByTrainingName("Yoga")).thenReturn(trainingType);
        when(trainerMapper.toTrainer(requestDTO, trainingType)).thenReturn(trainer);
        when(trainerRepository.save(trainer)).thenReturn(trainer);
        when(trainerMapper.toRegisterTrainerResponseDTO(trainer)).thenReturn(responseDTO);

        RegisterTrainerResponseDTO result = trainerService.createTrainer(requestDTO);

        assertNotNull(result);
        verify(trainerRepository).save(trainer);
    }

    @Test
    void updateTrainer_shouldUpdateAndReturnDTO() {
        Authentication auth = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);
        when(auth.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("trainerUser");

        TrainerRequestDTO requestDTO = new TrainerRequestDTO();
        requestDTO.setSpecialization("Pilates");

        TrainingType trainingType = new TrainingType();
        Trainer trainer = new Trainer();
        trainer.setUser(new User());

        when(trainerRepository.findTraineeByUser_Username("trainerUser")).thenReturn(Optional.of(trainer));
        when(trainingTypeService.getTrainingByTrainingName("Pilates")).thenReturn(trainingType);
        when(trainerMapper.toTrainerResponseDTO(trainer)).thenReturn(new TrainerResponseDTO());

        TrainerResponseDTO result = trainerService.updateTrainer(auth, requestDTO);

        assertNotNull(result);
        verify(trainerRepository).findTraineeByUser_Username("trainerUser");
        verify(trainingTypeService).getTrainingByTrainingName("Pilates");
    }

    @Test
    void updateTrainer_shouldThrowException_whenTrainerNotFound() {
        Authentication auth = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);
        when(auth.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("missingTrainer");

        when(trainerRepository.findTraineeByUser_Username("missingTrainer")).thenReturn(Optional.empty());

        assertThrows(TrainerNotFoundException.class, () -> trainerService.updateTrainer(auth, new TrainerRequestDTO()));
    }

    @Test
    void deleteTrainer_shouldDelete_whenExists() {
        Authentication auth = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);
        when(auth.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("trainer123");

        when(userService.existsByUsername("trainer123")).thenReturn(true);

        assertThrows(TrainerNotFoundException.class, () -> trainerService.deleteTrainer(auth));

        verify(trainerRepository).deleteTrainerByUser_Username("trainer123");
    }

    @Test
    void getTrainerByUsername_shouldReturnDTO_whenFound() {
        Authentication auth = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);
        Trainer trainer = new Trainer();

        when(auth.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("trainerX");
        when(trainerRepository.findTraineeByUser_Username("trainerX")).thenReturn(Optional.of(trainer));
        when(trainerMapper.toTrainerResponseDTO(trainer)).thenReturn(new TrainerResponseDTO());

        TrainerResponseDTO result = trainerService.getTrainerByUsername(auth);

        assertNotNull(result);
        verify(trainerRepository).findTraineeByUser_Username("trainerX");
    }

    @Test
    void getTrainerByUsername_shouldThrowException_whenNotFound() {
        Authentication auth = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);
        when(auth.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("notFound");

        when(trainerRepository.findTraineeByUser_Username("notFound")).thenReturn(Optional.empty());

        assertThrows(TrainerNotFoundException.class, () -> trainerService.getTrainerByUsername(auth));
    }

    @Test
    void updateTrainerStatus_shouldUpdate_whenFound() {
        Authentication auth = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);
        Trainer trainer = new Trainer();
        User user = new User();
        trainer.setUser(user);

        when(auth.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("trainerX");
        when(trainerRepository.findTraineeByUser_Username("trainerX")).thenReturn(Optional.of(trainer));

        trainerService.updateTrainerStatus(auth, true);

        assertTrue(trainer.getUser().getIsActive());
    }

    @Test
    void updateTrainerStatus_shouldThrowException_whenNotFound() {
        Authentication auth = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);

        when(auth.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("ghost");
        when(trainerRepository.findTraineeByUser_Username("ghost")).thenReturn(Optional.empty());

        assertThrows(TrainerNotFoundException.class, () -> trainerService.updateTrainerStatus(auth, true));
    }
}
