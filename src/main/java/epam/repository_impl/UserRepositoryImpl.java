package epam.repository_impl;

import epam.entity.User;
import epam.repository.UserRepository;
import epam.request_dto.ChangePasswordRequestDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final Log log = LogFactory.getLog(UserRepositoryImpl.class);

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
                            SELECT COUNT(u) FROM User u WHERE u.username = :username AND u.password = :password
                        """, Long.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .getSingleResult();
        return count > 0;
    }


    @Override
    public Boolean changePassword(ChangePasswordRequestDTO changePasswordRequestDTO) {
        if (existsByUsernameAndPassword(changePasswordRequestDTO.getUsername(), changePasswordRequestDTO.getOldPassword())) {
            try {

                entityManager.getTransaction().begin();

                entityManager.createQuery(
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
                return true;
            } catch (Exception e) {
                entityManager.getTransaction().rollback();
                log.error(e.getMessage());
            }
        }
        return false;
    }

    @Override
    public Boolean toggleActiveStatus(User user) {
        try {
            entityManager.getTransaction().begin();

            // Toggle status in Java
            user.setIsActive(!user.getIsActive());

            // Merge updated entity into persistence context
            User updatedUser = entityManager.merge(user);

            entityManager.getTransaction().commit();

            return updatedUser.getIsActive();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error("Error toggling active status: " + e.getMessage(), e);
            return false;
        }
    }



    @Override
    @Transactional(readOnly = true)
    public User getByUsername(String username) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);

        Root<User> root = criteriaQuery.from(User.class);

        criteriaQuery.select(root);

        criteriaQuery.where(criteriaBuilder.equal(root.get("username"), username));

        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

}
