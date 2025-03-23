package epam.user.service;

import epam.shared.security.dto.ChangePasswordRequestDTO;

public interface UserService {

    Boolean existsByUsernameAndPassword(String username, String password);

    boolean existsByUsername(String username);

    Boolean changePassword(ChangePasswordRequestDTO changePasswordRequestDTO);

    Boolean toggleStatus(String username);
}
