package org.bgs.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@FieldNameConstants
public class OrderGoodsReadDto {

    Long id;
    OrderReadDto order;
    GoodsReadDto goods;
    Integer totalGoods;
}
