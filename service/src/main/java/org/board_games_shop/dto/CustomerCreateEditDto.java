package org.board_games_shop.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.board_games_shop.entity.Role;
import org.board_games_shop.nodeModel.AddressNode;

import java.time.LocalDate;

@Value
@FieldNameConstants
public class CustomerCreateEditDto {

    String login;
    String password;
    String firstname;
    String lastname;
    LocalDate birthDate;
    String phone;
    Role role;
    AddressNode address;
}
