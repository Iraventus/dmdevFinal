package org.bgs.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.bgs.entity.Role;

import java.time.LocalDate;

@FieldNameConstants
@AllArgsConstructor
@Data
public class UserReadDto {

    private Long id;
    private String login;
    private String password;
    private String firstname;
    private String lastname;
    private String phone;
    @Enumerated(EnumType.STRING)
    private Role role;
    private LocalDate birthDate;
}
