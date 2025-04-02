package epam.repository;

import epam.dto.request_dto.ChangePasswordRequestDTO;
import epam.entity.User;

import java.util.Optional;

public interface UserRepository {

    boolean existsByUsername(String username);

    boolean existsByUsernameAndPassword(String username, String password);

    Boolean changePassword(ChangePasswordRequestDTO changePasswordRequestDTO);

    Optional<User> findUserByUsername(String username);
}
