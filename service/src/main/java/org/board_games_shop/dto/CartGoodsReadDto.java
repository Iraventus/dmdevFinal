package org.board_games_shop.dto;

import lombok.Value;

@Value
public class CartGoodsReadDto {

    Long id;
    GoodsReadDto goods;
    CartReadDto cart;
    OrderReadDto order;
    Integer totalGoods;
}
