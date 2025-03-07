package epam.service;

import epam.request_dto.ChangePasswordRequestDTO;

public interface UserService {

    Boolean existsByUsernameAndPassword(String username, String password);

    boolean existsByUsername(String username);

    Boolean changePassword(ChangePasswordRequestDTO changePasswordRequestDTO);

    Boolean toggleStatus(String username);
}
