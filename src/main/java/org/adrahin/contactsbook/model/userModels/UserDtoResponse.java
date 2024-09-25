package org.adrahin.contactsbook.model.userModels;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.adrahin.contactsbook.model.ErrorResponse;

@Getter
@Setter
@Builder
public class UserDtoResponse {
    private ErrorResponse error;
    @NotBlank
    @Email
    private String login;
    private String token;
}
