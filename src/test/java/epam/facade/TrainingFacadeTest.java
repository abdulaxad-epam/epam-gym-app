package epam.facade;

import epam.dto.request_dto.*;
import epam.dto.response_dto.*;
import epam.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrainingFacadeTest {

    @Mock
    private TraineeService traineeService;

    @Mock
    private TrainerService trainerService;

    @Mock
    private TrainingService trainingService;

    @Mock
    private TrainingTypeService trainingTypeService;

    @Mock
    private UserService userService;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private TraineeTrainerService traineeTrainerService;

    @InjectMocks
    private TrainingFacadeImpl trainingFacade;

    private RegisterTraineeRequestDTO registerTraineeRequestDTO;
    private RegisterTrainerRequestDTO registerTrainerRequestDTO;
    private AuthenticateRequestDTO authenticateRequestDTO;
    private TraineeRequestDTO traineeRequestDTO;
    private TrainerRequestDTO trainerRequestDTO;
    private TrainingRequestDTO trainingRequestDTO;

    @BeforeEach
    void setUp() {
        registerTraineeRequestDTO = RegisterTraineeRequestDTO.builder().build();
        registerTrainerRequestDTO = RegisterTrainerRequestDTO.builder().build();
        authenticateRequestDTO = AuthenticateRequestDTO.builder().build();
        traineeRequestDTO = TraineeRequestDTO.builder().build();
        trainerRequestDTO = TrainerRequestDTO.builder().build();
        trainingRequestDTO = TrainingRequestDTO.builder().build();
    }

    @Test
    void testRegisterTrainee() {
        when(authenticationService.register(registerTraineeRequestDTO)).thenReturn(true);
        assertTrue(trainingFacade.register(registerTraineeRequestDTO));
        verify(authenticationService).register(registerTraineeRequestDTO);
    }

    @Test
    void testRegisterTrainer() {
        when(authenticationService.register(registerTrainerRequestDTO)).thenReturn(true);
        assertTrue(trainingFacade.register(registerTrainerRequestDTO));
        verify(authenticationService).register(registerTrainerRequestDTO);
    }

    @Test
    void testAuthenticateUser() {
        when(authenticationService.authenticate(authenticateRequestDTO)).thenReturn(true);
        assertTrue(trainingFacade.authenticate(authenticateRequestDTO));
        verify(authenticationService).authenticate(authenticateRequestDTO);
    }

    @Test
    void testChangePassword() {
        ChangePasswordRequestDTO changePasswordRequestDTO = ChangePasswordRequestDTO.builder().build();
        when(userService.changePassword(changePasswordRequestDTO)).thenReturn(true);
        assertTrue(trainingFacade.changePassword(changePasswordRequestDTO));
        verify(userService).changePassword(changePasswordRequestDTO);
    }

    @Test
    void testCreateTrainee() {
        TraineeResponseDTO response = TraineeResponseDTO.builder().build();
        when(traineeService.createTrainee(traineeRequestDTO)).thenReturn(response);
        assertEquals(response, trainingFacade.createTrainee(traineeRequestDTO));
        verify(traineeService).createTrainee(traineeRequestDTO);
    }

    @Test
    void testGetAllTrainees() {
        List<TraineeResponseDTO> trainees = List.of(TraineeResponseDTO.builder().build());
        when(traineeService.getAllTrainees()).thenReturn(trainees);
        assertEquals(trainees, trainingFacade.getAllTrainees());
    }

    @Test
    void testGetAllTrainees_EmptyList() {
        when(traineeService.getAllTrainees()).thenReturn(Collections.emptyList());
        assertTrue(trainingFacade.getAllTrainees().isEmpty());
    }

    @Test
    void testCreateTrainer() {
        TrainerResponseDTO response = TrainerResponseDTO.builder().build();
        when(trainerService.createTrainer(trainerRequestDTO)).thenReturn(response);
        assertEquals(response, trainingFacade.createTrainer(trainerRequestDTO));
        verify(trainerService).createTrainer(trainerRequestDTO);
    }

    @Test
    void testGetAllTrainers() {
        List<TrainerResponseDTO> trainers = List.of(TrainerResponseDTO.builder().build());
        when(trainerService.getAllTrainers()).thenReturn(trainers);
        assertEquals(trainers, trainingFacade.getAllTrainers());
    }

    @Test
    void testCreateTraining() {
        TrainingResponseDTO response = TrainingResponseDTO.builder().build();
        when(trainingService.createTraining(trainingRequestDTO)).thenReturn(response);
        assertEquals(response, trainingFacade.createTraining(trainingRequestDTO));
    }

    @Test
    void testGetAllTrainings() {
        List<TrainingResponseDTO> trainings = List.of(TrainingResponseDTO.builder().build());
        when(trainingService.getAllTrainings()).thenReturn(trainings);
        assertEquals(trainings, trainingFacade.getAllTrainings());
    }

    @Test
    void testExistsByUsernameAndPassword() {
        when(userService.existsByUsernameAndPassword("user", "pass")).thenReturn(true);
        assertTrue(trainingFacade.existsByUsernameAndPassword("user", "pass"));
    }

    @Test
    void testExistsByUsername() {
        when(userService.existsByUsername("user")).thenReturn(true);
        assertTrue(trainingFacade.existsByUsername("user"));
    }

    @Test
    void testFindAllTrainingTypes() {
        List<String> trainingTypes = List.of("Yoga", "Cardio");
        when(trainingTypeService.findAll()).thenReturn(trainingTypes);
        assertEquals(trainingTypes, trainingFacade.findAllTrainingTypes());
    }

    @Test
    void testGetTraineeTrainings() {
        LocalDate fromDate = LocalDate.of(2024, 1, 1);
        LocalDate toDate = LocalDate.of(2024, 12, 31);
        List<TrainingResponseDTO> trainings = List.of(TrainingResponseDTO.builder().build());
        when(trainingService.getTrainingsByUsernameAndCriteria("user", fromDate, toDate, "trainer", "Cardio"))
                .thenReturn(trainings);

        assertEquals(trainings, trainingFacade.getTraineeTrainings("user", fromDate, toDate, "trainer", "Cardio"));
    }

    @Test
    void testGetTrainerTrainings() {
        LocalDate fromDate = LocalDate.of(2024, 1, 1);
        LocalDate toDate = LocalDate.of(2024, 12, 31);
        List<TrainingResponseDTO> trainings = List.of(TrainingResponseDTO.builder().build());
        when(trainingService.getTrainingsByUsernameAndCriteria("trainer", fromDate, toDate, "trainee", "Yoga"))
                .thenReturn(trainings);

        assertEquals(trainings, trainingFacade.getTrainerTrainings("trainer", fromDate, toDate, "trainee", "Yoga"));
    }

    @Test
    void testToggleStatus() {
        when(userService.toggleStatus("user")).thenReturn(true);
        assertTrue(trainingFacade.toggleStatus("user"));
    }

    @Test
    void testAddTrainerToTrainee() {
        when(traineeTrainerService.addTrainerToTrainee("trainee", "trainer")).thenReturn(true);
        assertTrue(trainingFacade.addTrainerToTrainee("trainee", "trainer"));
    }

    @Test
    void testRemoveTrainerFromTrainee() {
        when(traineeTrainerService.removeTrainerFromTrainee("trainee", "trainer")).thenReturn(true);
        assertTrue(trainingFacade.removeTrainerFromTrainee("trainee", "trainer"));
    }

    @Test
    void testUpdateTrainer() {
        TrainerResponseDTO trainerResponse = TrainerResponseDTO.builder().build();
        when(trainerService.updateTrainer("trainer1", trainerRequestDTO)).thenReturn(trainerResponse);

        assertEquals(trainerResponse, trainingFacade.updateTrainer("trainer1", trainerRequestDTO));
        verify(trainerService).updateTrainer("trainer1", trainerRequestDTO);
    }

    @Test
    void testDeleteTrainer() {
        doNothing().when(trainerService).deleteTrainer("trainer1");

        assertDoesNotThrow(() -> trainingFacade.deleteTrainer("trainer1"));
        verify(trainerService).deleteTrainer("trainer1");
    }

    @Test
    void testGetTrainerByUsername() {
        TrainerResponseDTO trainerResponse = TrainerResponseDTO.builder().build();
        when(trainerService.getTrainerByUsername("trainer1")).thenReturn(trainerResponse);

        assertEquals(trainerResponse, trainingFacade.getTrainerByUsername("trainer1"));
        verify(trainerService).getTrainerByUsername("trainer1");
    }

    @Test
    void testUpdateTrainee() {
        TraineeResponseDTO traineeResponse = TraineeResponseDTO.builder().build();
        when(traineeService.updateTrainee("trainee1", traineeRequestDTO)).thenReturn(traineeResponse);

        assertEquals(traineeResponse, trainingFacade.updateTrainee("trainee1", traineeRequestDTO));
        verify(traineeService).updateTrainee("trainee1", traineeRequestDTO);
    }

    @Test
    void testDeleteTrainee() {
        doNothing().when(traineeService).deleteTrainee("trainee1");

        assertDoesNotThrow(() -> trainingFacade.deleteTrainee("trainee1"));
        verify(traineeService).deleteTrainee("trainee1");
    }

    @Test
    void testGetTraineeByUsername() {
        TraineeResponseDTO traineeResponse = TraineeResponseDTO.builder().build();
        when(traineeService.getTraineeByUsername("trainee1")).thenReturn(traineeResponse);

        assertEquals(traineeResponse, trainingFacade.getTraineeByUsername("trainee1"));
        verify(traineeService).getTraineeByUsername("trainee1");
    }

    @Test
    void testUpdateTraining() {
        TrainingResponseDTO trainingResponse = TrainingResponseDTO.builder().build();
        when(trainingService.updateTraining("training1", trainingRequestDTO)).thenReturn(trainingResponse);

        assertEquals(trainingResponse, trainingFacade.updateTraining("training1", trainingRequestDTO));
        verify(trainingService).updateTraining("training1", trainingRequestDTO);
    }

    @Test
    void testDeleteTraining() {
        doNothing().when(trainingService).deleteTraining("training1");

        assertDoesNotThrow(() -> trainingFacade.deleteTraining("training1"));
        verify(trainingService).deleteTraining("training1");
    }

    @Test
    void testGetTrainingByUsername() {
        TrainingResponseDTO trainingResponse = TrainingResponseDTO.builder().build();
        when(trainingService.getTrainingByUsername("training1")).thenReturn(trainingResponse);

        assertEquals(trainingResponse, trainingFacade.getTrainingByUsername("training1"));
        verify(trainingService).getTrainingByUsername("training1");
    }

    @Test
    void testGetTrainersByTraineeUsername() {
        List<TrainerResponseDTO> trainers = List.of(TrainerResponseDTO.builder().build());
        when(trainerService.getTrainersByTrainee("trainee1")).thenReturn(trainers);

        assertEquals(trainers, trainingFacade.getTrainersByTraineeUsername("trainee1"));
        verify(trainerService).getTrainersByTrainee("trainee1");
    }

    @Test
    void testGetTraineesByTrainerUsername() {
        List<TraineeResponseDTO> trainees = List.of(TraineeResponseDTO.builder().build());
        when(traineeService.getTraineesByTrainer("trainer1")).thenReturn(trainees);

        assertEquals(trainees, trainingFacade.getTraineesByTrainerUsername("trainer1"));
        verify(traineeService).getTraineesByTrainer("trainer1");
    }

    @Test
    void testGetTrainingsByTraineeUsername() {
        List<TrainingResponseDTO> trainings = List.of(TrainingResponseDTO.builder().build());
        when(trainingService.getTrainingsByTraineeUsername("trainee1")).thenReturn(trainings);

        assertEquals(trainings, trainingFacade.getTrainingsByTraineeUsername("trainee1"));
        verify(trainingService).getTrainingsByTraineeUsername("trainee1");
    }

}
