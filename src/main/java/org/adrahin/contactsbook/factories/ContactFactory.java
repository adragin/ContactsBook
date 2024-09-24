package org.adrahin.contactsbook.factories;

import org.adrahin.contactsbook.model.contactModels.Contact;
import org.adrahin.contactsbook.model.contactModels.Phone;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.adrahin.contactsbook.utils.ConstantsContact.DEFAULT_BIRTHDAY;
import static org.adrahin.contactsbook.utils.ConstantsContact.DEFAULT_PHOTO_URL;

@Component
public class ContactFactory {

    public Contact createContact(String firstName, String lastName, String email, List<String> phones,
                                 LocalDate birthday, String address, UUID ownerId) {
        Contact contact = new Contact();
        UUID contactId = UUID.randomUUID();

        contact.setId(contactId);
        contact.setFirstName(firstName);
        contact.setLastName(lastName);
        contact.setEmail(email);
        contact.setBirthday(Objects.requireNonNullElse(birthday, DEFAULT_BIRTHDAY));
        contact.setAddress(address);
        contact.setOwnerId(ownerId);
        contact.setCreateDate(LocalDateTime.now());
        contact.setLastUpdateDate(LocalDateTime.now());

        List<Phone> phonesList = phones.stream()
                .map(Phone::new)
                .toList();

        contact.setPhones(phonesList);

        try {
            contact.setPhoto(new URL(DEFAULT_PHOTO_URL));
        } catch (MalformedURLException e) {
            contact.setPhoto(null);
        }

        return contact;
    }
}
