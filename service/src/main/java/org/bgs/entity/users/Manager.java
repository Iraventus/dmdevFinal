package org.bgs.entity.users;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import org.bgs.entity.Role;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("MANAGER")
public class Manager extends User {

    private String jobTitle;

    @Builder
    public Manager(String login, String password, String firstname, String lastname, String phone, Role role, LocalDate birthDate, String jobTitle) {
        super(login, password, firstname, lastname, phone, role, birthDate);
        this.jobTitle = jobTitle;
    }
}
