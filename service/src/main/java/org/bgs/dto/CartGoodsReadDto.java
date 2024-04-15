package org.bgs.dto;

import lombok.Value;

@Value
public class CartGoodsReadDto {

    Long id;
    GoodsReadDto goods;
    CartReadDto cart;
    OrderReadDto order;
    Integer totalGoods;
}
