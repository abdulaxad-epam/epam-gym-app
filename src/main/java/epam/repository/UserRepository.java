package epam.repository;

import epam.entity.User;
import epam.dto.request_dto.ChangePasswordRequestDTO;

public interface UserRepository {

    boolean existsByUsername(String username);

    boolean existsByUsernameAndPassword(String username, String password);

    Boolean changePassword(ChangePasswordRequestDTO changePasswordRequestDTO);

    Boolean toggleActiveStatus(User username);

    User getByUsername(String username);
}
