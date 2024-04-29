package org.bgs.dto;

import lombok.Value;
import org.bgs.entity.Status;

import java.time.Instant;
import java.util.List;

@Value
public class OrderReadDto {

    Long id;
    Status status;
    Instant reservationEndDate;
    CustomerReadDto user;
    List<CartGoodsReadDto> goodsIds;
}
