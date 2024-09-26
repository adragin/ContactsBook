package org.adrahin.contactsbook.repository;

import org.adrahin.contactsbook.model.contactModels.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface JPAContactRepository extends JpaRepository<Contact, UUID> {
    @Query("SELECT c FROM Contact c WHERE c.ownerId = :ownerId")
    List<Contact>  findContactsByOwnerId(@Param("ownerId") UUID ownerId);
}
