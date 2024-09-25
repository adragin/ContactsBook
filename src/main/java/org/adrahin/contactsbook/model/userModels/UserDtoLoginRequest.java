package org.adrahin.contactsbook.model.userModels;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDtoLoginRequest {
    @NotBlank
    @Email
    private String login;
    @NotBlank
    private String password;
}
