package org.board_games_shop.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Value;

import java.time.LocalDate;

@Value
public class CustomerReadDto {

    Long id;
    String login;
    String password;
    String firstname;
    String lastname;
    LocalDate birthDate;
    String phone;
    JsonNode address;
}
