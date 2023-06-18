package com.user_management.api.access;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.user_management.dao.UserDAO;

@Singleton
public class AccessManager {
    @Inject
    private UserDAO userDAO;

    private final Map<String, UUID> users = new ConcurrentHashMap<>();
    private final Map<UUID, Long> timeFromUserTokens = new ConcurrentHashMap<>();

    public boolean deregister(String username) {
        if (users.containsKey(username)) {
            UUID id = users.get(username);
            timeFromUserTokens.remove(id);
        }

        return users.remove(username) != null ? true : false;
    }

    public UUID register(String username, String password) {
        if (userDAO.checkUserPassword(username, password) == false) {
            throw new RuntimeException("ERROR: username and password do not correspond");
        }

        if (users.containsKey(username) == false) {
            UUID uuid = UUID.randomUUID();
            long currentTimeStamp = Instant.now().getEpochSecond();
            if (users.putIfAbsent(username, uuid) == null
                    && timeFromUserTokens.putIfAbsent(uuid, currentTimeStamp) == null) {
                return uuid;
            } else {
                throw new RuntimeException("ERROR: register user");
            }
        } else {
            // TODO: Sobald Frontend steht müssen wir hasAccess aufrufen, sobald die Seite
            // geladen wird -->
            // Bei jeder Interaktion muss neu has Access aufgerufen werden
            if (hasAccess(users.get(username)) == false) {
                throw new RuntimeException("ERROR: Logged out!");
            } else {
                return users.get(username);
            }

        }

    }

    public UUID getUuid(String username) {
        return users.get(username);
    }


    public String getUsername(UUID uuid) {
        Optional<String> username = users.entrySet().stream().filter(entry -> uuid.equals(entry.getValue()))
                .map(Map.Entry::getKey).findFirst();

        return username.isPresent() ? username.get() : null;
    }

    public boolean hasAccess(UUID uuid) {
        if (!timeFromUserTokens.containsKey(uuid)) {
            return false;
        }

        long currentTimeStamp = Instant.now().getEpochSecond();
        System.out.println("Timestapm:" + currentTimeStamp);
        long registerTimeStamp = timeFromUserTokens.getOrDefault(uuid, 0L);
        long maxTimeToLive = 300;
        if (registerTimeStamp + maxTimeToLive < currentTimeStamp) {
            deregister(getUsername(uuid));
        } else {
            // set new token time
            timeFromUserTokens.replace(uuid, currentTimeStamp);
        }

        return users.containsValue(uuid);
    }
}
