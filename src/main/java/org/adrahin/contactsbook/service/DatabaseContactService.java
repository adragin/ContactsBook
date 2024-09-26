package org.adrahin.contactsbook.service;

import org.adrahin.contactsbook.factories.ContactFactory;
import org.adrahin.contactsbook.model.contactModels.Contact;
import org.adrahin.contactsbook.model.contactModels.ContactDto;
import org.adrahin.contactsbook.model.contactModels.Phone;
import org.adrahin.contactsbook.repository.InterfaceContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class DatabaseContactService implements InterfaceContactService {

    @Autowired
    private InterfaceContactRepository dbRepository;
    @Autowired
    private ContactFactory contactFactory;

    @Override
    public ContactDto getContactByContactId(UUID contactId) {
        Contact tempContact = dbRepository.getContactByContactId(contactId);
        return convertToDto(tempContact);
    }

    public List<ContactDto> getContactsByUserId(UUID userId) {
        List<Contact> contactListByUserId = dbRepository.getContactsByUserId(userId);
        return contactListByUserId.stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public List<ContactDto> getAllContacts() {
        List<Contact> tempListAllContacts = dbRepository.getAllContacts();
        return tempListAllContacts.stream()
                .map(contact -> convertToDto(contact))
                .toList();
    }

    @Override
    public Contact createContact(ContactDto contactDto, UUID userID) {
        Contact newContact = contactFactory.createContact(
                contactDto.getFirstName(),
                contactDto.getLastName(),
                contactDto.getEmail(),
                contactDto.getPhones(),  // Только номера телефонов
                contactDto.getBirthday(),
                contactDto.getAddress(),
                contactDto.getPhoto(),
                userID
        );
        newContact = dbRepository.createContact(newContact, userID);
        return newContact;
    }

//    @Override
//    public ContactDto updateContact(UUID contactId, ContactDto newContact) {
//        return dbRepository.updateContact(contactId, newContact);
//    }

    @Override
    public boolean deleteContactById(UUID contactId) {
        return dbRepository.deleteContactById(contactId);
    }

//    public String getOwnerId(String contactId) {
//        return dbRepository.getOwnerId(contactId);
//    }
//
    private ContactDto convertToDto(Contact contact) {
        List<String> phoneNumbers = contact.getPhones()
                .stream()
                .map(Phone::getPhoneNumber)
                .toList();

        return new ContactDto(
                contact.getFirstName(),
                contact.getLastName(),
                contact.getEmail(),
                phoneNumbers,  // Только номера телефонов
                contact.getBirthday(),
                contact.getAddress(),
                contact.getPhoto(),
                contact.getOwnerId()
        );
    }

}
