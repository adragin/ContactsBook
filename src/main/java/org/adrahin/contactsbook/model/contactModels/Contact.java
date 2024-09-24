package org.adrahin.contactsbook.model.contactModels;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.adrahin.contactsbook.utils.ConstantsContact.DEFAULT_BIRTHDAY;

@Getter
@Setter
@Entity
@Table(name = "contacts")
public class Contact {
    @Id
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "contact_id")
    private List<Phone> phones;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    private String address;
    private URL photo;
    private UUID ownerId;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;

    public LocalDate getBirthday() {
        if (birthday.equals(DEFAULT_BIRTHDAY)) {
            return null;
        }
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = Objects.requireNonNullElse(birthday, DEFAULT_BIRTHDAY);
    }
}
