package org.adrahin.contactsbook.service;

import org.adrahin.contactsbook.exceptions.EasyUserPasswordException;
import org.adrahin.contactsbook.exceptions.InvalidLoginPasswordException;
import org.adrahin.contactsbook.exceptions.UserAlreadyExistsException;
import org.adrahin.contactsbook.factories.UserFactory;
import org.adrahin.contactsbook.model.ErrorResponse;
import org.adrahin.contactsbook.model.userModels.*;
import org.adrahin.contactsbook.repository.InterfaceUserRepository;
import org.adrahin.contactsbook.utils.UtilsUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class DataBaseUserService implements InterfaceUserService {

    @Autowired
    private InterfaceUserRepository userRepository;
    @Autowired
    private UtilsUser utilsUser;
    @Autowired
    private UserFactory userFactory;

    @Override
    public UserDtoResponse registerUser(UserDtoRegisterRequest userDtoRegister) {
        User existingUser = userRepository.getUserByLogin(userDtoRegister.getLogin());
        if (checkExistingUser(existingUser, userDtoRegister)) {
            throw new UserAlreadyExistsException("User already exists");
        }

        if (!utilsUser.checkCorrectPassword(userDtoRegister.getPassword())) {
            throw new EasyUserPasswordException("Your password is too easy");
        }


        User newUser = userFactory.createUser(
                userDtoRegister.getLogin(),
                utilsUser.hashPassword(userDtoRegister.getPassword()),
                userDtoRegister.getName()
        );
        newUser.setRole(Roles.USER.getRole());

        String token = utilsUser.generateToken(newUser.getLogin(), newUser.getPassword());

        userRepository.createUser(newUser);

        return UserDtoResponse.builder()
                .login(newUser.getLogin())
                .token(token)
                .build();
    }

    @Override
    public UserDtoResponse loginUser(UserDtoLoginRequest userDtoLogin) {
        User existingUser = userRepository.getUserByLogin(userDtoLogin.getLogin());

        if (existingUser != null) {
            boolean autorized = existingUser.getLogin().equalsIgnoreCase(userDtoLogin.getLogin()) &&
                    existingUser.getPassword().equals(utilsUser.hashPassword(userDtoLogin.getPassword()));
            if (!autorized) {
                throw new InvalidLoginPasswordException("Invalid login or password.");
            }

            String token = utilsUser.generateToken(userDtoLogin.getLogin(), userDtoLogin.getPassword());
            return UserDtoResponse.builder()
                    .login(userDtoLogin.getLogin())
                    .token(token)
                    .build();
        }

        return UserDtoResponse.builder()
                .error(new ErrorResponse(404, "User not found"))
                .build();
    }

    @Override
    public User getUserById(UUID userId) {
        return userRepository.getUserById(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public boolean deleteUserById(UUID userId) {
        return userRepository.deleteUserById(userId);
    }

    @Override
    public UserDtoResponse updateUser(User user) {
        if (isUserExists(user.getLogin())) {
            userRepository.updateUser(user);
            return UserDtoResponse.builder()
                    .login(user.getLogin())
                    .token(utilsUser.generateToken(user.getLogin(), user.getPassword()))
                    .build();
        }
        return UserDtoResponse.builder()
                .error(new ErrorResponse(404, "User not found"))
                .build();
    }

    @Override
    public UUID getUserIdByToken(String token) {
        return userRepository.getUserIdByToken(token);
    }

    @Override
    public boolean isAdmin(String token) {
        getUserIdByToken(token);
        return userRepository.getUserRoleByToken(token) == 1;
    }

    @Override
    public boolean isUserExists(String login) {
        return userRepository.getUserByLogin(login) != null;
    }

    private boolean checkExistingUser(User user, UserDtoRegisterRequest userDtoRegister) {
        return Objects.nonNull(user) && user.getLogin().equalsIgnoreCase(userDtoRegister.getLogin());
    }

}
