package org.board_games_shop.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.board_games_shop.entity.Status;

import java.time.Instant;
import java.util.List;

@Value
@FieldNameConstants
public class OrderCreateEditDto {

    Status status;
    Long userId;
}
