package epam.service;

import epam.request_dto.AuthenticateRequestDTO;
import epam.request_dto.ChangePasswordRequestDTO;
import epam.request_dto.RegisterTraineeRequestDTO;
import epam.request_dto.RegisterTrainerRequestDTO;


public interface AuthenticationService {
    Boolean register(RegisterTraineeRequestDTO userRequestDTO);

    Boolean register(RegisterTrainerRequestDTO userRequestDTO);

    Boolean authenticate(AuthenticateRequestDTO authenticateRequestDTO);

    Boolean changePassword(ChangePasswordRequestDTO changePasswordRequestDTO);
}
