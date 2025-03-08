package epam.facade;

import epam.request_dto.AuthenticateRequestDTO;
import epam.request_dto.ChangePasswordRequestDTO;
import epam.request_dto.RegisterTraineeRequestDTO;
import epam.request_dto.RegisterTrainerRequestDTO;
import epam.request_dto.TraineeRequestDTO;
import epam.request_dto.TrainerRequestDTO;
import epam.request_dto.TrainingRequestDTO;
import epam.response_dto.TraineeResponseDTO;
import epam.response_dto.TrainerResponseDTO;
import epam.response_dto.TrainingResponseDTO;
import epam.service.AuthenticationService;
import epam.service.TraineeService;
import epam.service.TraineeTrainerService;
import epam.service.TrainerService;
import epam.service.TrainingService;
import epam.service.TrainingTypeService;
import epam.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    private String username;
    private RegisterTraineeRequestDTO registerTraineeRequest;
    private RegisterTrainerRequestDTO registerTrainerRequest;
    private AuthenticateRequestDTO authenticateRequest;
    private ChangePasswordRequestDTO changePasswordRequest;
    private TraineeRequestDTO traineeRequest;
    private TrainerRequestDTO trainerRequest;
    private TrainingRequestDTO trainingRequest;

    @BeforeEach
    void setUp() {
        username = "testuser";

        registerTraineeRequest = RegisterTraineeRequestDTO.builder().build();
        registerTrainerRequest = RegisterTrainerRequestDTO.builder().build();
        authenticateRequest = AuthenticateRequestDTO.builder().build();
        changePasswordRequest = ChangePasswordRequestDTO.builder().build();
        traineeRequest = TraineeRequestDTO.builder().build();
        trainerRequest = TrainerRequestDTO.builder().build();
        trainingRequest = TrainingRequestDTO.builder().build();
    }

    @Test
    void testRegisterTrainee() {
        when(authenticationService.register(registerTraineeRequest)).thenReturn(true);
        assertTrue(trainingFacade.register(registerTraineeRequest));
        verify(authenticationService).register(registerTraineeRequest);
    }

    @Test
    void testRegisterTrainer() {
        when(authenticationService.register(registerTrainerRequest)).thenReturn(true);
        assertTrue(trainingFacade.register(registerTrainerRequest));
        verify(authenticationService).register(registerTrainerRequest);
    }

    @Test
    void testAuthenticate() {
        when(authenticationService.authenticate(authenticateRequest)).thenReturn(true);
        assertTrue(trainingFacade.authenticate(authenticateRequest));
        verify(authenticationService).authenticate(authenticateRequest);
    }

    @Test
    void testChangePassword() {
        when(userService.changePassword(changePasswordRequest)).thenReturn(true);
        assertTrue(trainingFacade.changePassword(changePasswordRequest));
        verify(userService).changePassword(changePasswordRequest);
    }

    @Test
    void testCreateTrainee() {
        TraineeResponseDTO response = TraineeResponseDTO.builder().build();
        when(traineeService.createTrainee(traineeRequest)).thenReturn(response);
        assertEquals(response, trainingFacade.createTrainee(traineeRequest));
        verify(traineeService).createTrainee(traineeRequest);
    }

    @Test
    void testDeleteTrainee() {
        doNothing().when(traineeService).deleteTrainee(username);
        trainingFacade.deleteTrainee(username);
        verify(traineeService).deleteTrainee(username);
    }

    @Test
    void testCreateTrainer() {
        TrainerResponseDTO response = TrainerResponseDTO.builder().build();
        when(trainerService.createTrainer(trainerRequest)).thenReturn(response);
        assertEquals(response, trainingFacade.createTrainer(trainerRequest));
        verify(trainerService).createTrainer(trainerRequest);
    }

    @Test
    void testDeleteTrainer() {
        doNothing().when(trainerService).deleteTrainer(username);
        trainingFacade.deleteTrainer(username);
        verify(trainerService).deleteTrainer(username);
    }

    @Test
    void testCreateTraining() {
        TrainingResponseDTO response = TrainingResponseDTO.builder().build();
        when(trainingService.createTraining(trainingRequest)).thenReturn(response);
        assertEquals(response, trainingFacade.createTraining(trainingRequest));
        verify(trainingService).createTraining(trainingRequest);
    }

    @Test
    void testDeleteTraining() {
        doNothing().when(trainingService).deleteTraining(username);
        trainingFacade.deleteTraining(username);
        verify(trainingService).deleteTraining(username);
    }

    @Test
    void testGetAllTrainings() {
        List<TrainingResponseDTO> responseList = List.of(TrainingResponseDTO.builder().build());
        when(trainingService.getAllTrainings()).thenReturn(responseList);
        assertEquals(responseList, trainingFacade.getAllTrainings());
        verify(trainingService).getAllTrainings();
    }

    @Test
    void testExistsByUsernameAndPassword() {
        when(userService.existsByUsernameAndPassword(username, "password")).thenReturn(true);
        assertTrue(trainingFacade.existsByUsernameAndPassword(username, "password"));
        verify(userService).existsByUsernameAndPassword(username, "password");
    }

    @Test
    void testExistsByUsername() {
        when(userService.existsByUsername(username)).thenReturn(true);
        assertTrue(trainingFacade.existsByUsername(username));
        verify(userService).existsByUsername(username);
    }

    @Test
    void testGetTrainersByTraineeUsername() {
        List<TrainerResponseDTO> trainers = List.of(TrainerResponseDTO.builder().build());
        when(trainerService.getTrainersByTrainee(username)).thenReturn(trainers);
        assertEquals(trainers, trainingFacade.getTrainersByTraineeUsername(username));
        verify(trainerService).getTrainersByTrainee(username);
    }

    @Test
    void testAddTrainerToTrainee() {
        when(traineeTrainerService.addTrainerToTrainee(username, "trainerUser")).thenReturn(true);
        assertTrue(trainingFacade.addTrainerToTrainee(username, "trainerUser"));
        verify(traineeTrainerService).addTrainerToTrainee(username, "trainerUser");
    }

    @Test
    void testRemoveTrainerFromTrainee() {
        when(traineeTrainerService.removeTrainerFromTrainee(username, "trainerUser")).thenReturn(true);
        assertTrue(trainingFacade.removeTrainerFromTrainee(username, "trainerUser"));
        verify(traineeTrainerService).removeTrainerFromTrainee(username, "trainerUser");
    }

    @Test
    void testGetTraineesByTrainerUsername() {
        List<TraineeResponseDTO> trainees = List.of(TraineeResponseDTO.builder().build());
        when(traineeService.getTraineesByTrainer(username)).thenReturn(trainees);
        assertEquals(trainees, trainingFacade.getTraineesByTrainerUsername(username));
        verify(traineeService).getTraineesByTrainer(username);
    }

    @Test
    void testGetTrainingsByTraineeUsername() {
        List<TrainingResponseDTO> trainings = List.of(TrainingResponseDTO.builder().build());
        when(trainingService.getTrainingsByTraineeUsername(username)).thenReturn(trainings);
        assertEquals(trainings, trainingFacade.getTrainingsByTraineeUsername(username));
        verify(trainingService).getTrainingsByTraineeUsername(username);
    }

    @Test
    void testToggleStatus() {
        when(userService.toggleStatus(username)).thenReturn(true);
        assertTrue(trainingFacade.toggleStatus(username));
        verify(userService).toggleStatus(username);
    }

    @Test
    void testFindAllTrainingTypes() {
        List<String> types = List.of("Cardio", "Strength");
        when(trainingTypeService.findAll()).thenReturn(types);
        assertEquals(types, trainingFacade.findAllTrainingTypes());
        verify(trainingTypeService).findAll();
    }
}
