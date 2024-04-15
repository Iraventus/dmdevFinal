package org.bgs.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.bgs.entity.Role;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Value
@FieldNameConstants
public class ManagerCreateEditDto extends UserCreateEditDto{

    String jobTitle;

    @Builder
    public ManagerCreateEditDto(String login, String password, String firstname, String lastname, String phone, Role role, LocalDate birthDate, String jobTitle) {
        super(login, password, firstname, lastname, phone, role, birthDate);
        this.jobTitle = jobTitle;
    }
}
