package epam.repository;

import epam.dto.request_dto.ChangePasswordRequestDTO;
import epam.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByUsername(String username);

    boolean existsByUsernameAndPassword(String username, String password);

    @Modifying
    @Query("""
             UPDATE User u
             SET u.password = :newPassword
             WHERE u.username = :username AND u.password = :oldPassword
            """)
    Boolean changePassword(String newPassword, String oldPassword, String username);

}
