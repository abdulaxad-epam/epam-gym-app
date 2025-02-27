package epam.dao;

import epam.domain.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;
import java.util.UUID;

@FunctionalInterface
public interface TrainingUserRepository<T extends User> {
    Log log = LogFactory.getLog(TrainingUserRepository.class);

    Map<UUID, T> getInMemoryStorage();

    default T insert(UUID id, T trainingUser) {
        Map<UUID, T> inMemoryStorage = getInMemoryStorage();

        trainingUser = updateUsername(trainingUser);

        String oldUserName = trainingUser.getUsername();
        String newUsername = oldUserName;

        int count = 1;

        log.info("Inserting user with ID: " + id + " and username: " + oldUserName);

        while (isUsernameExists(newUsername)) {
            log.warn("Username '" + newUsername + "' already exists! Trying '" + oldUserName + count + "'");
            newUsername = oldUserName + count;
            count++;
        }

        trainingUser.setUsername(newUsername);
        trainingUser.setUserId(id);
        inMemoryStorage.put(trainingUser.getUserId(), trainingUser);

        log.info("User '" + id + "' inserted with final username '" + newUsername + "'");
        return trainingUser;
    }

    default T updateUsername(T trainingUser) {
        trainingUser.setUsername(trainingUser.getFirstname() + "." + trainingUser.getLastname());
        return trainingUser;
    }

    default boolean isUsernameExists(String username) {
        Map<UUID, T> inMemoryStorage = getInMemoryStorage();
        boolean exists = inMemoryStorage.values().stream()
                .anyMatch(trainee -> trainee.getUsername().equals(username));

        if (exists) {
            log.debug("Username '" + username + "' already exists in storage");
        }
        return exists;
    }
}
