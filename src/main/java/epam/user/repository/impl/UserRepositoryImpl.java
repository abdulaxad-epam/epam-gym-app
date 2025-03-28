package epam.user.repository.impl;

import epam.shared.security.dto.ChangePasswordRequestDTO;
import epam.user.entity.User;
import epam.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Log
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final EntityManager entityManager;

    @Override
    public boolean existsByUsername(String username) {
        return entityManager.createQuery(
                        """
                                SELECT CASE WHEN EXISTS
                                (SELECT 1 FROM User u WHERE u.username = :username)
                                THEN TRUE
                                ELSE
                                FALSE END
                                """,
                        Boolean.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    @Override
    public boolean existsByUsernameAndPassword(String username, String password) {

        Long count = entityManager.createQuery("""
                            SELECT COUNT(u) FROM User u WHERE  u.username = :username AND u.password = :password
                        """, Long.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .getSingleResult();

        return count > 0;
    }


    @Override
    public Boolean changePassword(ChangePasswordRequestDTO changePasswordRequestDTO) {
        if (existsByUsernameAndPassword(changePasswordRequestDTO.getUsername().toLowerCase(), changePasswordRequestDTO.getOldPassword())) {
            try {

                entityManager.getTransaction().begin();

                int i = entityManager.createQuery(
                                """
                                        UPDATE User u
                                        SET u.password = :newPassword
                                        WHERE u.username = :username AND u.password = :oldPassword
                                        """)
                        .setParameter("username", changePasswordRequestDTO.getUsername())
                        .setParameter("oldPassword", changePasswordRequestDTO.getOldPassword())
                        .setParameter("newPassword", changePasswordRequestDTO.getNewPassword())
                        .executeUpdate();

                entityManager.getTransaction().commit();
                return i > 0;
            } catch (Exception e) {
                entityManager.getTransaction().rollback();
                log.warning(e.getMessage());
            }
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findUserByUsername(String username) {

        User foundUser = entityManager.createQuery(
                        """
                                SELECT u FROM User u WHERE u.username = :username
                                """,
                        User.class)
                .setParameter("username", username)
                .getSingleResult();
        return Optional.ofNullable(foundUser);
    }


}
