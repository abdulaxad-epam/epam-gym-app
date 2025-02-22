package epam.dao;

import epam.domain.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;
import java.util.UUID;

@FunctionalInterface
public interface Train<T extends User> {
    Log log = LogFactory.getLog(Train.class);

    Map<UUID, T> getInMemoryStorage();

    default T insert(UUID id, T train) {
        Map<UUID, T> inMemoryStorage = getInMemoryStorage();

        train.setUsername(train.getFirstname() + "." + train.getLastname());

        String oldUserName = train.getUsername();
        String newUsername = oldUserName;

        int count = 1;

        log.info("Inserting user with ID: "+id+" and username: "+oldUserName);

        while (isUsernameExists(newUsername)) {
            log.warn("Username '" + newUsername + "' already exists! Trying '" + oldUserName + count + "'");
            newUsername = oldUserName + count;
            count++;
        }

        train.setUsername(newUsername);
        inMemoryStorage.put(id, train);

        log.info("User '" + id + "' inserted with final username '" + newUsername + "'");
        return train;
    }

    default boolean isUsernameExists(String username) {
        Map<UUID, T> inMemoryStorage = getInMemoryStorage();
        boolean exists = inMemoryStorage.values().stream()
                .anyMatch(trainee -> trainee.getUsername().equals(username));

        if (exists) {
            log.debug("Username '"+username+"' already exists in storage");
        }
        return exists;
    }
}
