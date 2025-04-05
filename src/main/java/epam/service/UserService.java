package epam.service;

import epam.dto.request_dto.ChangePasswordRequestDTO;

public interface UserService {

    Boolean existsByUsernameAndPassword(String username, String password);

    boolean existsByUsername(String username);

    Boolean changePassword(ChangePasswordRequestDTO changePasswordRequestDTO);
}
