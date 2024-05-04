package org.bgs.dto;

import jakarta.validation.constraints.Email;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.bgs.entity.Role;
import org.bgs.nodeModel.AddressNode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Value
@FieldNameConstants
public class CustomerReadDto extends UserReadDto {

    AddressNode address;

    public CustomerReadDto(Long id, @Email String login, String password, String firstname, String lastname, String phone, Role role, LocalDate birthDate, AddressNode address) {
        super(id, login, password, firstname, lastname, phone, role, birthDate);
        this.address = address;
    }
}
