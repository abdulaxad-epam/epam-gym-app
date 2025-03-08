package epam.repository;

import epam.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;

@FunctionalInterface
public interface TrainingUserRepository {
    EntityManager getEntityManager();

    private Long serialUsernames(String username) {
        EntityManager entityManager = getEntityManager();
        try {
            return entityManager.createQuery(
                            "SELECT COUNT(u) FROM User u WHERE u.username LIKE :username", Long.class)
                    .setParameter("username", username + "%")
                    .getSingleResult();
        } catch (Exception e) {
            System.err.println("Error checking existing usernames: " + e.getMessage());
            return 0L;
        }
    }

    default User updateUsername(User user) {
        String baseUsername = user.getFirstname() + "." + user.getLastname();
        long serial = serialUsernames(baseUsername);

        if (serial > 0) {
            user.setUsername(baseUsername + serial);
        } else {
            user.setUsername(baseUsername);
        }

        return user;
    }

}
