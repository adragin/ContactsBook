package org.adrahin.contactsbook.repository;


import org.adrahin.contactsbook.model.userModels.User;

import java.util.List;
import java.util.UUID;

public interface InterfaceUserRepository {

    User createUser(User user);

    User getUserById(UUID userId);

    User getUserByLogin(String login);

    List<User> getAllUsers();

    UUID getUserIdByToken(String token);

    int getUserRoleByToken(String token);

    User updateUser(User user);

    User setUserRoleById(UUID userId, int role);

    boolean deleteUserById(UUID userId);

}

