package epam.repository;

import epam.entity.User;
import jakarta.persistence.EntityManager;

@FunctionalInterface
public interface TrainingUserRepository {
    EntityManager getEntityManager();

    default Long serialUsernames(String username) {
        EntityManager entityManager = getEntityManager();
        return entityManager.createQuery(
                        "SELECT COUNT(u) FROM User u WHERE u.username LIKE :username", Long.class)
                .setParameter("username", username + "%")
                .getSingleResult();
    }

    default User updateUsername(User user) {
        user.setUsername(user.getFirstname() + "." + user.getLastname());
        return user;
    }

}
