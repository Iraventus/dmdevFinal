package org.bgs.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@FieldNameConstants
public class CartGoodsCreateEditDto {

    Long goodsId;
    Long cartId;
    Long orderId;
    Integer totalGoods;
}
