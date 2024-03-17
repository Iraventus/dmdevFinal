package org.boardGamesShop.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserFilter {

    String login;
    String firstName;
    String lastName;
}
