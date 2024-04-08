package org.board_games_shop.dto;

import lombok.Value;


@Value
public class CartReadDto {

    Long id;
    CustomerReadDto user;
}
