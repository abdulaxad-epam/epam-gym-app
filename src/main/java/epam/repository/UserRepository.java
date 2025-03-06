package epam.repository;

import epam.request_dto.ChangePasswordRequestDTO;

public interface UserRepository {

    boolean existsByUsername(String username);

    boolean existsByUsernameAndPassword(String username, String password);

    Boolean changePassword(ChangePasswordRequestDTO changePasswordRequestDTO);
}
