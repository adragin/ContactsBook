package org.adrahin.contactsbook.service;

import org.adrahin.contactsbook.model.contactModels.Contact;
import org.adrahin.contactsbook.model.contactModels.ContactDto;

import java.util.List;
import java.util.UUID;

public interface InterfaceContactService {

    Contact createContact(ContactDto contact, UUID userID);

    List<ContactDto> getAllContacts();

    ContactDto getContactByContactId(UUID id);

//    ContactDto updateContact(String id, ContactDto newContact);

    boolean deleteContactById(UUID id);
}
