package org.adrahin.contactsbook.repository;

import org.adrahin.contactsbook.model.contactModels.Contact;
import org.adrahin.contactsbook.model.contactModels.ContactDto;

import java.util.List;
import java.util.UUID;

public interface InterfaceContactRepository {

    Contact createContact(Contact contact, UUID userID);

    Contact getContactByContactId(UUID id);

    List<Contact> getAllContacts();

    List<Contact> getContactsByUserId(UUID userId);

//    ContactDto updateContact(UUID id, ContactDto newContact);

    boolean deleteContactById(UUID id);

//    UUID getOwnerId(UUID contactId);
}
