package epam.user.repository;

import epam.shared.security.dto.ChangePasswordRequestDTO;
import epam.user.entity.User;

import java.util.Optional;

public interface UserRepository {

    boolean existsByUsername(String username);

    boolean existsByUsernameAndPassword(String username, String password);

    Boolean changePassword(ChangePasswordRequestDTO changePasswordRequestDTO);

    Optional<User> findUserByUsername(String username);
}
