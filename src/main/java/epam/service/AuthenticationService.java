package epam.service;

import epam.dto.request_dto.AuthenticateRequestDTO;
import epam.dto.request_dto.ChangePasswordRequestDTO;
import epam.dto.request_dto.RegisterTraineeRequestDTO;
import epam.dto.request_dto.RegisterTrainerRequestDTO;
import epam.dto.response_dto.AuthenticationResponseDTO;
import epam.dto.response_dto.RegisterTraineeResponseDTO;
import epam.dto.response_dto.RegisterTrainerResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;


public interface AuthenticationService {

    AuthenticationResponseDTO register(RegisterTrainerRequestDTO userRequestDTO);

    AuthenticationResponseDTO register(RegisterTraineeRequestDTO userRequestDTO);

    AuthenticationResponseDTO authenticate(AuthenticateRequestDTO authenticateRequestDTO);

    Boolean changePassword(ChangePasswordRequestDTO changePasswordRequestDTO, Authentication authentication);

    void logout(HttpServletRequest request);

    AuthenticationResponseDTO refreshToken(HttpServletRequest request);
}
