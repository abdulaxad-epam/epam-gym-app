package epam.facade;

import epam.shared.facade.TrainingFacadeImpl;
import epam.shared.security.dto.AuthenticateRequestDTO;
import epam.shared.security.dto.ChangePasswordRequestDTO;
import epam.shared.security.dto.RegisterTraineeRequestDTO;
import epam.shared.security.dto.RegisterTrainerRequestDTO;
import epam.shared.security.service.AuthenticationService;
import epam.trainee.dto.TraineeRequestDTO;
import epam.trainee.dto.TraineeResponseDTO;
import epam.trainee.service.TraineeService;
import epam.shared.trainee_trainer.service.TraineeTrainerService;
import epam.trainer.dto.TrainerRequestDTO;
import epam.trainer.dto.TrainerResponseDTO;
import epam.trainer.service.TrainerService;
import epam.training.dto.TrainingRequestDTO;
import epam.training.dto.TrainingResponseDTO;
import epam.training.service.TrainingService;
import epam.training_type.service.TrainingTypeService;
import epam.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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


}
