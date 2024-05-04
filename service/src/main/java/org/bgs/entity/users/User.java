package org.bgs.entity.users;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.bgs.entity.BaseEntity;
import org.bgs.entity.Role;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString()
@EqualsAndHashCode(callSuper = false)
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "status")
public abstract class User extends BaseEntity<Long> {

    @Email
    private String login;
    @NotEmpty
    private String password;
    @NotEmpty
    private String firstname;
    private String lastname;
    private String phone;
    @Enumerated(EnumType.STRING)
    private Role role;
    private LocalDate birthDate;
}
