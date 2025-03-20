package epam.repository;

import epam.dto.request_dto.ChangePasswordRequestDTO;
import epam.entity.User;

public interface UserRepository {

    boolean existsByUsername(String username);

    boolean existsByUsernameAndPassword(String username, String password);

    Boolean changePassword(ChangePasswordRequestDTO changePasswordRequestDTO);

    Boolean toggleActiveStatus(User username);

    User getByUsername(String username);
}
