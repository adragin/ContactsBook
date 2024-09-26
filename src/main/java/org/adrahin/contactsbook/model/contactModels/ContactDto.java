package org.adrahin.contactsbook.model.contactModels;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.adrahin.contactsbook.utils.ConstantsContact;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactDto {
    private String firstName;
    private String lastName;
    private String email;
    private List<String> phones;  // Список строк вместо списка объектов Phone
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    private String address;
    private String photo;
    private UUID ownerId;

    public LocalDate getBirthday() {
        if (birthday.equals(ConstantsContact.DEFAULT_BIRTHDAY)) {
            return null;
        }
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = Objects.requireNonNullElse(birthday, ConstantsContact.DEFAULT_BIRTHDAY);
    }
}
