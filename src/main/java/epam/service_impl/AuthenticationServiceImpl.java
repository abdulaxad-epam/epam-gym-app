package epam.service_impl;

import epam.mapper.TraineeMapper;
import epam.mapper.UserMapper;
import epam.request_dto.AuthenticateRequestDTO;
import epam.request_dto.RegisterTraineeRequestDTO;
import epam.request_dto.RegisterTrainerRequestDTO;
import epam.request_dto.TraineeRequestDTO;
import epam.request_dto.TrainerRequestDTO;
import epam.service.AuthenticationService;
import epam.service.TraineeService;
import epam.service.TrainerService;
import epam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userRepository;
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
            return userRepository.existsByUsernameAndPassword(
                    authenticateRequestDTO.getUsername(), authenticateRequestDTO.getPassword()
            );
        return false;
    }

}
