package org.adrahin.contactsbook.model.userModels;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.adrahin.contactsbook.utils.UtilsUser;

import javax.management.relation.Role;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @Column(name = "user_id")
    private UUID userId;
    private int role; // 1->admin, 0->user
    @Column(unique = true)
    private String login; // email
    private String password;
    private String userName; // FullName or FirstName
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
}
