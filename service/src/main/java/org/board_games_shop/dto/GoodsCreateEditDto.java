package org.board_games_shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

@FieldNameConstants
@AllArgsConstructor
@Data
public abstract class GoodsCreateEditDto {
    String name;
    String description;
    Integer quantity;
    Integer price;
}
