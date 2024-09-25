package org.adrahin.contactsbook.factories;

import org.adrahin.contactsbook.model.userModels.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class UserFactory {

    public User createUser(String login, String password, String userName) {
        User user = new User();
        UUID userId = UUID.randomUUID();

        user.setCreateDate(LocalDateTime.now());
        user.setLastUpdateDate(LocalDateTime.now());

        return user;
    }
}
