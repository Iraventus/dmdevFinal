package org.bgs.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.bgs.entity.Role;

import java.time.LocalDate;

@FieldNameConstants
@AllArgsConstructor
@Data
public class UserCreateEditDto {

    @Email
    private String login;
    @NotBlank()
    private String password;
    private String firstname;
    private String lastname;
    private String phone;
    @Enumerated(EnumType.STRING)
    private Role role;
    private LocalDate birthDate;
}
