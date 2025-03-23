package epam.shared.util;


import epam.user.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Setter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;


public class UsernameGeneratorListener {

    private static final Log log = LogFactory.getLog(UsernameGeneratorListener.class);

    @Setter
    private static EntityManagerFactory entityManagerFactory;

    @PrePersist
    @PreUpdate
    public void generateUsername(User user) {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            String baseUsername = user.getFirstname().toLowerCase() + "." + user.getLastname().toLowerCase();
            long count = countUsernames(baseUsername);
            System.out.println((String.format("Username generated: %s   %d", baseUsername, count)));
            if (count > 0) {
                user.setUsername(baseUsername + count);
            } else {
                user.setUsername(baseUsername);
            }

        }
    }

    private long countUsernames(String baseUsername) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            // Find existing usernames that match "baseUsername" + possible numeric suffix
            List<String> existingUsernames = entityManager.createQuery(
                            "SELECT u.username FROM User u WHERE u.username LIKE :username", String.class)
                    .setParameter("username", baseUsername + "%")
                    .getResultList();

            // Extract max numeric suffix and return incremented count
            return getNextUsernameIndex(baseUsername, existingUsernames);
        } catch (Exception e) {
            log.error(e);
            return 0;
        }
    }
    private long getNextUsernameIndex(String baseUsername, List<String> existingUsernames) {
        long maxIndex = 0;
        boolean baseExists = false; // Track if "baseUsername" itself exists

        for (String username : existingUsernames) {
            if (username.equals(baseUsername)) {
                baseExists = true; // "baseUsername" exists
            } else if (username.startsWith(baseUsername)) {
                String suffix = username.substring(baseUsername.length()).trim(); // Extract suffix

                try {
                    long num = Long.parseLong(suffix); // Convert to number if possible
                    maxIndex = Math.max(maxIndex, num);
                } catch (NumberFormatException ignored) {
                    // Ignore cases where suffix isn't a number
                }
            }
        }

        return baseExists ? maxIndex + 1 : 1; // If base exists, start numbering at 1
    }

}
