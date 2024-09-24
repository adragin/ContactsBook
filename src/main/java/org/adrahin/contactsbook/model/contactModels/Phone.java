package org.adrahin.contactsbook.model.contactModels;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "contacts_phones")
public class Phone {
//    @JoinColumn(name = "contact_id")
//    private UUID contactId;
    @Id
    private String phoneNumber;

    public Phone(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
