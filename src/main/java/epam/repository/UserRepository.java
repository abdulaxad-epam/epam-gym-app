package epam.repository;

import epam.dto.request_dto.ChangePasswordRequestDTO;
import epam.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByUsername(String username);

    boolean existsByUsernameAndPassword(String username, String password);

    Optional<User> getByUsernameAndPassword(String username, String password);

    Optional<User> findByUsername(String username);
}
