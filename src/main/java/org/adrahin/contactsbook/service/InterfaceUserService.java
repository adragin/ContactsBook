package org.adrahin.contactsbook.service;

import org.adrahin.contactsbook.model.userModels.User;

import org.adrahin.contactsbook.model.userModels.UserDtoLoginRequest;
import org.adrahin.contactsbook.model.userModels.UserDtoRegisterRequest;
import org.adrahin.contactsbook.model.userModels.UserDtoResponse;

import java.util.List;
import java.util.UUID;

public interface InterfaceUserService {

    UserDtoResponse registerUser(UserDtoRegisterRequest userDTO);

    UserDtoResponse loginUser(UserDtoLoginRequest userDTO);

    List<User> getAllUsers();

    User getUserById(UUID userId);

    UserDtoResponse updateUser(User user);

    boolean deleteUserById(UUID userId);

    UUID getUserIdByToken(String token);

    boolean isAdmin(String token);

    boolean isUserExists(String login);
}



