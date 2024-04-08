package org.board_games_shop.entity.users;

import jakarta.persistence.*;
import lombok.*;
import org.board_games_shop.entity.AuditingEntity;
import org.board_games_shop.entity.Role;

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
public abstract class User extends AuditingEntity<Long> {

    private String login;
    private String password;
    private String firstname;
    private String lastname;
    private String phone;
    @Enumerated(EnumType.STRING)
    private Role role;
    private LocalDate birthDate;
    private LocalDate registrationDate;
}
