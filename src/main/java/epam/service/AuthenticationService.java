package epam.service;

import epam.dto.request_dto.AuthenticateRequestDTO;
import epam.dto.request_dto.RegisterTraineeRequestDTO;
import epam.dto.request_dto.RegisterTrainerRequestDTO;


public interface AuthenticationService {
    Boolean register(RegisterTraineeRequestDTO userRequestDTO);

    Boolean register(RegisterTrainerRequestDTO userRequestDTO);

    Boolean authenticate(AuthenticateRequestDTO authenticateRequestDTO);
}
