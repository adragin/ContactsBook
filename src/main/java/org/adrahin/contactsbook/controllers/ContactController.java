package org.adrahin.contactsbook.controllers;

import org.adrahin.contactsbook.factories.ContactFactory;
import org.adrahin.contactsbook.model.contactModels.Contact;
import org.adrahin.contactsbook.service.ContactService;
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
    private ContactService contactFactory;
    @Autowired
    private ContactService contactService;

    @GetMapping("/")
    public ResponseEntity<Contact> getRandomContact() {
        return ResponseEntity.ok(contactService.getContact());
    }

}
