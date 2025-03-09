package epam.service_impl;

import epam.entity.Trainee;
import epam.entity.Trainer;
import epam.entity.Training;
import epam.entity.TrainingType;
import epam.exception.TraineeNotFoundException;
import epam.exception.TrainerNotFoundException;
import epam.exception.TrainingNotFoundException;
import epam.mapper.TrainingMapper;
import epam.repository.TraineeRepository;
import epam.repository.TrainerRepository;
import epam.repository.TrainingRepository;
import epam.request_dto.TrainingRequestDTO;
import epam.response_dto.TraineeResponseDTO;
import epam.response_dto.TrainerResponseDTO;
import epam.response_dto.TrainingResponseDTO;
import epam.response_dto.UserResponseDTO;
import epam.service.TrainingService;
import epam.service.TrainingTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainingServiceImplTest {

    private TrainingRepository trainingRepository;
    private TrainingMapper trainingMapper;
    private TrainingTypeService trainingTypeService;
    private TraineeRepository traineeRepository;
    private TrainerRepository trainerRepository;
    private TrainingService trainingService;

    private TraineeResponseDTO traineeResponseDTO;
    private TrainerResponseDTO trainerResponseDTO;
    private TrainingRequestDTO trainingRequestDTO;
    private TrainingResponseDTO trainingResponseDTO;



    @BeforeEach
    public void setUp() {

        trainingRequestDTO = TrainingRequestDTO.builder()
                .trainerUsername("trainerUser")
                .traineeUsername("unknownTrainee")
                .trainingName("Strength Training")
                .trainingDate(LocalDateTime.now()) // Example date
                .trainingType("Strength")
                .trainingDuration(60)
                .build();

        traineeResponseDTO = TraineeResponseDTO.builder()
                .traineeDateOfBirth(LocalDateTime.of(1995, 5, 20, 0, 0))
                .address("123 Main Street")
                .user(UserResponseDTO.builder()
                        .firstName("John")
                        .lastName("Doe")
                        .username("unknownTrainee")
                        .password("password123")
                        .isActive(true)
                        .build())
                .build();

        trainerResponseDTO = TrainerResponseDTO.builder()
                .trainerSpecialization("Strength Training")
                .user(UserResponseDTO.builder()
                        .firstName("Alice")
                        .lastName("Smith")
                        .username("trainerUser")
                        .password("trainerPass")
                        .isActive(true)
                        .build())
                .build();

        trainingResponseDTO = TrainingResponseDTO.builder()
                .trainee(traineeResponseDTO)
                .trainer(trainerResponseDTO)
                .trainingName("Strength Training")
                .trainingDate(LocalDateTime.now())
                .trainingType("Strength")
                .trainingDuration(1) // Placeholder for duration
                .build();

        trainingRepository = mock(TrainingRepository.class);
        trainingMapper = mock(TrainingMapper.class);
        trainingTypeService = mock(TrainingTypeService.class);
        traineeRepository = mock(TraineeRepository.class);
        trainerRepository = mock(TrainerRepository.class);
        trainingService = new TrainingServiceImpl(trainingRepository, trainingMapper, trainingTypeService, traineeRepository, trainerRepository);
    }

    @Test
    void testCreateTraining_Success() {
        TrainingRequestDTO requestDTO = trainingRequestDTO;
        TrainingType trainingType = new TrainingType();
        Trainer trainer = new Trainer();
        Trainee trainee = new Trainee();
        Training training = new Training();
        TrainingResponseDTO responseDTO = trainingResponseDTO;

        when(trainingTypeService.getTrainingByTrainingName(requestDTO.getTrainingType())).thenReturn(trainingType);
        when(trainerRepository.findByUsername(requestDTO.getTrainerUsername())).thenReturn(Optional.of(trainer));
        when(traineeRepository.findByUsername(requestDTO.getTraineeUsername())).thenReturn(Optional.of(trainee));
        when(trainingMapper.toTraining(requestDTO, trainingType, trainer, trainee)).thenReturn(training);
        when(trainingRepository.insert(training)).thenReturn(training);
        when(trainingMapper.toTrainingResponseDTO(training)).thenReturn(responseDTO);

        TrainingResponseDTO result = trainingService.createTraining(requestDTO);

        assertNotNull(result);
        verify(trainingRepository, times(1)).insert(training);
    }

    @Test
    void testCreateTraining_TrainerNotFound() {
        TrainingRequestDTO requestDTO = trainingRequestDTO;

        when(trainerRepository.findByUsername(requestDTO.getTrainerUsername())).thenReturn(Optional.empty());

        assertThrows(TrainerNotFoundException.class, () -> trainingService.createTraining(requestDTO));
    }

    @Test
    void testCreateTraining_TraineeNotFound() {
        TrainingRequestDTO requestDTO = trainingRequestDTO;

        when(trainerRepository.findByUsername(requestDTO.getTrainerUsername())).thenReturn(Optional.of(new Trainer()));
        when(traineeRepository.findByUsername(requestDTO.getTraineeUsername())).thenReturn(Optional.empty());

        assertThrows(TraineeNotFoundException.class, () -> trainingService.createTraining(requestDTO));
    }

    @Test
    void testUpdateTraining_Success() {
        String username = "trainingUser";
        TrainingRequestDTO requestDTO = trainingRequestDTO;
        UUID trainingId = UUID.randomUUID();
        TrainingType trainingType = new TrainingType();
        Trainer trainer = new Trainer();
        Trainee trainee = new Trainee();
        Training updatedTraining = new Training();
        TrainingResponseDTO responseDTO = trainingResponseDTO;

        when(trainingRepository.getIdByUsername(username)).thenReturn(Optional.of(trainingId));
        when(trainingTypeService.getTrainingByTrainingName(requestDTO.getTrainingType())).thenReturn(trainingType);
        when(trainerRepository.findByUsername(requestDTO.getTrainerUsername())).thenReturn(Optional.of(trainer));
        when(traineeRepository.findByUsername(requestDTO.getTraineeUsername())).thenReturn(Optional.of(trainee));
        when(trainingMapper.toTraining(requestDTO, trainingType, trainer, trainee)).thenReturn(updatedTraining);
        when(trainingRepository.update(trainingId, updatedTraining)).thenReturn(updatedTraining);
        when(trainingMapper.toTrainingResponseDTO(updatedTraining)).thenReturn(responseDTO);

        TrainingResponseDTO result = trainingService.updateTraining(username, requestDTO);

        assertNotNull(result);
        verify(trainingRepository, times(1)).update(trainingId, updatedTraining);
    }

    @Test
    void testUpdateTraining_TrainingNotFound() {
        String username = "unknownTraining";
        TrainingRequestDTO requestDTO = trainingRequestDTO;

        when(trainingRepository.getIdByUsername(username)).thenReturn(Optional.empty());

        assertThrows(TrainingNotFoundException.class, () -> trainingService.updateTraining(username, requestDTO));
    }

    @Test
    void testGetTrainingByUsername_Success() {
        String username = "trainingUser";
        UUID trainingId = UUID.randomUUID();
        Training training = new Training();
        TrainingResponseDTO responseDTO = trainingResponseDTO;

        when(trainingRepository.getIdByUsername(username)).thenReturn(Optional.of(trainingId));
        when(trainingRepository.findById(trainingId)).thenReturn(training);
        when(trainingMapper.toTrainingResponseDTO(training)).thenReturn(responseDTO);

        TrainingResponseDTO result = trainingService.getTrainingByUsername(username);

        assertNotNull(result);
    }

    @Test
    void testGetTrainingByUsername_NotFound() {
        String username = "unknownTraining";

        when(trainingRepository.getIdByUsername(username)).thenReturn(Optional.empty());

        assertThrows(TrainingNotFoundException.class, () -> trainingService.getTrainingByUsername(username));
    }

    @Test
    void testDeleteTraining_Success() {
        String username = "trainingUser";
        UUID trainingId = UUID.randomUUID();

        when(trainingRepository.getIdByUsername(username)).thenReturn(Optional.of(trainingId));
        doNothing().when(trainingRepository).delete(trainingId);

        trainingService.deleteTraining(username);

        verify(trainingRepository, times(1)).delete(trainingId);
    }

    @Test
    void testGetAllTrainings_Success() {
        List<Training> trainings = List.of(new Training(), new Training());
        List<TrainingResponseDTO> responseDTOs = List.of(trainingResponseDTO, trainingResponseDTO);

        when(trainingRepository.findAll()).thenReturn(trainings);
        when(trainingMapper.toTrainingResponseDTO(any())).thenReturn(responseDTOs.get(0), responseDTOs.get(1));

        List<TrainingResponseDTO> result = trainingService.getAllTrainings();

        assertEquals(2, result.size());
    }

    @Test
    void testGetTrainingsByTraineeUsername_Success() {
        String traineeUsername = "traineeUser";
        List<Training> trainings = List.of(new Training(), new Training());
        List<TrainingResponseDTO> responseDTOs = List.of(trainingResponseDTO, trainingResponseDTO);

        when(trainingRepository.findTrainingsByTrainee(traineeUsername)).thenReturn(trainings);
        when(trainingMapper.toTrainingResponseDTO(any())).thenReturn(responseDTOs.get(0), responseDTOs.get(1));

        List<TrainingResponseDTO> result = trainingService.getTrainingsByTraineeUsername(traineeUsername);

        assertEquals(2, result.size());
    }
}
