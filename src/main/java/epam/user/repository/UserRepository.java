package epam.user.repository;

import epam.shared.security.dto.ChangePasswordRequestDTO;
import epam.user.entity.User;

public interface UserRepository {

    boolean existsByUsername(String username);

    boolean existsByUsernameAndPassword(String username, String password);

    Boolean changePassword(ChangePasswordRequestDTO changePasswordRequestDTO);

    Boolean toggleActiveStatus(User username);

    User getByUsername(String username);

}
