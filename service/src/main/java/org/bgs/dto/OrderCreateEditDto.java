package org.bgs.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.bgs.entity.Status;

import java.util.List;

@Value
@FieldNameConstants
public class OrderCreateEditDto {

    List<Long> goodsIds;
    Status status;
    Long userId;
}
