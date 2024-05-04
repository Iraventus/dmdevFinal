package org.bgs.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@FieldNameConstants
public class OrderGoodsCreateEditDto {

    Long orderId;
    Long goodsId;
    Integer totalGoods;
}
