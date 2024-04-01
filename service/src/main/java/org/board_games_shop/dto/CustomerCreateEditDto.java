package org.board_games_shop.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

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
    JsonNode address;
}
