package org.adrahin.contactsbook.repository;

import org.adrahin.contactsbook.model.userModels.User;
import org.adrahin.contactsbook.utils.UtilsUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class DBUserRepositoryAdapter implements InterfaceUserRepository {

    @Autowired
    private JPAUserRepository jpaUserRepository;
    @Autowired
    private UtilsUser utilsUser;

    @Override
    public User createUser(User user) {
        return jpaUserRepository.save(user);
    }

    @Override
    public User getUserById(UUID userId) {
        Optional<User> userById = jpaUserRepository.findById(userId);
        return userById.orElse(null);
    }

    @Override
    public User getUserByLogin(String login) {
        Optional<User> userByLogin = jpaUserRepository.findUserByLogin(login);
        return userByLogin.orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return jpaUserRepository.findAll();
    }

    @Override
    public UUID getUserIdByToken(String token) {
        String userLogin = utilsUser.extractLoginFromToken(token);
        User userByLogin = getUserByLogin(userLogin);
        if (userByLogin != null) {
            return userByLogin.getUserId();
        }
        return null;
    }

    @Override
    public int getUserRoleByToken(String token) {
        String userLogin = utilsUser.extractLoginFromToken(token);
        User userByLogin = getUserByLogin(userLogin);
        if (userByLogin != null) {
            return userByLogin.getRole();
        }
        return -1;
    }

    @Override
    public User updateUser(User user) {
        Optional<User> userToUpdate = jpaUserRepository.findById(user.getUserId());
        if (userToUpdate.isPresent()) {
            userToUpdate.get().setPassword(user.getPassword());
            userToUpdate.get().setRole(user.getRole());
            userToUpdate.get().setUserName(user.getUserName());
            userToUpdate.get().setLastUpdateDate(LocalDateTime.now());
        }
        jpaUserRepository.save(userToUpdate.get());
        return userToUpdate.get();
    }

    @Override
    public User setUserRoleById(UUID userId, int role) {
        Optional<User> userById = jpaUserRepository.findById(userId);
        if (userById.isPresent()) {
            User user = userById.get();
            user.setRole(role);
            jpaUserRepository.save(user);
            return user;
        }
        return null;
    }

    @Override
    public boolean deleteUserById(UUID userId) {
        Optional<User> userById = jpaUserRepository.findById(userId);
        if (userById.isPresent()) {
            jpaUserRepository.delete(userById.get());
            return true;
        }
        return false;
    }

}
