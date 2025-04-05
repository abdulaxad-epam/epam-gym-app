package epam.service.impl;

import epam.dto.request_dto.AuthenticateRequestDTO;
import epam.dto.request_dto.ChangePasswordRequestDTO;
import epam.dto.request_dto.RegisterTraineeRequestDTO;
import epam.dto.request_dto.RegisterTrainerRequestDTO;
import epam.dto.request_dto.TraineeRequestDTO;
import epam.dto.request_dto.TrainerRequestDTO;
import epam.dto.response_dto.RegisterTraineeResponseDTO;
import epam.dto.response_dto.RegisterTrainerResponseDTO;
import epam.exception.exception.UserNotFoundException;
import epam.service.AuthenticationService;
import epam.service.TraineeService;
import epam.service.TrainerService;
import epam.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final TrainerService trainerService;
    private final TraineeService traineeService;

    @Override
    public RegisterTraineeResponseDTO register(RegisterTraineeRequestDTO userRequestDTO, HttpServletResponse response) {

        TraineeRequestDTO traineeRequestDTO = TraineeRequestDTO.builder()
                .dateOfBirth(userRequestDTO.getDateOfBirth())
                .address(userRequestDTO.getAddress())
                .user(userRequestDTO.getUser())
                .build();

        RegisterTraineeResponseDTO trainee = traineeService.createTrainee(traineeRequestDTO);

        addCookie(response, trainee.getUser().getUsername(), trainee.getPassword());

        return trainee;
    }

    @Override
    public RegisterTrainerResponseDTO register(RegisterTrainerRequestDTO userRequestDTO, HttpServletResponse response) {

        TrainerRequestDTO trainerRequestDTO = TrainerRequestDTO.builder()
                .specialization(userRequestDTO.getSpecialization())
                .user(userRequestDTO.getUser())
                .build();

        RegisterTrainerResponseDTO trainer = trainerService.createTrainer(trainerRequestDTO);

        addCookie(response, trainer.getUser().getUsername(), trainer.getPassword());

        return trainer;
    }

    @Override
    public Boolean authenticate(AuthenticateRequestDTO authenticateRequestDTO, HttpServletResponse response) {
        if (authenticateRequestDTO.getUsername() != null && authenticateRequestDTO.getPassword() != null &&
                userService.existsByUsernameAndPassword(authenticateRequestDTO.getUsername(), authenticateRequestDTO.getPassword())) {
            addCookie(response, authenticateRequestDTO.getUsername(), authenticateRequestDTO.getPassword());
            return true;
        }
        throw new UserNotFoundException("User not found wrong username/password");
    }

    @Override
    public Boolean changePassword(ChangePasswordRequestDTO changePasswordRequestDTO, HttpServletResponse response) {
        if (changePasswordRequestDTO.getOldPassword() != null && changePasswordRequestDTO.getNewPassword() != null &&
                userService.changePassword(changePasswordRequestDTO)) {
            addCookie(response, changePasswordRequestDTO.getUsername(), changePasswordRequestDTO.getNewPassword());
            return true;
        }
        throw new UserNotFoundException("User not found wrong username/password");
    }

    @Override
    public boolean validateToken(String authCookie) {
        String[] split = authCookie.split(":");
        if (split.length == 3) {
            return userService.existsByUsernameAndPassword(split[0], split[2]);
        }
        return false;
    }

    private void addCookie(HttpServletResponse response, String... credentials) {
        Cookie cookie = new Cookie("__auth", generateToken(credentials[0], credentials[1]));
        cookie.setMaxAge(3600);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);
    }

    private String generateToken(String... credentials) {
        return credentials[0] + ":" + UUID.randomUUID() + ":" + credentials[1];
    }
}
