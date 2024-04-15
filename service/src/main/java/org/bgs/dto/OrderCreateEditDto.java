package org.bgs.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.bgs.entity.Status;

@Value
@FieldNameConstants
public class OrderCreateEditDto {

    Status status;
    Long userId;
}
