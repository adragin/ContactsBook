package org.adrahin.contactsbook.controllers;

import org.adrahin.contactsbook.factories.ContactFactory;
import org.adrahin.contactsbook.model.contactModels.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactFactory contactFactory;

    @GetMapping("/")
    public ResponseEntity<Contact> getRandomContact() {
        Contact contact = contactFactory.createContact(
                "John",
                "Doe",
                "john.doe@ex.com",
                List.of("914-504-4855","817-805-2624"),
                LocalDate.parse("1980-01-01"),
                "9636 Langworth Hills, Moorefurt, WI 54589",
                UUID.randomUUID()
                );
        return ResponseEntity.ok(contact);
    }

}
