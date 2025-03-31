package epam.shared.security.service;

import epam.shared.security.dto.AuthenticateRequestDTO;
import epam.shared.security.dto.ChangePasswordRequestDTO;
import epam.shared.security.dto.RegisterTraineeRequestDTO;
import epam.shared.security.dto.RegisterTraineeResponseDTO;
import epam.shared.security.dto.RegisterTrainerRequestDTO;
import epam.shared.security.dto.RegisterTrainerResponseDTO;
import epam.trainee.dto.TraineeResponseDTO;
import epam.trainer.dto.TrainerResponseDTO;
import jakarta.servlet.http.HttpServletResponse;


public interface AuthenticationService {

    RegisterTrainerResponseDTO register(RegisterTrainerRequestDTO userRequestDTO, HttpServletResponse response);

    RegisterTraineeResponseDTO register(RegisterTraineeRequestDTO userRequestDTO, HttpServletResponse response);

    Boolean authenticate(AuthenticateRequestDTO authenticateRequestDTO, HttpServletResponse response);

    Boolean changePassword(ChangePasswordRequestDTO changePasswordRequestDTO, HttpServletResponse response);

    boolean validateToken(String authCookie);
}
