package org.bgs.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.bgs.entity.Role;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Value
public class ManagerReadDto extends UserReadDto {

    String jobTitle;

    @Builder
    public ManagerReadDto(Long id, String login, String password, String firstname, String lastname, String phone, Role role, LocalDate birthDate, String jobTitle) {
        super(id, login, password, firstname, lastname, phone, role, birthDate);
        this.jobTitle = jobTitle;
    }
}
