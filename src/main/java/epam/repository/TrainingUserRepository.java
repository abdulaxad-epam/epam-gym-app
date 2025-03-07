package epam.repository;

import epam.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;

@FunctionalInterface
public interface TrainingUserRepository {
    EntityManager getEntityManager();

    default Long serialUsernames(String username) {
        EntityManager entityManager = getEntityManager();
        try {
            // Check if the User table exists
            Boolean tableExists = (Boolean) entityManager.createNativeQuery(
                    "SELECT CASE WHEN EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'users') THEN TRUE ELSE FALSE END"
            ).getSingleResult();

            if (Boolean.FALSE.equals(tableExists)) {
                return 0L;
            }

            // If the table exists, count the number of usernames
            return entityManager.createQuery(
                            "SELECT COUNT(u) FROM User u WHERE u.username LIKE :username", Long.class)
                    .setParameter("username", username + "%")
                    .getSingleResult();

        } catch (Exception e) {
            System.err.println("Error checking User table or counting usernames: " + e.getMessage());
            return 0L; // Default to 0 on any unexpected errors
        }
    }

    default User updateUsername(User user) {
        user.setUsername(user.getFirstname() + "." + user.getLastname());
        return user;
    }

}
