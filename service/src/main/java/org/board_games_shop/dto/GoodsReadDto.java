package org.board_games_shop.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@Data
public abstract class GoodsReadDto {

    Long id;
    String name;
    String description;
    Integer quantity;
    Integer price;
}
