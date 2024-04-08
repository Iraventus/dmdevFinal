package org.board_games_shop.dto;

import lombok.Value;
import org.board_games_shop.entity.Status;

import java.time.Instant;

@Value
public class OrderReadDto {

    Long id;
    Status status;
    Instant reservationEndDate;
    CustomerReadDto user;
}
