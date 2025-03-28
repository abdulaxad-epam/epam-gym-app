package epam.service;

import epam.shared.security.dto.AuthenticateRequestDTO;
import epam.shared.security.dto.RegisterTraineeRequestDTO;
import epam.shared.security.dto.RegisterTrainerRequestDTO;
import epam.shared.security.service.impl.AuthenticationServiceImpl;
import epam.trainee.service.TraineeService;
import epam.trainer.service.TrainerService;
import epam.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthenticationServiceImplTest {

    private UserService userService;
    private TrainerService trainerService;
    private TraineeService traineeService;
    private AuthenticationServiceImpl authenticationService;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        trainerService = mock(TrainerService.class);
        traineeService = mock(TraineeService.class);
        authenticationService = new AuthenticationServiceImpl(userService, trainerService, traineeService);
    }



}
