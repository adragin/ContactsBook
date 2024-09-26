package org.adrahin.contactsbook.repository;

import org.adrahin.contactsbook.model.contactModels.Contact;
import org.adrahin.contactsbook.model.contactModels.ContactDto;
import org.adrahin.contactsbook.model.userModels.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class DBContactRepositoryAdapter implements InterfaceContactRepository {

    @Autowired
    private JPAContactRepository jpaContactRepository;
    @Autowired
    private JPAUserRepository jpaUserRepository;

    @Override
    public Contact createContact(Contact contact, UUID UserId) {
        User owner = jpaUserRepository.findById(UserId).get();
        contact.setOwnerId(owner.getUserId());
        jpaContactRepository.save(contact);
        return contact;
    }

    @Override
    public Contact getContactByContactId(UUID id) {
        return jpaContactRepository.findById(id).get();
    }

    @Override
    public List<Contact> getAllContacts() {
        return jpaContactRepository.findAll();
    }

    @Override
    public List<Contact> getContactsByUserId(UUID userId) {
        return jpaContactRepository.findContactsByOwnerId(userId);
    }

//    @Override
//    public ContactDto updateContact(String id, ContactDto newContact) {
//        if (jpaContactRepository.existsById(id)) {
//            jpaContactRepository.save();
//            return ;
//        }
//        return null;
//    }

    @Override
    public boolean deleteContactById(UUID contactId) {
        if (jpaContactRepository.existsById(contactId)) {
            jpaContactRepository.deleteById(contactId);
            return true;
        }
        return false;
    }

//    public String getOwnerId(String contactId) {
//        return jpaContactRepository.findById(contactId).get().getOwner().getUserId();
//    }
}
