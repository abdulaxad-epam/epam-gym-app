package epam.service;

import epam.request_dto.AuthenticateRequestDTO;
import epam.request_dto.RegisterTraineeRequestDTO;
import epam.request_dto.RegisterTrainerRequestDTO;
import epam.request_dto.UserRequestDTO;

public interface AuthenticationService {
    void register(RegisterTraineeRequestDTO userRequestDTO);

    void register(RegisterTrainerRequestDTO userRequestDTO);

    void authenticate(AuthenticateRequestDTO authenticateRequestDTO);


}
