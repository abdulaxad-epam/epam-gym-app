package epam.service;

import epam.dto.request_dto.AuthenticateRequestDTO;
import epam.dto.request_dto.ChangePasswordRequestDTO;
import epam.dto.request_dto.RegisterTraineeRequestDTO;
import epam.dto.request_dto.RegisterTrainerRequestDTO;
import epam.dto.response_dto.RegisterTraineeResponseDTO;
import epam.dto.response_dto.RegisterTrainerResponseDTO;
import jakarta.servlet.http.HttpServletResponse;


public interface AuthenticationService {

    RegisterTrainerResponseDTO register(RegisterTrainerRequestDTO userRequestDTO, HttpServletResponse response);

    RegisterTraineeResponseDTO register(RegisterTraineeRequestDTO userRequestDTO, HttpServletResponse response);

    Boolean authenticate(AuthenticateRequestDTO authenticateRequestDTO, HttpServletResponse response);

    Boolean changePassword(ChangePasswordRequestDTO changePasswordRequestDTO, HttpServletResponse response);

    boolean validateToken(String authCookie);
}
