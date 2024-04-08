package org.board_games_shop.dto;

import lombok.Value;
import org.board_games_shop.entity.Role;
import org.board_games_shop.nodeModel.AddressNode;

import java.time.LocalDate;

@Value
public class CustomerReadDto {

    Long id;
    String login;
    String password;
    String firstname;
    String lastname;
    Role role;
    LocalDate birthDate;
    String phone;
    AddressNode address;
}
