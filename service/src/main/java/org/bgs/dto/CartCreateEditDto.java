package org.bgs.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@FieldNameConstants
public class CartCreateEditDto {

    Long userId;
}
