package epam.shared.security.service.impl;

import epam.shared.security.dto.AuthenticateRequestDTO;
import epam.shared.security.dto.RegisterTraineeRequestDTO;
import epam.shared.security.dto.RegisterTrainerRequestDTO;
import epam.trainee.dto.TraineeRequestDTO;
import epam.trainee.service.TraineeService;
import epam.trainer.dto.TrainerRequestDTO;
import epam.trainer.service.TrainerService;
import epam.user.service.UserService;
import epam.shared.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final TrainerService trainerService;
    private final TraineeService traineeService;

    @Override
    public Boolean register(RegisterTraineeRequestDTO userRequestDTO) {

        TraineeRequestDTO trainee = TraineeRequestDTO.builder()
                .dateOfBirth(userRequestDTO.getDateOfBirth())
                .address(userRequestDTO.getAddress())
                .user(userRequestDTO.getUser())
                .build();

        return traineeService.createTrainee(trainee) != null;
    }

    @Override
    public Boolean register(RegisterTrainerRequestDTO userRequestDTO) {

        TrainerRequestDTO trainer = TrainerRequestDTO.builder()
                .specialization(userRequestDTO.getSpecialization())
                .user(userRequestDTO.getUser())
                .build();

        return trainerService.createTrainer(trainer) != null;
    }

    @Override
    public Boolean authenticate(AuthenticateRequestDTO authenticateRequestDTO) {
        if (authenticateRequestDTO.getUsername() != null && authenticateRequestDTO.getPassword() != null)
            return userService.existsByUsernameAndPassword(
                    authenticateRequestDTO.getUsername(), authenticateRequestDTO.getPassword()
            );
        return false;
    }
}
