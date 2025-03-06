package epam.service;

import epam.request_dto.ChangePasswordRequestDTO;

public interface UserService {

    Boolean changePassword(ChangePasswordRequestDTO changePasswordRequestDTO);

    Boolean existsByUsernameAndPassword(String username, String password);

    boolean existsByUsername(String username);

}
