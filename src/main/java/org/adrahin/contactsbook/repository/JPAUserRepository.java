package org.adrahin.contactsbook.repository;


import org.adrahin.contactsbook.model.userModels.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JPAUserRepository extends JpaRepository<User, UUID> {
    Optional<User> findUserByLogin(String login);
}
