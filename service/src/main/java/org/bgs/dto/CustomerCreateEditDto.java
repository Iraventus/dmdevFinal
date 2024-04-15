package org.bgs.dto;

import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.bgs.entity.Role;
import org.bgs.nodeModel.AddressNode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Value
@FieldNameConstants
public class CustomerCreateEditDto extends UserCreateEditDto {

    AddressNode address;

    @Builder
    public CustomerCreateEditDto(String login, String password, String firstname, String lastname, String phone, Role role, LocalDate birthDate, AddressNode address) {
        super(login, password, firstname, lastname, phone, role, birthDate);
        this.address = address;
    }
}
