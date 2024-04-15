package org.bgs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

@FieldNameConstants
@AllArgsConstructor
@Data
public abstract class GoodsCreateEditDto {
    private String name;
    private String description;
    private Integer quantity;
    private Integer price;
}
