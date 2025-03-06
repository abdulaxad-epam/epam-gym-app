package epam.service_impl;

import epam.entity.Trainee;
import epam.entity.Trainer;
import epam.entity.User;
import epam.mapper.UserMapper;
import epam.repository.TraineeRepository;
import epam.repository.TrainerRepository;
import epam.repository.UserRepository;
import epam.request_dto.AuthenticateRequestDTO;
import epam.request_dto.ChangePasswordRequestDTO;
import epam.request_dto.RegisterTraineeRequestDTO;
import epam.request_dto.RegisterTrainerRequestDTO;
import epam.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final TrainerRepository trainerRepository;

    private final UserMapper userMapper;

    private final TraineeRepository traineeRepository;

    private final UserRepository userRepository;

    @Override
    public Boolean register(RegisterTraineeRequestDTO userRequestDTO) {
        User connectedUser = userMapper.toUser(userRequestDTO.getUser());

        Trainee trainee = Trainee.builder()
                .address(userRequestDTO.getAddress())
                .dateOfBirth(userRequestDTO.getDateOfBirth())
                .user(connectedUser)
                .build();

        return traineeRepository.insert(trainee.getTraineeId(), trainee) != null;
    }

    @Override
    public Boolean register(RegisterTrainerRequestDTO userRequestDTO) {
        User connectedUser = userMapper.toUser(userRequestDTO.getUser());

        Trainer trainer = Trainer.builder()
                .specialization(userRequestDTO.getSpecialization())
                .user(connectedUser)
                .build();

        return trainerRepository.insert(trainer.getTrainerId(), trainer) != null;
    }

    @Override
    public Boolean authenticate(AuthenticateRequestDTO authenticateRequestDTO) {
        if (authenticateRequestDTO.getUsername() != null && authenticateRequestDTO.getPassword() != null)
            return userRepository.existsByUsernameAndPassword(
                    authenticateRequestDTO.getUsername(), authenticateRequestDTO.getPassword()
            );
        return false;
    }

    @Override
    public Boolean changePassword(ChangePasswordRequestDTO changePasswordRequestDTO) {
        if (changePasswordRequestDTO.getUsername() != null && changePasswordRequestDTO.getOldPassword() != null && changePasswordRequestDTO.getNewPassword() != null)
            return userRepository.changePassword(changePasswordRequestDTO);
        return false;
    }

}
