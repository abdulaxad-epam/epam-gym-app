package epam.service;

import epam.dto.request_dto.ChangePasswordRequestDTO;
import epam.entity.User;

import java.util.Optional;

public interface UserService {

    Boolean existsByUsernameAndPassword(String username, String password);

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}
