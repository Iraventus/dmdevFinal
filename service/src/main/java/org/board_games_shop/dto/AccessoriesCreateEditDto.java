package org.board_games_shop.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

@EqualsAndHashCode(callSuper = true)
@Value
@FieldNameConstants
public class AccessoriesCreateEditDto extends GoodsCreateEditDto {

    Long producerId;

    @Builder
    public AccessoriesCreateEditDto(String name, String description, Integer quantity, Integer price, Long producerId) {
        super(name, description, quantity, price);
        this.producerId = producerId;
    }
}
